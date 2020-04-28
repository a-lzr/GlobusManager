package by.a_lzr.globusmanager.ui.main.messages.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.ui.main.messages.MessagesCollection
import kotlinx.android.synthetic.main.fragment_main_messages_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainMessagesDetailsFragment : Fragment() {

    private lateinit var viewModel: MainMessagesDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainMessagesDetailsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main_messages_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                MessagesCollection.instance.loadMessages()
                messagesDetailsView.adapter =
                    MessagesDetailsAdapter()
                messagesDetailsView.layoutManager = LinearLayoutManager(messagesDetailsView.context)
                messagesDetailsView.setHasFixedSize(true)
            }
        }
    }
}
