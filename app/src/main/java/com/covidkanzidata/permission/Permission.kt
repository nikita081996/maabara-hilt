package com.covidkanzidata.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.covidkanzidata.R
import com.covidkanzidata.utility.PermissionConstants
import splitties.toast.toast

abstract class Permission : Fragment() {
    internal abstract fun startPreview()

    internal fun checkPermissions(permissions: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permissions
        ) == PackageManager.PERMISSION_GRANTED
    }

    internal fun requestPermissions(permissions: String, PERMISSION_ID: Int) {
        requestPermissions(
            arrayOf(permissions),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PermissionConstants.REQUEST_CAMERA_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                startPreview()
            } else {
                requireContext().toast(getString(R.string.camera_permission_error))
            }
        }
    }


}
