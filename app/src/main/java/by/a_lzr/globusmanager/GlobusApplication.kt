package by.a_lzr.globusmanager

import android.app.Application
import android.content.Context
import android.content.res.Resources
import by.a_lzr.globusmanager.notify.NotifyHelper
import by.a_lzr.globusmanager.settings.SettingsHelper
import by.a_lzr.globusmanager.storage.GlobusDatabase

class GlobusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
//        appDB = DatabaseHelper.database

        SettingsHelper.loadSettings()
        NotifyHelper.createChannel(this)
    }

    companion object {
        lateinit var appContext: Context
//        lateinit var appDB: GlobusDatabase

        fun getResources() : Resources {
            return appContext.resources
        }
    }
}