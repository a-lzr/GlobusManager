package by.tms.globusmanager.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission

object PermissionsHelper {

    fun addPermissions(
        activity: Activity,
        permissions: Array<String>,
        requestCode: Int
    ): Boolean {
        if (checkPermissions(activity, permissions)) return true
        Log.e("TAG1", "START REQUEST")
        requestPermissions(activity, permissions, requestCode)
        Log.e("TAG1", "END REQUEST")
        return false
    }

    fun checkPermissions(context: Context, permissions: Array<String>): Boolean {
        for (i in permissions.indices)
            if (checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED)
                return false
        return true
    }

    fun checkPermissionsResult(permissions: IntArray): Boolean {
        for (i in permissions.indices)
            if (permissions[i] != PackageManager.PERMISSION_GRANTED)
                return false
        return true
    }
}