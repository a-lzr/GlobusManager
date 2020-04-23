package by.tms.globusmanager

import android.app.Application
import android.content.Context
import android.content.res.Resources
import by.tms.globusmanager.settings.SettingsHelper

class GlobusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        SettingsHelper.loadSettings()
    }

    companion object {
        lateinit var appContext: Context

        fun getResources() : Resources {
            return appContext.resources
        }
    }
}