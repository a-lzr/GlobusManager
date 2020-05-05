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
import kotlinx.android.synthetic.main.fragment_main_messages_groups.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.utils.ActivityHelper
import by.a_lzr.globusmanager.data.DatabaseHelper
import by.a_lzr.globusmanager.data.entity.MessageGroup
import by.a_lzr.globusmanager.data.MessagesHelper
import by.a_lzr.globusmanager.ui.main.messages.details.MainMessagesDetailsActivity

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

                val config = PagedList.Config.Builder()
                    .setPageSize(30)
                    .setEnablePlaceholders(false)
                    .build()

                val liveData = initializedPagedListBuilder(config).build()

                liveData.observe(
                    viewLifecycleOwner,
                    Observer<PagedList<MessageGroup>> { pagedList ->
                        adapter.submitList(pagedList)
                    })
            }
        }
    }

    override fun showDetails(personId: Long) {
        MessagesHelper.personId = personId
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
