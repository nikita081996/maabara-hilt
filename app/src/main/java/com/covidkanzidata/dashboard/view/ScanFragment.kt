package com.covidkanzidata.dashboard.view

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageFormat
import android.hardware.camera2.*
import android.media.AudioManager
import android.media.ImageReader
import android.media.ToneGenerator
import android.os.Bundle
import android.os.Handler
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.covidkanzidata.R
import com.covidkanzidata.databinding.FragmentScanBinding
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import splitties.toast.toast
import timber.log.Timber


class ScanFragment : Fragment() {
    private lateinit var binding: FragmentScanBinding
    private lateinit var navController: NavController
    private val toneGen1 = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        FragmentScanBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        manageBlinkEffect()
        addSurfaceCallback()
    }

    /**
     * initilize blinking effect
     */
    private fun manageBlinkEffect() {
        val anim = ObjectAnimator.ofInt(
            binding.redLine, getString(R.string.backgroundColor), Color.RED
        )
        anim.duration = 100
        anim.setEvaluator(ArgbEvaluator())
        anim.repeatMode = ValueAnimator.REVERSE
        anim.repeatCount = ValueAnimator.INFINITE
        anim.start()
    }

    /**
     * create camera preview
     */
    private fun addSurfaceCallback() {
        binding.cameraPreview.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder?) {}
            override fun surfaceDestroyed(holder: SurfaceHolder?) {}

            override fun surfaceChanged(
                holder: SurfaceHolder?,
                format: Int,
                width: Int,
                height: Int
            ) {
                startCameraPreview(width, height)
            }
        })

    }

    /**
     * start camera preview
     */
    private fun startCameraPreview(desiredWidth: Int, desiredHeight: Int) {
        try {
            val cameraBkgHandler = Handler()
            val cameraManager =
                requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager

            cameraManager.cameraIdList.find {
                val characteristics = cameraManager.getCameraCharacteristics(it)
                val cameraDirection = characteristics.get(CameraCharacteristics.LENS_FACING)
                return@find cameraDirection != null && cameraDirection == CameraCharacteristics.LENS_FACING_BACK
            }?.let {
                val cameraStateCallback = object : CameraDevice.StateCallback() {
                    /**
                     * start detecteing barcode when camera is open and camera lens facing back
                     */
                    override fun onOpened(camera: CameraDevice) {
                        detectBarCode(camera, cameraBkgHandler, desiredWidth, desiredHeight)
                    }

                    override fun onClosed(camera: CameraDevice) {
                        Timber.d("onClosed")
                    }

                    override fun onDisconnected(camera: CameraDevice) {
                        Timber.d("onDisconnected")
                    }

                    override fun onError(camera: CameraDevice, error: Int) {
                        context?.toast(getString(R.string.error))
                    }
                }
                cameraManager.openCamera(it, cameraStateCallback, cameraBkgHandler)
                return
            }

        } catch (e: CameraAccessException) {
            context?.toast(getString(R.string.camera_access_error))
            Timber.d(e)
        } catch (e: SecurityException) {
            context?.toast(getString(R.string.error))
        }
    }

    /**
     * start detecting barcode
     */
    private fun detectBarCode(
        camera: CameraDevice,
        cameraBkgHandler: Handler,
        desiredWidth: Int,
        desiredHeight: Int
    ) {
        val barcodeDetector = BarcodeDetector.Builder(requireContext()).build()
        if (!barcodeDetector.isOperational) {
            return
        }

        val imgReader = ImageReader.newInstance(
            desiredWidth,
            desiredHeight,
            ImageFormat.JPEG,
            1
        )
        imgReader.setOnImageAvailableListener({ reader ->
            onImageAvailable(reader, barcodeDetector, imgReader)
        }, cameraBkgHandler)

        val captureStateCallback = object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession) {
                val builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                builder.addTarget(binding.cameraPreview.holder.surface)
                builder.addTarget(imgReader.surface)
                session.setRepeatingRequest(builder.build(), null, null)
            }

            override fun onConfigureFailed(session: CameraCaptureSession) {
                Timber.d("onConfigureFailed")
            }
        }

        camera.createCaptureSession(
            listOf(binding.cameraPreview.holder.surface, imgReader.surface),
            captureStateCallback,
            cameraBkgHandler
        )
    }

    /**
     * read barcode when barcode detected
     */
    private fun onImageAvailable(
        reader: ImageReader,
        barcodeDetector: BarcodeDetector,
        imgReader: ImageReader
    ) {
        val cameraImage = reader.acquireNextImage()
        val buffer = cameraImage.planes.first().buffer
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)

        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.count(), null)
        val frameToProcess = Frame.Builder().setBitmap(bitmap).build()
        val barcodeResults: SparseArray<Barcode> = barcodeDetector.detect(frameToProcess)

        if (barcodeResults.size() > 0) {
            val thisCode: Barcode = barcodeResults.valueAt(0)
            Timber.d("Barcode detected!")
            if (thisCode.rawValue != null && navController.currentDestination?.id == R.id.scanFragment) {
                val action =
                    ScanFragmentDirections.actionScanFragmentToResultFragment(
                        thisCode.rawValue
                    )
                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 100)
                navController.navigate(action)
            }
            imgReader.close()
        } else {
            Timber.d("No barcode found")
        }
        cameraImage.close()
    }
}



