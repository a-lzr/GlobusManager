package by.a_lzr.globusmanager.sync

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import by.a_lzr.globusmanager.settings.SettingsHelper
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.toast.ToastHelper

const val SYNC_CODE_TASK1 = 1

const val SYNC_STATUS_START = 100
const val SYNC_STATUS_FINISH = 200
const val SYNC_STATUS_BREAK = 300

const val SYNC_PARAM_TASK = "SYNC_TASK"
const val SYNC_PARAM_STATUS = "SYNC_STATUS"

class SyncBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return
        val task = intent.getIntExtra(SYNC_PARAM_TASK, 0)

        when (intent.getIntExtra(SYNC_PARAM_STATUS, 0)) {
            SYNC_STATUS_START -> {
                when (task) {
                    SYNC_CODE_TASK1 -> {
                        ToastHelper.showToast(
                            context,
                            SettingsHelper.getPreferenceString(R.string.synchronize_start_hint)
                        )
                    }
                }
            }
            SYNC_STATUS_FINISH -> {
                when (task) {
                    SYNC_CODE_TASK1 -> {
                        ToastHelper.showToast(
                            context,
                            SettingsHelper.getPreferenceString(R.string.synchronize_success_hint)
                        )
                    }
                }
                (context as SyncDataListener).syncFinish(true)
            }
            else -> {
                when (task) {
                    SYNC_CODE_TASK1 -> {
                        ToastHelper.showToast(
                            context,
                            SettingsHelper.getPreferenceString(R.string.synchronize_fail_hint)
                        )
                    }
                }
                (context as SyncDataListener).syncFinish(false)
            }
        }
    }
}