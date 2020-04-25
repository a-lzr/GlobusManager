package by.a_lzr.globusmanager.deprecated.sync

import android.annotation.SuppressLint
import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import android.provider.ContactsContract.RawContacts
import android.util.Log
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.account.AccountHelper
import by.a_lzr.globusmanager.settings.SettingsHelper


object ContactsManager {

    fun addContact(context: Context, contact: Contact) {
        val resolver: ContentResolver = context.contentResolver
        resolver.delete(
            RawContacts.CONTENT_URI,
            RawContacts.ACCOUNT_TYPE + " = ?",
            arrayOf(AccountHelper.getAccountType())
        )
        val ops = ArrayList<ContentProviderOperation>()
        ops.add(
            ContentProviderOperation.newInsert(
                addCallerIsSyncAdapterParameter(
                    RawContacts.CONTENT_URI,
                    true
                )
            )
                .withValue(RawContacts.ACCOUNT_NAME, AccountHelper.getAccountName())
                .withValue(
                    RawContacts.ACCOUNT_TYPE,
                    AccountHelper.getAccountType()
                ) //.withValue(RawContacts.SOURCE_ID, 12345)
                //.withValue(RawContacts.AGGREGATION_MODE, RawContacts.AGGREGATION_MODE_DISABLED)
                .build()
        )
/*        ops.add(
            ContentProviderOperation.newInsert(
                addCallerIsSyncAdapterParameter(
                    ContactsContract.RawContacts.CONTENT_URI,
                    true
                )
            )
                .withValue(RawContacts.ACCOUNT_NAME, AccountHelper.getAccountName())
                .withValue(RawContacts.ACCOUNT_TYPE, AccountHelper.getAccountType())
                .withValue(Settings.UNGROUPED_VISIBLE, 1)
                .build()
        ) */
        ops.add(
            ContentProviderOperation.newInsert(
                addCallerIsSyncAdapterParameter(
                    ContactsContract.Data.CONTENT_URI,
                    true
                )
            )
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.GIVEN_NAME, contact.firstName)
                .withValue(StructuredName.FAMILY_NAME, contact.lastName)
                .build()
        )
        ops.add(
            ContentProviderOperation.newInsert(
                addCallerIsSyncAdapterParameter(
                    ContactsContract.Data.CONTENT_URI,
                    true
                )
            )
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "12342145")
                .build()
        )
        ops.add(
            ContentProviderOperation.newInsert(
                addCallerIsSyncAdapterParameter(
                    ContactsContract.Data.CONTENT_URI,
                    true
                )
            )
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    Email.CONTENT_ITEM_TYPE
                )
                .withValue(Email.DATA, "sample@email.com")
                .build()
        )
        ops.add(
            ContentProviderOperation.newInsert(
                addCallerIsSyncAdapterParameter(
                    ContactsContract.Data.CONTENT_URI,
                    true
                )
            )
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                    ContactsContract.Data.MIMETYPE,
                    SettingsHelper.getPreferenceString(R.string.mime_type)
                )
                .withValue(ContactsContract.Data.DATA1, 12345)
                .withValue(ContactsContract.Data.DATA2, "sample")
                .withValue(ContactsContract.Data.DATA3, "sample")
                .build()
        )
        try {
            val results =
                resolver.applyBatch(ContactsContract.AUTHORITY, ops)
            if (results.size == 0);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addCallerIsSyncAdapterParameter(
        uri: Uri,
        isSyncOperation: Boolean
    ): Uri? {
        return if (isSyncOperation) {
            uri.buildUpon()
                .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true")
                .build()
        } else uri
    }

    fun getMyContacts(): List<Contact?>? {
        return null
    }


    @SuppressLint("Recycle")
    fun updateContact(context: Context, name: String) {
        var id = -1
        val cursor: Cursor? = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            arrayOf<String>(
                ContactsContract.Data.RAW_CONTACT_ID,
                ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.CONTACT_ID
            ),
            StructuredName.DISPLAY_NAME + "= ?",
            arrayOf(name),
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0)
                Log.e("TAGX", cursor.getString(0))
                Log.e("TAGX", cursor.getString(1))
                Log.e("TAGX", cursor.getString(2))
                Log.e("TAGX", cursor.getString(3))
            } while (cursor.moveToNext())
        }
        if (id != -1) {
            val ops = ArrayList<ContentProviderOperation>()
            ops.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValue(ContactsContract.Data.RAW_CONTACT_ID, id)
                    .withValue(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                    .withValue(Email.DATA, "sample")
                    .build()
            )
            ops.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValue(ContactsContract.Data.RAW_CONTACT_ID, id)
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        SettingsHelper.getPreferenceString(R.string.mime_type)
                    )
                    .withValue(ContactsContract.Data.DATA1, "profile")
                    .withValue(ContactsContract.Data.DATA2, "profile")
                    .withValue(ContactsContract.Data.DATA3, "profile")
                    .build()
            )
            try {
                context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            Log.e("TAGX", "id not found")
        }
    }
}