package by.a_lzr.globusmanager.deprecated.sync

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import android.util.Log

class SyncAdapter(context: Context?, autoInitialize: Boolean) :
    AbstractThreadedSyncAdapter(context, autoInitialize) {

    override fun onPerformSync(
        account: Account?,
        extras: Bundle?,
        authority: String?,
        provider: ContentProviderClient?,
        syncResult: SyncResult?
    ) {
        Log.e("TAGX", "Sync adapter called")
        ContactsManager.addContact(context, Contact("sample", "sampleee"))
    }
}