package com.covidkanzidata.dashboard.view

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.covidkanzidata.R
import com.covidkanzidata.databinding.HomeBinding
import com.covidkanzidata.permission.Permission
import com.covidkanzidata.utility.PermissionConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Permission(), View.OnClickListener {

    private lateinit var binding: HomeBinding
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = HomeBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    /**
     * navigate to scan fragment when start button clicked
     */
    override fun startPreview() {
        navController.navigate(R.id.scanFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        binding.scanBtn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.scan_btn -> {
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
}
