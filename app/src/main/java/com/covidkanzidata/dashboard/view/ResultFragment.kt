package com.covidkanzidata.dashboard.view

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.covidkanzidata.R
import com.covidkanzidata.dashboard.model.detailRequest.Request
import com.covidkanzidata.dashboard.model.details.Data
import com.covidkanzidata.dashboard.viewmodel.ResultViewModel
import com.covidkanzidata.databinding.ResultBinding
import com.covidkanzidata.permission.Permission
import com.covidkanzidata.service.Result
import com.covidkanzidata.utility.AppUtils
import com.covidkanzidata.utility.PermissionConstants
import com.covidkanzidata.utility.SessionObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.result.*
import splitties.toast.toast
import timber.log.Timber

@AndroidEntryPoint
class ResultFragment : Permission(), View.OnClickListener {

    private lateinit var binding: ResultBinding
    private lateinit var navController: NavController

    private val viewModel: ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ResultBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }

    /**
     * navigate to home fragment when start again button clicked
     */
    override fun startPreview() {
        navController.navigate(R.id.action_resultFragment_to_homeFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        binding.btnStartAgain.setOnClickListener(this)
        if (arguments != null) {
            val args = ResultFragmentArgs.fromBundle(
                requireArguments()
            )
            Timber.d("This is the value ==> %s", args.barcode)
            if (SessionObject.authToken != null && SessionObject.authToken != "")
                callDetailApi(viewModel.createRequest(args.barcode))
            else
                callTokenApi(viewModel.createRequest(args.barcode))
        }
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_start_again -> {
                if (checkPermissions(Manifest.permission.CAMERA)) {
                    startPreview()
                } else
                    requestPermissions(
                        Manifest.permission.CAMERA,
                        PermissionConstants.REQUEST_CAMERA_PERMISSION
                    )
            }
        }
    }

    /*
        call token api to get token
     */
    private fun callTokenApi(request: Request) {
        viewModel.getToken.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    SessionObject.putAccessToken(result.data.response?.token)
                    callDetailApi(request)
                }
                is Result.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    context?.toast(result.toString())
                    progressBar.visibility = View.GONE
                    AppUtils.showAlertWithYes(requireContext(), result.message)
                }
            }
        })
    }

    /*
        call detail api to get result of sacnned barcode
     */
    private fun callDetailApi(request: Request) {
        viewModel.getDetails(request).observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Success -> {
                    progressBar.visibility = View.GONE
                    if (it.data.messages?.get(0)?.code == "0") {
                        it.data.response?.data.let {
                            if (it?.get(0)?.fieldData?.testResult.equals(
                                    getString(R.string.Negative),
                                    true
                                )
                            )
                                it?.let { it1 -> negativeResult(it1) }
                            else if (it?.get(0)?.fieldData?.testResult.equals(
                                    getString(R.string.Positive),
                                    true
                                )
                            )
                                it?.let { it1 -> positiveResult(it1) }
                            else
                                noResult()
                        }
                    }
                }
                is Result.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    if (it.message.contains(getString(R.string.unauthorized), true)) {
                        callTokenApi(request)
                    } else {
                        noResult()
                        progressBar.visibility = View.GONE
                    }

                }
            }
        })
    }

    /**
     *  update UI when result is postive
     *  data is the return value from api
     */
    private fun positiveResult(data: List<Data>) {
        binding.data = data.get(0)
        binding.dataCl.visibility = View.VISIBLE
        binding.tvResult.text = getString(R.string.positive_result_text)
        binding.tvResult.visibility = View.VISIBLE
        binding.tvResult2.visibility = View.GONE
        binding.tvValidity.visibility = View.VISIBLE
    }

    /**
     *  update UI when result is negative
     *  data is the return value from api
     */
    private fun negativeResult(data: List<Data>) {
        binding.data = data.get(0)
        binding.dataCl.visibility = View.VISIBLE
        binding.tvResult.visibility = View.GONE
        binding.tvResult2.visibility = View.VISIBLE
        binding.tvValidity.visibility = View.VISIBLE
    }

    /**
     *  update UI when result not found
     */
    private fun noResult() {
        binding.dataCl.visibility = View.GONE
        binding.tvResult.text = getString(R.string.no_result_found)
        binding.tvResult.visibility = View.VISIBLE
        binding.tvResult2.visibility = View.GONE
        binding.tvValidity.visibility = View.GONE
    }
}
