package by.a_lzr.globusmanager.sync

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.notify.NotifyHelper
import by.a_lzr.globusmanager.settings.SettingsHelper

class SyncDataService : Service() {
    private var complete = false
    private var task by Delegates.notNull<Int>()

    override fun onDestroy() {
        if (!complete)
            sendRespond(task, SYNC_STATUS_BREAK)
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null)
            stopSelf(startId)
        else {
            task = intent.getIntExtra(SYNC_PARAM_TASK, 0)

            val notification = NotifyHelper.buildNotify(
                this,
                SettingsHelper.getPreferenceString(R.string.synchronize_notify_title)
            )
            startForeground(1, notification)

            when (task) {
                SYNC_CODE_TASK1 -> syncData(startId)
            }
        }
        return START_NOT_STICKY
    }

    private fun syncData(startId: Int) {
        sendRespond(task, SYNC_STATUS_START)

        val job = CoroutineScope(Dispatchers.Default).launch {
            if (SyncHelper.syncAll()) {
                sendRespond(task, SYNC_STATUS_FINISH)
                complete = true
            }
            stopSelf(startId)
        }
    }

    private fun sendRespond(task: Int, status: Int) {
        val intent = Intent(SettingsHelper.getPreferenceString(R.string.sync_broadcast_action))
            .putExtra(SYNC_PARAM_TASK, task)
            .putExtra(SYNC_PARAM_STATUS, status)
        sendBroadcast(intent)
    }
}