package by.a_lzr.globusmanager

import android.app.Application
import android.content.Context
import android.content.res.Resources
import by.a_lzr.globusmanager.notify.NotifyHelper
import by.a_lzr.globusmanager.settings.SettingsHelper

class GlobusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        SettingsHelper.loadSettings()
        NotifyHelper.createChannel(this)
    }

    companion object {
        lateinit var appContext: Context

        fun getResources() : Resources {
            return appContext.resources
        }
    }
}