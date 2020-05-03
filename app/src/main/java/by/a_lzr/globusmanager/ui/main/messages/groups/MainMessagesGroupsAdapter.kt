package by.a_lzr.globusmanager.ui.main.messages.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.data.entity.MessageGroup
import by.a_lzr.globusmanager.data.MessageGroupCallback
import by.a_lzr.globusmanager.utils.Converter
import kotlinx.android.synthetic.main.item_group.view.*

class MainMessagesGroupsAdapter(val fragment: MainMessagesGroupsFragmentListener) :
    PagedListAdapter<MessageGroup, MainMessagesGroupsAdapter.MessagesViewHolder>(MessageGroupCallback()) {

    class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_group, parent, false)

        return MessagesViewHolder(itemView)
    }

//    override fun getItemCount(): Int {
//        return MessagesCollection.instance.groups.size
//    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        val item = getItem(position)
        val itemView = holder.itemView
        itemView.setOnClickListener { v ->
            fragment.showDetails(item!!.personId)
        }

        with(item!!) {
            if (countNoRead > 0) {
                itemView.badgeTopNotifyView.text = countNoRead.toString()
                itemView.badgeTopNotifyView.visibility = View.VISIBLE
            }
            else
                itemView.badgeTopNotifyView.visibility = View.INVISIBLE

            if (countNoDelivery > 0) {
                itemView.badgeBottomNotifyView.text = countNoDelivery.toString()
                itemView.badgeBottomNotifyView.visibility = View.VISIBLE
            }
            else
                itemView.badgeBottomNotifyView.visibility = View.INVISIBLE

            itemView.titleTextView.text = message
            itemView.infoTextView.text = Converter.getDateString(date)
        }
    }
}