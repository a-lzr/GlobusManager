package by.a_lzr.globusmanager.utils

import android.content.Context
import android.content.Intent

object ActivityHelper {

    fun startActivity(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        context.startActivity(intent)
    }
}