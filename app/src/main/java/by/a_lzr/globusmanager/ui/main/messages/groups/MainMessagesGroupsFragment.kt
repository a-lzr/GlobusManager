package by.a_lzr.globusmanager.ui.main.messages.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.activities.ActivityHelper
import by.a_lzr.globusmanager.storage.DatabaseHelper
import by.a_lzr.globusmanager.storage.entity.MessageGroup
import by.a_lzr.globusmanager.ui.main.messages.MainMessagesCollection
import by.a_lzr.globusmanager.ui.main.messages.details.MainMessagesDetailsActivity
import kotlinx.android.synthetic.main.fragment_main_messages_groups.*

class MainMessagesGroupsFragment : Fragment(), MainMessagesGroupsFragmentListener {

    private lateinit var viewModel: MainMessagesGroupsViewModel
    private val adapter = MainMessagesGroupsAdapter(this)

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
                messagesGroupsView.layoutManager = LinearLayoutManager(messagesGroupsView.context)
                messagesGroupsView.adapter = adapter
                //1
                val config = PagedList.Config.Builder()
                    .setPageSize(30)
                    .setEnablePlaceholders(false)
                    .build()

                //2
                val liveData = initializedPagedListBuilder(config).build()

                //3
                liveData.observe(viewLifecycleOwner, Observer<PagedList<MessageGroup>> { pagedList ->
                    adapter.submitList(pagedList)
                })
//                MessagesCollection.instance.loadGroups()
//                messagesGroupsView.adapter =
//                    MessagesGroupsAdapter(this@MainMessagesGroupsFragment)
//                messagesGroupsView.layoutManager = LinearLayoutManager(messagesGroupsView.context)
//                messagesGroupsView.setHasFixedSize(true)
            }
        }
    }

    override fun showDetails(personId: Long) {
        MainMessagesCollection.instance.personId = personId
        activity?.let { ActivityHelper.startActivity(it, MainMessagesDetailsActivity::class.java) }
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, MessageGroup> {

        val livePageListBuilder = LivePagedListBuilder(
            DatabaseHelper.db.personDao.getAllMessageGroups(),
            config
        )
//        livePageListBuilder.setBoundaryCallback(MessagesDetailsBoundaryCallback())
        return livePageListBuilder
    }
}
