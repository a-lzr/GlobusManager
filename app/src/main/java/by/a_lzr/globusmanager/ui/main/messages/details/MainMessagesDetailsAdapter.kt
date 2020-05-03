package by.a_lzr.globusmanager.ui.main.messages.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.a_lzr.globusmanager.R
import by.a_lzr.globusmanager.data.MessageDetailCallback
import by.a_lzr.globusmanager.data.entity.MessageDetail
import by.a_lzr.globusmanager.utils.Converter
import kotlinx.android.synthetic.main.fragment_main_messages_details.view.*
import kotlinx.android.synthetic.main.item_message_received.view.*
import kotlinx.android.synthetic.main.item_message_sent.view.*
import kotlinx.android.synthetic.main.item_message_sent.view.messageOutAttachBtn

private const val VIEW_TYPE_MESSAGE_SENT = 1
private const val VIEW_TYPE_MESSAGE_RECEIVED = 2

class MainMessagesDetailsAdapter :
    PagedListAdapter<MessageDetail, MainMessagesDetailsAdapter.MessagesViewHolder>(
        MessageDetailCallback()
    ) {

    open class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        return if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            SentMessageHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_sent, parent, false)
            )
        } else { //if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            ReceivedMessageHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_received, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item!!.message.outType)
            VIEW_TYPE_MESSAGE_SENT
        else
            VIEW_TYPE_MESSAGE_RECEIVED
    }

    public override fun getItem(position: Int): MessageDetail? = super.getItem(position)

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        val item = getItem(position)
        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(item!!)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(item!!)
        }
    }

    private class SentMessageHolder internal constructor(itemView: View) :
        MessagesViewHolder(itemView) {

        fun bind(message: MessageDetail) {
            itemView.messageOutTextView.text = message.message.message
            itemView.messageOutTimeView.text = Converter.getTimeString(message.message.date)
            if (message.countFiles > 0)
                itemView.messageOutAttachBtn.visibility = View.VISIBLE
            else
                itemView.messageOutAttachBtn.visibility = View.GONE
            when (message.message.status) {
                1.toByte() -> {
                    itemView.messageOutStatusImageView.setImageResource(R.drawable.ic_item_send_status1)
                    itemView.messageOutStatusImageView.visibility = View.VISIBLE
                }
                2.toByte() -> {
                    itemView.messageOutStatusImageView.setImageResource(R.drawable.ic_item_send_status2)
                    itemView.messageOutStatusImageView.visibility = View.VISIBLE
                }
                3.toByte() -> {
                    itemView.messageOutStatusImageView.setImageResource(R.drawable.ic_item_send_status3)
                    itemView.messageOutStatusImageView.visibility = View.VISIBLE
                }
                else -> itemView.messageOutStatusImageView.visibility = View.GONE
            }
        }
    }

    private class ReceivedMessageHolder internal constructor(itemView: View) :
        MessagesViewHolder(itemView) {
//        var messageText: TextView
//        var timeText: TextView
//        var nameText: TextView
//        var profileImage: ImageView

        fun bind(item: MessageDetail) {

            if (item.message.status == 0.toByte())
                itemView.inBadgeTopNotifyView.visibility = View.VISIBLE
            else
                itemView.inBadgeTopNotifyView.visibility = View.INVISIBLE

            itemView.inMessageTextView.text = item.message.message

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

        /*init {
            messageText = itemView.findViewById<View>(R.id.text_message_body) as TextView
            timeText = itemView.findViewById<View>(R.id.text_message_time) as TextView
            nameText = itemView.findViewById<View>(R.id.text_message_name) as TextView
//            profileImage = itemView.findViewById<View>(R.id.image_message_profile) as ImageView
        } */
    }
}