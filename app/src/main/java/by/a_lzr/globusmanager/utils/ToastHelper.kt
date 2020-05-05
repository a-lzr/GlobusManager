package by.a_lzr.globusmanager.utils

import android.content.Context
import android.widget.Toast

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
        showToast(
            context,
            text,
            Toast.LENGTH_LONG
        )
    }

    private fun showToast(context: Context?, text: String, duration: Int) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT)
            .show()
    }
}