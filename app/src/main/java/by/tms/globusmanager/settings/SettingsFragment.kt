package by.tms.globusmanager.settings

import android.os.Bundle
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import by.tms.globusmanager.MainApplication
import by.tms.globusmanager.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        val themePreference =
            findPreference<ListPreference>(getString(R.string.settings_theme_code))

        if (themePreference != null) {
            themePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    SettingsHelper.applyTheme(
                        MainApplication.appContext,
                        newValue.toString()
                    )

                    val name =
                        resources.getStringArray(R.array.settings_theme_names)[resources.getStringArray(
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
