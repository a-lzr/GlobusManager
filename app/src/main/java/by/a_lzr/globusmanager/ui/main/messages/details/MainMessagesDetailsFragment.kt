package by.a_lzr.globusmanager.ui.main.messages.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.storage.DatabaseHelper
import by.a_lzr.globusmanager.storage.GlobusDatabase
import by.a_lzr.globusmanager.storage.entity.Message
import by.a_lzr.globusmanager.ui.main.messages.MainMessagesFragmentListener
import by.a_lzr.globusmanager.ui.main.messages.MessagesCollection
import kotlinx.android.synthetic.main.fragment_main_messages_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainMessagesDetailsFragment : Fragment() {

    private lateinit var viewModel: MainMessagesDetailsViewModel
    private val adapter = MessagesDetailsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainMessagesDetailsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main_messages_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        backBtn.setOnClickListener {
            with(parentFragment as MainMessagesFragmentListener) {
                showGroups()
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
//                MessagesCollection.instance.loadMessages()
                messagesDetailsView.layoutManager = LinearLayoutManager(messagesDetailsView.context)
                messagesDetailsView.adapter = adapter
                //1
                val config = PagedList.Config.Builder()
                    .setPageSize(30)
                    .setEnablePlaceholders(false)
                    .build()

                //2
                val liveData = initializedPagedListBuilder(config).build()

                //3
                liveData.observe(viewLifecycleOwner, Observer<PagedList<Message>> { pagedList ->
                    adapter.submitList(pagedList)
                })
//                messagesDetailsView.setHasFixedSize(true)
            }
        }
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Message> {

        val livePageListBuilder = LivePagedListBuilder(
            DatabaseHelper.db.personDao.getMessagesByPerson(MessagesCollection.instance.personId),
            config
        )
        livePageListBuilder.setBoundaryCallback(MessagesDetailsBoundaryCallback())
        return livePageListBuilder
    }
}
