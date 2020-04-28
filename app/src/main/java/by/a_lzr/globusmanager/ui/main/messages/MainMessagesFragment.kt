package by.a_lzr.globusmanager.ui.main.messages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.ui.main.messages.details.MainMessagesDetailsFragment
import by.a_lzr.globusmanager.ui.main.messages.groups.MainMessagesGroupsFragment
import kotlinx.android.synthetic.main.fragment_main_messages.*

class MainMessagesFragment : Fragment(), MainMessagesFragmentListener {

    private lateinit var viewModel: MainMessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainMessagesViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main_messages, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showGroups()
//        showDetails(2)
//        childFragmentManager
//            .beginTransaction()
//            .replace(main_messages_fragment_widget.id, MainMessagesGroupsFragment())
//            .commit()


/*        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                MessagesCollection.instance.loadMessages()
                messagesView.adapter =
                    MessagesAdapter()
                messagesView.layoutManager = LinearLayoutManager(messagesView.context)
                messagesView.setHasFixedSize(true)
            }
        } */
    }

    override fun showGroups() {
        childFragmentManager
            .beginTransaction()
            .replace(main_messages_fragment_widget.id, MainMessagesGroupsFragment())
            .commit()
    }

    override fun showDetails(personID: Long) {
        MessagesCollection.instance.personId = personID
        childFragmentManager
            .beginTransaction()
            .replace(main_messages_fragment_widget.id, MainMessagesDetailsFragment())
            .commit()
    }
}
