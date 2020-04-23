package by.tms.globusmanager.ui.settings

import android.os.Bundle
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import by.tms.globusmanager.R
import by.tms.globusmanager.settings.SettingsHelper

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

                    Toast.makeText(
                        context,
                        "${getString(R.string.settings_theme_set)} $name",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
        }
    }

}
