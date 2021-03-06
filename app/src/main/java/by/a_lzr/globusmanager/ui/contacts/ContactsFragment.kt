package by.a_lzr.globusmanager.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_contacts.*
import by.a_lzr.globusmanager.R

class ContactsFragment : Fragment() {

    private lateinit var viewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.contact_tab_layout)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                updateTabPosition(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
//                Toast.makeText(context, "onTabUnselected", Toast.LENGTH_SHORT)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
//                Toast.makeText(context, "onTabReselected", Toast.LENGTH_SHORT)
            }
        })

        updateTabPosition(tabLayout.selectedTabPosition)
    }

    private fun updateTabPosition(position: Int) {
        when (position) {
            1 -> {
                childFragmentManager
                    .beginTransaction()
                    .replace(contact_fragment_widget.id, ContactsGroupsFragment())
                    .commit()
            }
            else -> {
                childFragmentManager
                    .beginTransaction()
                    .replace(contact_fragment_widget.id, ContactsFacesFragment())
                    .commit()
            }
        }
    }
}
