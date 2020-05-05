package by.a_lzr.globusmanager.ui.contacts

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.utils.sync.SyncHelper
import kotlinx.android.synthetic.main.fragment_contacts_faces.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ContactsFacesFragment : Fragment() {

    private lateinit var viewModel: ContactsFacesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        viewModel = ViewModelProvider(this).get(ContactsFacesViewModel::class.java)

        return inflater.inflate(R.layout.fragment_contacts_faces, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                ContactsCollection.instance.loadContacts()
                contactsView.adapter = ContactsAdapter()
                contactsView.layoutManager = LinearLayoutManager(contactsView.context)
                contactsView.setHasFixedSize(true)
            }
        }

//        ContactsContract.RawContacts.
/*        if (context == null) return

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
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
            requireContext(), queryUri,
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

        add_btn.setOnClickListener {
            with(context as MainActivityListener) {
                addContact()
                textView.text = ""
            }
        }

        get_btn.setOnClickListener {
            with(context as MainActivityListener) {
                textView.text = getContacts().toString()
            }
        } */
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu_contacts, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.action_contacts_sync -> {
                SyncHelper.updatePerson()
            }
        }
    }
}
