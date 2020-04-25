package by.tms.globusmanager.ui.system

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.tms.globusmanager.R
import by.tms.globusmanager.activities.ActivityHelper
import by.tms.globusmanager.settings.SettingsHelper
import by.tms.globusmanager.ui.MainActivity
import kotlinx.android.synthetic.main.activity_synchronize.*

class SynchronizeActivity : AppCompatActivity() {

    private var step = 0

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_synchronize)

        syncDataBtn.setOnClickListener {
            when (step) {
                1 -> {
                    ActivityHelper.startActivity(this, MainActivity::class.java)
                    Toast.makeText(
                        this,
                        SettingsHelper.getPreferenceString(R.string.synchronize_wait_hint),
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
                else -> {
                    syncTitleView.text =
                        SettingsHelper.getPreferenceString(R.string.synchronize_title_after)
                    syncInfoView.text =
                        SettingsHelper.getPreferenceString(R.string.synchronize_info_after)
                    syncDataBtn . setImageResource (R.drawable.ic_sync_wait)
                    step = 1
                    Toast.makeText(
                        this,
                        SettingsHelper.getPreferenceString(R.string.synchronize_start_hint),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
