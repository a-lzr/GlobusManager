package by.a_lzr.globusmanager.ui.contacts

import android.accounts.Account
import android.accounts.AccountManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.a_lzr.globusmanager.GlobusApplication
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.sync.SyncHelper
import kotlinx.android.synthetic.main.fragment_contacts_groups.*

class ContactsGroupsFragment : Fragment() {

    private lateinit var viewModel: ContactsGroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        viewModel = ViewModelProvider(this).get(ContactsGroupsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_contacts_groups, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val am = AccountManager.get(GlobusApplication.appContext)
        val list: Array<Account> = am.accounts
        var s: String = ""

        list.forEach {
            s = s.plus(it.name).plus(" ")
        }
        textView2.text = s
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu_groups, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.action_groups_sync -> {
                SyncHelper.updatePerson()
            }
        }
    }
}
