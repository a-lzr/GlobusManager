package by.tms.globusmanager.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import by.tms.globusmanager.R

class ContactsFacesFragment : Fragment() {

    private lateinit var viewModel: ContactsFacesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ContactsFacesViewModel::class.java)

        return inflater.inflate(R.layout.fragment_contacts_faces, container, false)
    }

}