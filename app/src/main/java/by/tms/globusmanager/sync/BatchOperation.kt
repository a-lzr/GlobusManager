package by.tms.globusmanager.sync

import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Context
import android.content.OperationApplicationException
import android.net.Uri
import android.os.RemoteException
import android.provider.ContactsContract
import android.util.Log


class BatchOperation(context: Context?, private val mResolver: ContentResolver) {
    private val TAG = "BatchOperation"

    // List for storing the batch mOperations
    private val mOperations: ArrayList<ContentProviderOperation> = ArrayList()
    fun size(): Int {
        return mOperations.size
    }

    fun add(cpo: ContentProviderOperation) {
        mOperations.add(cpo)
    }

    fun execute(): List<Uri> {
        val resultUris: MutableList<Uri> = ArrayList<Uri>()
        if (mOperations.size === 0) {
            return resultUris
        }
        // Apply the mOperations to the content provider
        try {
            val results = mResolver.applyBatch(
                ContactsContract.AUTHORITY,
                mOperations
            )
            if (results != null && results.size > 0) {
                for (i in results.indices) {
                    resultUris.add(results[i].uri)
                }
            }
        } catch (e1: OperationApplicationException) {
            Log.e(TAG, "storing contact data failed", e1)
        } catch (e2: RemoteException) {
            Log.e(TAG, "storing contact data failed", e2)
        }
        mOperations.clear()
        return resultUris
    }

}