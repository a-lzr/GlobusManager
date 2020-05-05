package by.a_lzr.globusmanager.ui.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.utils.SettingsHelper
import by.a_lzr.globusmanager.utils.ToastHelper

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        val themePreference =
            findPreference<ListPreference>(
                SettingsHelper.getPreferenceString(R.string.settings_theme_code)
            )

        if (themePreference != null) {
            themePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    SettingsHelper.applyTheme(newValue.toString())

                    val name =
                        SettingsHelper.getPreferenceStringArray(R.array.settings_theme_names)[SettingsHelper.getPreferenceStringArray(
                            R.array.settings_theme_codes
                        ).indexOf(newValue.toString())]

                    ToastHelper.showToast(
                        context,
                        "${getString(R.string.settings_theme_set)} $name"
                    )
                    true
                }
        }
    }

}
