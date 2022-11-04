package com.example.androidtv.utils

import android.app.ProgressDialog
import android.content.Context

/**
 * Created by Dipak Kumar Mehta on 10/18/2021.
 */
class Progress {

    companion object {
        var progressDialog: ProgressDialog?= null
        fun showProgressBar(context: Context) {
           progressDialog = ProgressDialog(context)
            progressDialog?.max = 100
            progressDialog?.setMessage("Its loading....")
            //progressDialog?.setTitle("Downloading")
            progressDialog?.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            progressDialog?.show()
        }

        fun cancelProgressBar() {
            progressDialog?.dismiss()
        }
    }
}