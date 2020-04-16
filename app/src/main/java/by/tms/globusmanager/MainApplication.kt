package by.tms.globusmanager

import android.app.Application
import android.content.Context
import by.tms.globusmanager.settings.SettingsHelper

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        SettingsHelper.initThemeSettings(appContext)
    }

    companion object {
        lateinit var appContext: Context
    }
}