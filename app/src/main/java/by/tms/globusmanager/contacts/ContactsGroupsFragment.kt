package by.tms.globusmanager.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import by.tms.globusmanager.R

class ContactsGroupsFragment : Fragment() {

    private lateinit var viewModel: ContactsGroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ContactsGroupsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_contacts_groups, container, false)
    }

}
