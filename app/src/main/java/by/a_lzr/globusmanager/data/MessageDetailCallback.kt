package by.a_lzr.globusmanager.data

import androidx.recyclerview.widget.DiffUtil
import by.a_lzr.globusmanager.data.entity.Message
import by.a_lzr.globusmanager.data.entity.MessageDetail

class MessageDetailCallback : DiffUtil.ItemCallback<MessageDetail>() {

    override fun areItemsTheSame(oldItem: MessageDetail, newItem: MessageDetail): Boolean {
        return oldItem.message.id == newItem.message.id
    }

    override fun areContentsTheSame(oldItem: MessageDetail, newItem: MessageDetail): Boolean {
        return oldItem.message.personId == newItem.message.personId
                && oldItem.message.date == newItem.message.date
                && oldItem.message == newItem.message
    }
}