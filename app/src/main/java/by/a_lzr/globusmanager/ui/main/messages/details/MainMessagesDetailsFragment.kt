package by.a_lzr.globusmanager.ui.main.messages.details

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.permissions.PermissionsHelper
import by.a_lzr.globusmanager.data.DatabaseHelper
import by.a_lzr.globusmanager.data.MessagesHelper
import by.a_lzr.globusmanager.data.entity.MessageDetail
import by.a_lzr.globusmanager.sync.SyncHelper
import by.a_lzr.globusmanager.ui.PERMISSION_CONTACT_REQUEST_CODE
import by.a_lzr.globusmanager.utils.Converter
import kotlinx.android.synthetic.main.fragment_main_messages_details.*

const val CAMERA_REQUEST = 0
const val REQUEST_GALLERY = 100

class MainMessagesDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: MainMessagesDetailsViewModel
    private val adapter = MainMessagesDetailsAdapter()
    lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var popupMenu: PopupMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MainMessagesDetailsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_main_messages_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        CoroutineScope(Dispatchers.IO).launch {
            MessagesHelper.posIndex =
                DatabaseHelper.db.personDao.getMessagesPosByPerson(MessagesHelper.personId)
            withContext(Dispatchers.Main) {
                mLayoutManager = LinearLayoutManager(messagesDetailsView.context)
                messagesDetailsView.layoutManager = mLayoutManager
                messagesDetailsView.adapter = adapter


                val scrollListener = object : RecyclerView.OnScrollListener() {

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        val position = mLayoutManager.findLastVisibleItemPosition()
                        val item = adapter.getItem(position) ?: return
                        if (position > MessagesHelper.posIndex || item.message.status == 0.toByte()) {
                            CoroutineScope(Dispatchers.IO).launch {
                                SyncHelper.updateMessageStatus(
                                    MessagesHelper.personId,
                                    item.message.id
                                )
                                MessagesHelper.posIndex = position
                            }
                        }

//                        adapter.getItemViewType()
//                        itemViewModel.getPosition

//                        val totalItemCount = recyclerView!!.layoutManager.itemCount
//                        if (totalItemCount == lastVisibleItemPosition + 1) {
//                            Log.d("MyTAG", "Load new list")
//                            recycler.removeOnScrollListener(scrollListener)
//                        }
                    }
                }
                messagesDetailsView.addOnScrollListener(scrollListener)

                adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        if (adapter.getItem(positionStart)?.message!!.outType) {
                            MessagesHelper.posIndex = positionStart
                            mLayoutManager.scrollToPosition(positionStart)
                        }
                    }
                })

                val config = PagedList.Config.Builder()
                    .setPageSize(30)
                    .setEnablePlaceholders(true)
                    .build()

                val liveData = initializedPagedListBuilder(config)
//                    .setInitialLoadKey(50) ????
                    .build()

                liveData.observe(viewLifecycleOwner, Observer<PagedList<MessageDetail>> { pagedList ->
                    mLayoutManager.scrollToPosition(MessagesHelper.posIndex)
                    adapter.submitList(pagedList)
//                    mLayoutManager.onScrollStateChanged()
                })
            }
        }
    }

    private fun initView() {
        sendBtn.setOnClickListener(this)
        attachFileBtn.setOnClickListener(this)
        attachCameraBtn.setOnClickListener(this)
        attachMoreBtn.setOnClickListener(this)

        popupMenu = PopupMenu(context, attachMoreBtn)
        popupMenu.inflate(R.menu.popup_menu_message_send)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_messages_send -> {
                    MessagesHelper.clearFiles()
                    true
                }
                else -> false
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            sendBtn.id -> addMessageOut()
            attachFileBtn.id -> addFile()
            attachCameraBtn.id -> {
                if (!PermissionsHelper.addPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CAMERA),
                        PERMISSION_CONTACT_REQUEST_CODE
                    )
                ) return
                addPhoto()
            }
            attachMoreBtn.id -> {
                popupMenu.show()
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

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return

//        var bitmap: Bitmap? = null
//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        when (requestCode) {
            REQUEST_GALLERY -> {
                if (resultCode == RESULT_OK) {
                    MessagesHelper.addFile("file", "bmp", Converter.getBytes(data.data!!))
//                    imageTitleView.setImageBitmap(Converter.getImage(MessagesHelper.file!!))

//                    imageTitleView.setImageURI(Utils.getImage(bb))

//                    val selectedImage = data!!.data;
//                    val image = ImageDecoder.createSource(context?.contentResolver!!, selectedImage!!)
//                    imageTitleView.setImageBitmap()
//                    imageTest.data = getBytesFromImageMethod(image)//TODO
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    imageView.setImageBitmap(bitmap);
                }
            }
        }

        //if (requestCode === CAMERA_REQUEST && resultCode === Activity.RESULT_OK) {
        // Фотка сделана, извлекаем картинку
//            val thumbnailBitmap = data!!.extras!!["data"] as Bitmap?
//            imageView.setImageBitmap(thumbnailBitmap)
//        }
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
            LivePagedListBuilder<Int, MessageDetail> {

        val livePageListBuilder = LivePagedListBuilder(
            DatabaseHelper.db.personDao.getMessagesDetailsByPerson(MessagesHelper.personId),
            config
        )
//        livePageListBuilder.setBoundaryCallback(MessagesDetailsBoundaryCallback())
        return livePageListBuilder
    }

    private fun addMessageOut() {
        CoroutineScope(Dispatchers.IO).launch {
            if (sendTextView.text!!.isNotEmpty()) {
                SyncHelper.addMessageOut(
                    MessagesHelper.personId,
                    sendTextView.text.toString()
                )
            }
        }
    }

    private fun addFile() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY)
//        ToastHelper.showToast(context, "Прикрепление файла завершено")
    }

    private fun addPhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }
}