package by.tms.globusmanager.settings

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import by.tms.globusmanager.MainApplication
import by.tms.globusmanager.R
import by.tms.globusmanager.account.AccountHelper

object SettingsHelper {

    private val storage = PreferenceManager.getDefaultSharedPreferences(MainApplication.appContext)

    fun loadSettings() {
        loadAccountSettings()
        loadThemeSettings()
    }

    fun saveAccount(name: String) {
        putStringToStorage(
            getPreferenceString(R.string.settings_account_code),
            name
        )
    }

    private fun loadAccountSettings() {
        val account =
            getStringFromStorage(
                getPreferenceString(
                    R.string.settings_account_code
                ),
                null
            )
        account?.let {
            applyAccount(
                it
            )
        }
    }

    private fun loadThemeSettings() {
        val theme =
            storage.getString(
                getPreferenceString(
                    R.string.settings_theme_code
                ),null)
        theme?.let { applyTheme(it) }
    }

    private fun getStringFromStorage(key: String, defValue: String?): String? {
        return storage.getString(key, defValue)
    }

    private fun putStringToStorage(key: String, value: String) {
        storage.edit().putString(key, value).apply()
    }

    fun getPreferenceString(id: Int): String {
        return MainApplication.getResources().getString(id)
    }

    fun getPreferenceStringArray(id: Int): Array<String> {
        return MainApplication.getResources().getStringArray(id)
    }

    fun applyAccount(account: String) {;
        AccountHelper.initAccount(account)
    }

    fun applyTheme(code: String?) {;
        when (code) {
            getPreferenceString(R.string.settings_theme_classic_code) -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            getPreferenceString(R.string.settings_theme_dark_code) -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }
}