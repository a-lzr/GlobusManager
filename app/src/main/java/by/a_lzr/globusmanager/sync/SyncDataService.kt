package by.a_lzr.globusmanager.sync

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates
import java.lang.Thread.sleep
import by.a_lzr.globusmanager.NOTIFY_CHANNEL_ID
import by.a_lzr.globusmanager.settings.SettingsHelper
import by.a_lzr.globusmanager.R


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

            val notification = NotificationCompat.Builder(this, NOTIFY_CHANNEL_ID)
                .setContentTitle("Синхронизвция данных")
                .setContentText("1234")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build()
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
            sleep(5000)
            sendRespond(task, SYNC_STATUS_FINISH)
            complete = true
            stopSelf(startId)
//            val deferred1 = async(Dispatchers.Default) { getFirstValue() }
//            val deferred2 = async(Dispatchers.IO) { getSecondValue() }
//            useValues(deferred1.await(), deferred2.await())
        }
    }

    private fun sendRespond(task: Int, status: Int) {
        val intent = Intent(SettingsHelper.getPreferenceString(R.string.sync_broadcast_action))
            .putExtra(SYNC_PARAM_TASK, task)
            .putExtra(SYNC_PARAM_STATUS, status)
        sendBroadcast(intent)
    }
}