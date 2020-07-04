package com.covidkanzidata.utility

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.covidkanzidata.BuildConfig
import splitties.alertdialog.appcompat.*
import splitties.views.textColorResource
import com.covidkanzidata.R


class AppUtils {

    companion object {

        fun shortToast(context: Context, msg: String?) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        fun longToast(context: Context, msg: String?) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }

        fun getBuildVersionName(): String {
            return BuildConfig.VERSION_NAME
        }

        fun hideKeyboard(view: View) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        internal fun showAlertWithYes(context: Context, string: String) {
            context.alertDialog {
                message = string
                titleResource = R.string.app_name
                okButton { }
            }.onShow {
                positiveButton.textColorResource = R.color.colorPrimary
            }.show()
        }

    }
}