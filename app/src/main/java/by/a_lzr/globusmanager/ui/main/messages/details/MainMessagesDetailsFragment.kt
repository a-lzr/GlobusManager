package by.a_lzr.globusmanager.ui.main.messages.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.storage.DatabaseHelper
import by.a_lzr.globusmanager.storage.entity.Message
import by.a_lzr.globusmanager.toast.ToastHelper
import by.a_lzr.globusmanager.ui.main.messages.MessagesCollection
import kotlinx.android.synthetic.main.fragment_main_messages_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainMessagesDetailsFragment : Fragment(),  View.OnClickListener {

    private lateinit var viewModel: MainMessagesDetailsViewModel
    private val adapter = MessagesDetailsAdapter()
    lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainMessagesDetailsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main_messages_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sendBtn.setOnClickListener(this)
        attachFileBtn.setOnClickListener(this)
        attachCameraBtn.setOnClickListener(this)

        CoroutineScope(Dispatchers.IO).launch {
            MessagesCollection.instance.posIndex =
                DatabaseHelper.db.personDao.getMessagesPosByPerson(MessagesCollection.instance.personId)
            withContext(Dispatchers.Main) {
                mLayoutManager = LinearLayoutManager(messagesDetailsView.context)
                messagesDetailsView.layoutManager = mLayoutManager
                messagesDetailsView.adapter = adapter

                //1
                val config = PagedList.Config.Builder()
                    .setPageSize(30)
                    .setEnablePlaceholders(false)
                    .build()

                //2
                val liveData = initializedPagedListBuilder(config)
//                    .setInitialLoadKey(50) ????
                    .build()

                //3
                liveData.observe(viewLifecycleOwner, Observer<PagedList<Message>> { pagedList ->
                    mLayoutManager.scrollToPosition(MessagesCollection.instance.posIndex)
                    adapter.submitList(pagedList)
                })
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            sendBtn.id -> {
                ToastHelper.showToast(context, "Сообщщение отправлено")
            }
            attachFileBtn.id -> {
                ToastHelper.showToast(context, "Прикрепление файла завершено")
            }
            attachCameraBtn.id -> {
                ToastHelper.showToast(context, "Прикрепление изображения с камеры завершено")
            }
        }
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Message> {

        val livePageListBuilder = LivePagedListBuilder(
            DatabaseHelper.db.personDao.getMessagesByPerson(MessagesCollection.instance.personId),
            config
        )
//        livePageListBuilder.setBoundaryCallback(MessagesDetailsBoundaryCallback())
        return livePageListBuilder
    }
}