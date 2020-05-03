package by.a_lzr.globusmanager.data

import androidx.recyclerview.widget.DiffUtil
import by.a_lzr.globusmanager.data.entity.MessageGroup

class MessageGroupCallback : DiffUtil.ItemCallback<MessageGroup>() {

    override fun areItemsTheSame(oldItem: MessageGroup, newItem: MessageGroup): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageGroup, newItem: MessageGroup): Boolean {
        return oldItem.personId == newItem.personId
                && oldItem.date == newItem.date
                && oldItem.message == newItem.message
                && oldItem.countNoRead == newItem.countNoRead
                && oldItem.countNoDelivery == newItem.countNoDelivery
    }
}