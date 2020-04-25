package by.tms.globusmanager.sync

import android.provider.ContactsContract
import by.tms.globusmanager.R
import by.tms.globusmanager.settings.SettingsHelper

object SampleSyncAdapterColumns {
    /**
     * MIME-type used when storing a profile [Data] entry.
     */
    val MIME_PROFILE = SettingsHelper.getPreferenceString(R.string.mime_type)
//        "vnd.android.cursor.item/vnd.samplesyncadapter.profile"
    const val DATA_PID: String = ContactsContract.Data.DATA1
    const val DATA_SUMMARY: String = ContactsContract.Data.DATA2
    const val DATA_DETAIL: String = ContactsContract.Data.DATA3
}