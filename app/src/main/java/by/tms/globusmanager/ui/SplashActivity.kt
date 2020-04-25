package by.tms.globusmanager.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import by.tms.globusmanager.R
import by.tms.globusmanager.account.AccountHelper
import by.tms.globusmanager.activities.ActivityHelper
import by.tms.globusmanager.settings.SettingsHelper
import by.tms.globusmanager.ui.system.RegistrationActivity
import by.tms.globusmanager.ui.system.SynchronizeActivity

class SplashActivity : AppCompatActivity() {

    @SuppressLint("HardwareIds", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (!AccountHelper.checkRegistry()) {
            ActivityHelper.startActivity(this, RegistrationActivity::class.java)
            finish()
            return
        }

        if (!SettingsHelper.checkSynchronize()) {
            ActivityHelper.startActivity(this, SynchronizeActivity::class.java)
            finish()
            return
        }

//        sleep(2000)
        ActivityHelper.startActivity(this, MainActivity::class.java)
        finish()
    }
}
