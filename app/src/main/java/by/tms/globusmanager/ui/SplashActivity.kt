package by.tms.globusmanager.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import by.tms.globusmanager.R
import by.tms.globusmanager.account.AccountHelper
import java.lang.Thread.sleep


class SplashActivity : AppCompatActivity() {

    @SuppressLint("HardwareIds", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (AccountHelper.checkRegistry()) {
            sleep(2000)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }
}
