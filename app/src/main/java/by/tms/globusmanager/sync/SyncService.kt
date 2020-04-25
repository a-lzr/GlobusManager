package by.tms.globusmanager.sync

import android.app.Service
import android.content.Intent
import android.os.IBinder

class SyncService : Service() {

    override fun onCreate() {
        super.onCreate()

        synchronized(syncAdapterLock) {
            syncAdapter = syncAdapter ?: SyncAdapter(applicationContext, true)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return syncAdapter?.syncAdapterBinder ?: throw IllegalStateException()
    }

    companion object {
        // Storage for an instance of the sync adapter
        private var syncAdapter: SyncAdapter? = null
        // Object to use as a thread-safe lock
        private val syncAdapterLock = Any()
    }
}
