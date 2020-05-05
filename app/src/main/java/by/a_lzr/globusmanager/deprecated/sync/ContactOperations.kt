package by.a_lzr.globusmanager.deprecated.sync

import android.content.ContentProviderOperation
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import android.provider.ContactsContract.RawContacts
import android.text.TextUtils
import by.a_lzr.globusmanager.utils.account.AccountHelper


class ContactOperations(
    context: Context, isSyncOperation: Boolean,
    batchOperation: BatchOperation
) {
    private val mValues: ContentValues
    private val mBatchOperation: BatchOperation
    private val mContext: Context
    private val mIsSyncOperation: Boolean
    private var mRawContactId: Long = 0
    private var mBackReference = 0
    private var mIsNewContact = false

    /**
     * Since we're sending a lot of contact provider operations in a single
     * batched operation, we want to make sure that we "yield" periodically
     * so that the Contact Provider can write changes to the DB, and can
     * open a new transaction.  This prevents ANR (application not responding)
     * errors.  The recommended time to specify that a yield is permitted is
     * with the first operation on a particular contact.  So if we're updating
     * multiple fields for a single contact, we make sure that we call
     * withYieldAllowed(true) on the first field that we update. We use
     * mIsYieldAllowed to keep track of what value we should pass to
     * withYieldAllowed().
     */
    private var mIsYieldAllowed: Boolean

    constructor(
        context: Context, userId: Long, accountName: String?,
        isSyncOperation: Boolean, batchOperation: BatchOperation
    ) : this(context, isSyncOperation, batchOperation) {
        mBackReference = mBatchOperation.size()
        mIsNewContact = true
        mValues.put(RawContacts.SOURCE_ID, userId)
        mValues.put(RawContacts.ACCOUNT_TYPE, AccountHelper.getAccountType())  //SyncStateContract.Constants.ACCOUNT_TYPE)
        mValues.put(RawContacts.ACCOUNT_NAME, accountName)
        val builder = newInsertCpo(
            RawContacts.CONTENT_URI,
            mIsSyncOperation,
            true
        ).withValues(mValues)
        mBatchOperation.add(builder.build())
    }

    constructor(
        context: Context, rawContactId: Long, isSyncOperation: Boolean,
        batchOperation: BatchOperation
    ) : this(context, isSyncOperation, batchOperation) {
        mIsNewContact = false
        mRawContactId = rawContactId
    }

    /**
     * Adds a contact name. We can take either a full name ("Bob Smith") or separated
     * first-name and last-name ("Bob" and "Smith").
     *
     * @param fullName The full name of the contact - typically from an edit form
     * Can be null if firstName/lastName are specified.
     * @param firstName The first name of the contact - can be null if fullName
     * is specified.
     * @param lastName The last name of the contact - can be null if fullName
     * is specified.
     * @return instance of ContactOperations
     */
    fun addName(
        fullName: String?,
        firstName: String?,
        lastName: String?
    ): ContactOperations {
        mValues.clear()
        if (!TextUtils.isEmpty(fullName)) {
            mValues.put(StructuredName.DISPLAY_NAME, fullName)
            mValues.put(StructuredName.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
        } else {
            if (!TextUtils.isEmpty(firstName)) {
                mValues.put(StructuredName.GIVEN_NAME, firstName)
                mValues.put(StructuredName.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
            }
            if (!TextUtils.isEmpty(lastName)) {
                mValues.put(StructuredName.FAMILY_NAME, lastName)
                mValues.put(StructuredName.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
            }
        }
        if (mValues.size() > 0) {
            addInsertOp()
        }
        return this
    }

    /**
     * Adds an email
     *
     * @param the email address we're adding
     * @return instance of ContactOperations
     */
    fun addEmail(email: String?): ContactOperations {
        mValues.clear()
        if (!TextUtils.isEmpty(email)) {
            mValues.put(Email.DATA, email)
            mValues.put(Email.TYPE, Email.TYPE_OTHER)
            mValues.put(Email.MIMETYPE, Email.CONTENT_ITEM_TYPE)
            addInsertOp()
        }
        return this
    }

    /**
     * Adds a phone number
     *
     * @param phone new phone number for the contact
     * @param phoneType the type: cell, home, etc.
     * @return instance of ContactOperations
     */
    fun addPhone(phone: String?, phoneType: Int): ContactOperations {
        mValues.clear()
        if (!TextUtils.isEmpty(phone)) {
            mValues.put(Phone.NUMBER, phone)
            mValues.put(Phone.TYPE, phoneType)
            mValues.put(Phone.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
            addInsertOp()
        }
        return this
    }

    /**
     * Adds a group membership
     *
     * @param id The id of the group to assign
     * @return instance of ContactOperations
     */
    fun addGroupMembership(groupId: Long): ContactOperations {
        mValues.clear()
        mValues.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, groupId)
        mValues.put(
            ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,
            ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
        )
        addInsertOp()
        return this
    }

    fun addAvatar(avatarUrl: String?): ContactOperations {
/*        if (avatarUrl != null) {
            val avatarBuffer: ByteArray = NetworkUtilities.downloadAvatar(avatarUrl)
            if (avatarBuffer != null) {
                mValues.clear()
                mValues.put(Photo.PHOTO, avatarBuffer)
                mValues.put(Photo.MIMETYPE, Photo.CONTENT_ITEM_TYPE)
                addInsertOp()
            }
        } */
        return this
    }

    /**
     * Adds a profile action
     *
     * @param userId the userId of the sample SyncAdapter user object
     * @return instance of ContactOperations
     */
    fun addProfileAction(userId: Long): ContactOperations {
        mValues.clear()
        if (userId != 0L) {
            mValues.put(SampleSyncAdapterColumns.DATA_PID, userId)
            mValues.put(SampleSyncAdapterColumns.DATA_SUMMARY, "Sample profile")
            mValues.put(SampleSyncAdapterColumns.DATA_DETAIL, "View Profile")
            mValues.put(ContactsContract.Data.MIMETYPE, SampleSyncAdapterColumns.MIME_PROFILE)
            addInsertOp()
        }
        return this
    }

    /**
     * Updates contact's serverId
     *
     * @param serverId the serverId for this contact
     * @param uri Uri for the existing raw contact to be updated
     * @return instance of ContactOperations
     */
    fun updateServerId(serverId: Long, uri: Uri): ContactOperations {
        mValues.clear()
        mValues.put(RawContacts.SOURCE_ID, serverId)
        addUpdateOp(uri)
        return this
    }

    /**
     * Updates contact's email
     *
     * @param email email id of the sample SyncAdapter user
     * @param uri Uri for the existing raw contact to be updated
     * @return instance of ContactOperations
     */
    fun updateEmail(
        email: String?,
        existingEmail: String?,
        uri: Uri
    ): ContactOperations {
        if (!TextUtils.equals(existingEmail, email)) {
            mValues.clear()
            mValues.put(Email.DATA, email)
            addUpdateOp(uri)
        }
        return this
    }

    /**
     * Updates contact's name. The caller can either provide first-name
     * and last-name fields or a full-name field.
     *
     * @param uri Uri for the existing raw contact to be updated
     * @param existingFirstName the first name stored in provider
     * @param existingLastName the last name stored in provider
     * @param existingFullName the full name stored in provider
     * @param firstName the new first name to store
     * @param lastName the new last name to store
     * @param fullName the new full name to store
     * @return instance of ContactOperations
     */
    fun updateName(
        uri: Uri,
        existingFirstName: String?,
        existingLastName: String?,
        existingFullName: String?,
        firstName: String?,
        lastName: String?,
        fullName: String?
    ): ContactOperations {
        mValues.clear()
        if (TextUtils.isEmpty(fullName)) {
            if (!TextUtils.equals(existingFirstName, firstName)) {
                mValues.put(StructuredName.GIVEN_NAME, firstName)
            }
            if (!TextUtils.equals(existingLastName, lastName)) {
                mValues.put(StructuredName.FAMILY_NAME, lastName)
            }
        } else {
            if (!TextUtils.equals(existingFullName, fullName)) {
                mValues.put(StructuredName.DISPLAY_NAME, fullName)
            }
        }
        if (mValues.size() > 0) {
            addUpdateOp(uri)
        }
        return this
    }

    fun updateDirtyFlag(isDirty: Boolean, uri: Uri): ContactOperations {
        val isDirtyValue = if (isDirty) 1 else 0
        mValues.clear()
        mValues.put(RawContacts.DIRTY, isDirtyValue)
        addUpdateOp(uri)
        return this
    }

    /**
     * Updates contact's phone
     *
     * @param existingNumber phone number stored in contacts provider
     * @param phone new phone number for the contact
     * @param uri Uri for the existing raw contact to be updated
     * @return instance of ContactOperations
     */
    fun updatePhone(
        existingNumber: String?,
        phone: String?,
        uri: Uri
    ): ContactOperations {
        if (!TextUtils.equals(phone, existingNumber)) {
            mValues.clear()
            mValues.put(Phone.NUMBER, phone)
            addUpdateOp(uri)
        }
        return this
    }

    fun updateAvatar(avatarUrl: String?, uri: Uri): ContactOperations {
/*        if (avatarUrl != null) {
            val avatarBuffer: ByteArray = NetworkUtilities.downloadAvatar(avatarUrl)
            if (avatarBuffer != null) {
                mValues.clear()
                mValues.put(Photo.PHOTO, avatarBuffer)
                mValues.put(Photo.MIMETYPE, Photo.CONTENT_ITEM_TYPE)
                addUpdateOp(uri)
            }
        } */
        return this
    }

    /**
     * Updates contact's profile action
     *
     * @param userId sample SyncAdapter user id
     * @param uri Uri for the existing raw contact to be updated
     * @return instance of ContactOperations
     */
    fun updateProfileAction(userId: Int?, uri: Uri): ContactOperations {
        mValues.clear()
        mValues.put(SampleSyncAdapterColumns.DATA_PID, userId)
        addUpdateOp(uri)
        return this
    }

    /**
     * Adds an insert operation into the batch
     */
    private fun addInsertOp() {
        if (!mIsNewContact) {
            mValues.put(Phone.RAW_CONTACT_ID, mRawContactId)
        }
        val builder = newInsertCpo(
            ContactsContract.Data.CONTENT_URI,
            mIsSyncOperation,
            mIsYieldAllowed
        )
        builder.withValues(mValues)
        if (mIsNewContact) {
            builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, mBackReference)
        }
        mIsYieldAllowed = false
        mBatchOperation.add(builder.build())
    }

    /**
     * Adds an update operation into the batch
     */
    private fun addUpdateOp(uri: Uri) {
        val builder =
            newUpdateCpo(uri, mIsSyncOperation, mIsYieldAllowed)
                .withValues(mValues)
        mIsYieldAllowed = false
        mBatchOperation.add(builder.build())
    }

    companion object {
        /**
         * Returns an instance of ContactOperations instance for adding new contact
         * to the platform contacts provider.
         *
         * @param context the Authenticator Activity context
         * @param userId the userId of the sample SyncAdapter user object
         * @param accountName the username for the SyncAdapter account
         * @param isSyncOperation are we executing this as part of a sync operation?
         * @return instance of ContactOperations
         */
        fun createNewContact(
            context: Context,
            userId: Long,
            accountName: String?,
            isSyncOperation: Boolean,
            batchOperation: BatchOperation
        ): ContactOperations {
            return ContactOperations(context, userId, accountName, isSyncOperation, batchOperation)
        }

        /**
         * Returns an instance of ContactOperations for updating existing contact in
         * the platform contacts provider.
         *
         * @param context the Authenticator Activity context
         * @param rawContactId the unique Id of the existing rawContact
         * @param isSyncOperation are we executing this as part of a sync operation?
         * @return instance of ContactOperations
         */
        fun updateExistingContact(
            context: Context, rawContactId: Long,
            isSyncOperation: Boolean, batchOperation: BatchOperation
        ): ContactOperations {
            return ContactOperations(context, rawContactId, isSyncOperation, batchOperation)
        }

        fun newInsertCpo(
            uri: Uri,
            isSyncOperation: Boolean, isYieldAllowed: Boolean
        ): ContentProviderOperation.Builder {
            return ContentProviderOperation
                .newInsert(
                    addCallerIsSyncAdapterParameter(
                        uri,
                        isSyncOperation
                    )
                )
                .withYieldAllowed(isYieldAllowed)
        }

        fun newUpdateCpo(
            uri: Uri,
            isSyncOperation: Boolean, isYieldAllowed: Boolean
        ): ContentProviderOperation.Builder {
            return ContentProviderOperation
                .newUpdate(
                    addCallerIsSyncAdapterParameter(
                        uri,
                        isSyncOperation
                    )
                )
                .withYieldAllowed(isYieldAllowed)
        }

        fun newDeleteCpo(
            uri: Uri,
            isSyncOperation: Boolean, isYieldAllowed: Boolean
        ): ContentProviderOperation.Builder {
            return ContentProviderOperation
                .newDelete(
                    addCallerIsSyncAdapterParameter(
                        uri,
                        isSyncOperation
                    )
                )
                .withYieldAllowed(isYieldAllowed)
        }

        private fun addCallerIsSyncAdapterParameter(
            uri: Uri,
            isSyncOperation: Boolean
        ): Uri {
            return if (isSyncOperation) {
                // If we're in the middle of a real sync-adapter operation, then go ahead
                // and tell the Contacts provider that we're the sync adapter.  That
                // gives us some special permissions - like the ability to really
                // delete a contact, and the ability to clear the dirty flag.
                //
                // If we're not in the middle of a sync operation (for example, we just
                // locally created/edited a new contact), then we don't want to use
                // the special permissions, and the system will automagically mark
                // the contact as 'dirty' for us!
                uri.buildUpon()
                    .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true")
                    .build()
            } else uri
        }
    }

    init {
        mValues = ContentValues()
        mIsYieldAllowed = true
        mIsSyncOperation = isSyncOperation
        mContext = context
        mBatchOperation = batchOperation
    }
}