package by.a_lzr.globusmanager.account

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AuthenticatorService : Service() {

    private lateinit var authenticator: Authenticator

    override fun onCreate() {
        super.onCreate()

        authenticator = Authenticator(this)
    }

    override fun onBind(intent: Intent): IBinder = authenticator.iBinder
}
