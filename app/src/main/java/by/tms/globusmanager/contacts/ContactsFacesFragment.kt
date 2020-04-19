package by.tms.globusmanager.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import by.tms.globusmanager.R
import kotlinx.android.synthetic.main.fragment_contacts_faces.*

class ContactsFacesFragment : Fragment() {

    private lateinit var viewModel: ContactsFacesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ContactsFacesViewModel::class.java)

        return inflater.inflate(R.layout.fragment_contacts_faces, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        ContactsContract.RawContacts.
        if (context == null) return

        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.WRITE_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return;
        }

        val queryUri: Uri = ContactsContract.Contacts.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
        )
        val selection = (ContactsContract.Contacts.DISPLAY_NAME
                + " IS NOT NULL")

        val cursorLoader = CursorLoader(
            context!!, queryUri,
            projection, selection, null, null
        )

        val cursor = cursorLoader.loadInBackground() ?: return

        val columnIndex_ID: Int = cursor.getColumnIndex(ContactsContract.Contacts._ID)
        val columnIndex_DISPLAYNAME: Int = cursor
            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

        var myContacts = "Мои контакты:\n"

        while (cursor.moveToNext()) {
            val id: Int = cursor.getInt(columnIndex_ID)
            val displayName: String = cursor.getString(columnIndex_DISPLAYNAME)
            myContacts += "$id : $displayName\n"
        }
//        mContactTextView.setText(myContacts)

        textView.text = myContacts
    }

}
