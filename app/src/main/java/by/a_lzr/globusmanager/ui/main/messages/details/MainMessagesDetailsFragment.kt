package by.a_lzr.globusmanager.ui.main.messages.details

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
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
import by.a_lzr.globusmanager.permissions.PermissionsHelper
import by.a_lzr.globusmanager.storage.DatabaseHelper
import by.a_lzr.globusmanager.storage.entity.Message
import by.a_lzr.globusmanager.toast.ToastHelper
import by.a_lzr.globusmanager.ui.PERMISSION_CONTACT_REQUEST_CODE
import by.a_lzr.globusmanager.ui.main.messages.MainMessagesCollection
import kotlinx.android.synthetic.main.fragment_main_messages_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val CAMERA_REQUEST = 0
const val REQUEST_GALLERY = 100

class MainMessagesDetailsFragment : Fragment(),  View.OnClickListener {

    private lateinit var viewModel: MainMessagesDetailsViewModel
    private val adapter = MainMessagesDetailsAdapter()
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
            MainMessagesCollection.instance.posIndex =
                DatabaseHelper.db.personDao.getMessagesPosByPerson(MainMessagesCollection.instance.personId)
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
                    mLayoutManager.scrollToPosition(MainMessagesCollection.instance.posIndex)
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
                addFile()
//                ToastHelper.showToast(context, "Прикрепление файла завершено")
            }
            attachCameraBtn.id -> {
                if (!PermissionsHelper.addPermissions(
                        requireActivity(), arrayOf(Manifest.permission.CAMERA), PERMISSION_CONTACT_REQUEST_CODE
                    )
                ) return
                addPhoto()
            }
/*                val intent = Intent()
                intent.action = Intent.ACTION_CAMERA_BUTTON
                intent.putExtra(
                    Intent.EXTRA_KEY_EVENT, KeyEvent(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_CAMERA
                    )
                )
                sendOrderedBroadcast(intent, null)

                ToastHelper.showToast(context, "Прикрепление изображения с камеры завершено")
            } */
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === CAMERA_REQUEST && resultCode === Activity.RESULT_OK) {
            // Фотка сделана, извлекаем картинку
//            val thumbnailBitmap = data!!.extras!!["data"] as Bitmap?
//            imageView.setImageBitmap(thumbnailBitmap)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CONTACT_REQUEST_CODE -> {
                if (PermissionsHelper.checkPermissionsResult(grantResults)) {
                    addPhoto()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Message> {

        val livePageListBuilder = LivePagedListBuilder(
            DatabaseHelper.db.personDao.getMessagesByPerson(MainMessagesCollection.instance.personId),
            config
        )
//        livePageListBuilder.setBoundaryCallback(MessagesDetailsBoundaryCallback())
        return livePageListBuilder
    }

    private fun addFile() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    private fun addPhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }
}