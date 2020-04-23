package by.tms.globusmanager.permissions

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import by.tms.globusmanager.GlobusApplication

object PermissionsHelper {

    fun addPermissions(
        activity: Activity,
        permissions: Array<String>,
        requestCode: Int
    ): Boolean {
        if (checkPermissions(permissions)) return true
        Log.e("TAG1", "START REQUEST")
        requestPermissions(activity, permissions, requestCode)
        Log.e("TAG1", "END REQUEST")
        return false
    }

    fun checkPermissions(permissions: Array<String>): Boolean {
        for (i in permissions.indices)
            if (checkSelfPermission(
                    GlobusApplication.appContext,
                    permissions[i]
                ) != PackageManager.PERMISSION_GRANTED
            )
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