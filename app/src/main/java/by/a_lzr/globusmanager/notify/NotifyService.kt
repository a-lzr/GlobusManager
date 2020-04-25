package by.a_lzr.globusmanager.notify

import android.app.IntentService
import android.content.Intent
import android.util.Log


class NotifyService : IntentService("NotifierService") {

    override fun onHandleIntent(intent: Intent?) {
        Log.e("TAG222", "Contact opened: " + intent!!.data);
    }
}
