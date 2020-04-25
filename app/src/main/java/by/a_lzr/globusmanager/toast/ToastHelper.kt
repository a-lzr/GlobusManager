package by.a_lzr.globusmanager.toast

import android.content.Context
import android.widget.Toast

object ToastHelper {

    fun showToast(context: Context?, text: String) {
        showToast(context, text, Toast.LENGTH_LONG)
    }

    private fun showToast(context: Context?, text: String, duration: Int) {
        Toast.makeText(context, text, duration).show()
    }
}