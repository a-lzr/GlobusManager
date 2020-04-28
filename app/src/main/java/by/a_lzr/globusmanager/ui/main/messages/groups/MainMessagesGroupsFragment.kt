package by.a_lzr.globusmanager.ui.main.messages.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.ui.main.messages.MessagesAdapter
import by.a_lzr.globusmanager.ui.main.messages.MessagesCollection
import kotlinx.android.synthetic.main.fragment_main_messages_groups.*

class MainMessagesGroupsFragment : Fragment() {

    private lateinit var viewModel: MainMessagesGroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainMessagesGroupsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main_messages_groups, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                MessagesCollection.instance.loadMessages()
                messagesGroupsView.adapter = MessagesAdapter()
                messagesGroupsView.layoutManager = LinearLayoutManager(messagesGroupsView.context)
                messagesGroupsView.setHasFixedSize(true)
            }
        }
    }
}
