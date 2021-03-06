package by.a_lzr.globusmanager.ui.main.messages

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_main_messages.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.utils.sync.SyncHelper
import by.a_lzr.globusmanager.utils.ToastHelper
import by.a_lzr.globusmanager.ui.main.MainFragmentListener
import by.a_lzr.globusmanager.ui.main.messages.groups.MainMessagesGroupsFragment


class MainMessagesFragment : Fragment() {

    private lateinit var viewModel: MainMessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        viewModel = ViewModelProvider(this).get(MainMessagesViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main_messages, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        childFragmentManager
            .beginTransaction()
            .replace(main_messages_fragment_widget.id, MainMessagesGroupsFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sync -> {
                CoroutineScope(Dispatchers.Default).launch {
                    SyncHelper.generateNewMessages()
                    withContext(Dispatchers.Main) {
                        updateBadge()
                        ToastHelper.showToast(context, getString(R.string.menu_main_messages_new))
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateBadge() {
        with(parentFragment as MainFragmentListener) {
            updateMessagesBadge()
        }
    }
}
