package by.a_lzr.globusmanager.ui.main.messages.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.storage.DataConverters
import by.a_lzr.globusmanager.toast.ToastHelper
import by.a_lzr.globusmanager.ui.main.messages.MessagesCollection
import kotlinx.android.synthetic.main.item_contact.view.*

class MessagesGroupsAdapter(val fragment: MainMessagesGroupsFragmentListener) :
    RecyclerView.Adapter<MessagesGroupsAdapter.MessagesViewHolder>() {

    class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return MessagesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return MessagesCollection.instance.groups.size
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        val itemView = holder.itemView
        itemView.setOnClickListener { v ->

//            with(v.parentFragment as MainMessagesActivityListener) {
//                showDetails(personId)
//            }
            fragment.showDetails(MessagesCollection.instance.groups[position].personId)
//            childFragmentManager
//                .beginTransaction()
//                .replace(main_messages_fragment_widget.id, MainMessagesGroupsFragment())
//                .commit()
            ToastHelper.showToast(v.context, "test")
        }

        with(MessagesCollection.instance.groups[position]) {
            itemView.nameTitle.text = message
            itemView.nameInfo.text = DataConverters().convertLongToTime(date)
        }
    }

}