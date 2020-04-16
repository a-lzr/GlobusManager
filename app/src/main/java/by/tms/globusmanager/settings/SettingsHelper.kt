package by.tms.globusmanager.settings

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import by.tms.globusmanager.R

object SettingsHelper {

    fun initThemeSettings(context: Context) {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        val themePreference =
            sharedPreferences.getString(
                context.getString(R.string.settings_theme_code),
                context.getString(R.string.settings_theme_default_code)
            )
        themePreference?.let { SettingsHelper.applyTheme(context, it) }
    }

    fun applyTheme(context: Context, themeCode: String) {;
        when (themeCode) {
            context.getString(R.string.settings_theme_classic_code) -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            context.getString(R.string.settings_theme_dark_code) -> {
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