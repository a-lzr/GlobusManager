package by.a_lzr.globusmanager.ui.main.messages.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.storage.entity.Message
import by.a_lzr.globusmanager.ui.main.messages.MessagesCollection


private const val VIEW_TYPE_MESSAGE_SENT = 1
private const val VIEW_TYPE_MESSAGE_RECEIVED = 2

class MessagesDetailsAdapter :
    PagedListAdapter<Message, MessagesDetailsAdapter.MessagesViewHolder>(MessagesDetailsCallback()) {

    open class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        var view: View

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_sent, parent, false)
            return SentMessageHolder(view) as MessagesViewHolder
        } else { //if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_received, parent, false)
            return ReceivedMessageHolder(view) as MessagesViewHolder
        }
//        return null
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item!!.outType)
            VIEW_TYPE_MESSAGE_SENT
        else
            VIEW_TYPE_MESSAGE_RECEIVED
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        val item = getItem(position)
        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(item!!)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(item!!)
        }
    }

    private class SentMessageHolder internal constructor(itemView: View) :
        MessagesViewHolder(itemView) {
        var messageText: TextView
        var timeText: TextView

        fun bind(message: Message) {
            messageText.text = message.message

            // Format the stored timestamp into a readable String using method.
//            timeText.setText(Utils.formatDateTime(message.getCreatedAt()))
        }

        init {
            messageText =
                itemView.findViewById<View>(R.id.text_message_body1) as TextView
            timeText = itemView.findViewById<View>(R.id.text_message_time) as TextView
        }
    }

    private class ReceivedMessageHolder internal constructor(itemView: View) :
        MessagesViewHolder(itemView) {
        var messageText: TextView
        var timeText: TextView
        var nameText: TextView
        var profileImage: ImageView

        fun bind(message: Message) {
            messageText.text = message.message

            // Format the stored timestamp into a readable String using method.
//            timeText.setText(Utils.formatDateTime(message.getCreatedAt()))
//            nameText.setText(message.getSender().getNickname())

            // Insert the profile image from the URL into the ImageView.
/*            Utils.displayRoundImageFromUrl(
                mContext,
                message.getSender().getProfileUrl(),
                profileImage
            ) */
        }

        init {
            messageText = itemView.findViewById<View>(R.id.text_message_body2) as TextView
            timeText = itemView.findViewById<View>(R.id.text_message_time) as TextView
            nameText = itemView.findViewById<View>(R.id.text_message_name) as TextView
            profileImage = itemView.findViewById<View>(R.id.image_message_profile) as ImageView
        }
    }
}