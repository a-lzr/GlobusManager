package by.a_lzr.globusmanager.ui.system

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View.OnLongClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_synchronize.*
import by.a_lzr.globusmanager.activities.ActivityHelper
import by.a_lzr.globusmanager.alerts.CustomDialogFragment
import by.a_lzr.globusmanager.settings.SettingsHelper
import by.a_lzr.globusmanager.sync.*
import by.a_lzr.globusmanager.ui.MainActivity
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.toast.ToastHelper

class SynchronizeActivity : AppCompatActivity(), SyncDataListener,
    CustomDialogFragment.DialogListener {

    enum class ViewState {
        STEP_START, STEP_SYNC, STEP_FINISH
    }

    private var step = ViewState.STEP_START
    private val br = SyncBroadcastReceiver()

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_synchronize)

        syncDataBtn.setOnClickListener {
            when (step) {
                ViewState.STEP_SYNC -> actionAlert()
                else -> actionStart()
            }
        }

        syncDataBtn.setOnLongClickListener(OnLongClickListener {
            val myDialogFragment = CustomDialogFragment()
            val manager: FragmentManager = supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            myDialogFragment.show(transaction, "dialog")
            false
        })

        registerReceiver(
            br,
            IntentFilter(SettingsHelper.getPreferenceString(R.string.sync_broadcast_action))
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }

    private fun actionStart() {
        val intent = Intent(this, SyncDataService::class.java)
            .putExtra(SYNC_PARAM_TASK, SYNC_CODE_TASK1)
        startService(intent)
        updateView(ViewState.STEP_SYNC)
    }

    private fun actionFinish() {
        val intent = Intent(this, SyncDataService::class.java)
        stopService(intent)
        updateView(ViewState.STEP_FINISH)
    }

    private fun actionBack() {
        val intent = Intent(this, SyncDataService::class.java)
        stopService(intent)
        updateView(ViewState.STEP_START)
    }

    private fun actionAlert() {
        ToastHelper.showToast(
            this,
            SettingsHelper.getPreferenceString(R.string.synchronize_wait_hint)
        )
    }

    override fun syncFinish(successful: Boolean) {
        if (successful)
            actionFinish()
        else
            actionBack()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment?) {
        actionBack()
        ToastHelper.showToast(
            this,
            SettingsHelper.getPreferenceString(R.string.synchronize_stop_hint)
        )
    }

    override fun onDialogNegativeClick(dialog: DialogFragment?) {}

    private fun updateView(state: ViewState) {
        when (state) {
            ViewState.STEP_FINISH -> {
                ActivityHelper.startActivity(this, MainActivity::class.java)
                finish()
                return
            }
            ViewState.STEP_SYNC -> {
                syncTitleView.text =
                    SettingsHelper.getPreferenceString(R.string.synchronize_title_after)
                syncInfoView.text =
                    SettingsHelper.getPreferenceString(R.string.synchronize_info_after)
                syncDataBtn.setImageResource(R.drawable.ic_sync_wait)
            }
            else -> {
                syncTitleView.text =
                    SettingsHelper.getPreferenceString(R.string.synchronize_title_before)
                syncInfoView.text =
                    SettingsHelper.getPreferenceString(R.string.synchronize_info_before)
                syncDataBtn.setImageResource(R.drawable.ic_sync_start)
            }
        }
        step = state
    }
}
