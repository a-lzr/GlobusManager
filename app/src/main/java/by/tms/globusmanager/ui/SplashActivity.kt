package by.tms.globusmanager.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import by.tms.globusmanager.R
import by.tms.globusmanager.account.AccountHelper
import by.tms.globusmanager.permissions.PermissionsHelper
import java.lang.Thread.sleep


class SplashActivity : AppCompatActivity() {

    @SuppressLint("HardwareIds", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

/*
        PermissionsHelper.addPermissions(
            this, arrayOf(
                Manifest.permission.READ_PHONE_STATE
            ),
            PERMISSION_PHONE_REQUEST_CODE
        )
 */

        val account = AccountHelper.getAccount()

        if (account != null && PermissionsHelper.checkPermissions(
                this, arrayOf(
                    Manifest.permission.READ_PHONE_STATE
                )
            )
        ) {



            sleep(4000)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
        finish()


/*        if (PermissionsHelper.checkPermissions(
                this, arrayOf(
                    Manifest.permission.READ_PHONE_STATE
                )
            )
        ) {
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            GlobusAccount.initAccount(telephonyManager.line1Number)
/*          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
              imei = telephonyManager.getImei()
            } else {
              imei = telephonyManager.getDeviceId()
            } */
//            Log.e("TAG2", account.name)
//            Log.e("TAG2", account.type)

//            finish()
        } else {
//            finish()
        }

        val am = AccountManager.get(this)
//        val authToken = am.peekAuthToken(account, authTokenType)

        val intent = Intent(this, RegistrationActivity::class.java)
//        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() */

/*        sleep(4000)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() */
    }
}
