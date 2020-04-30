package by.a_lzr.globusmanager.toast

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object ToastHelper {

    /*
    fun showSnackbar(view: View?, text: String) {
        showSnackbar(view, text, Toast.LENGTH_LONG)
    }

    private fun showSnackbar(view: View?, text: String, duration: Int) {
        Snackbar.make(view!!, text, Snackbar.LENGTH_SHORT)
            .show()
    } */

    fun showToast(context: Context?, text: String) {
        showToast(context, text, Toast.LENGTH_LONG)
    }

    private fun showToast(context: Context?, text: String, duration: Int) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT)
            .show()
    }
}