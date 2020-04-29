package by.a_lzr.globusmanager.ui.main.messages.details

import androidx.recyclerview.widget.DiffUtil
import by.a_lzr.globusmanager.storage.entity.Message

class MessagesDetailsCallback : DiffUtil.ItemCallback<Message>() {

    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.personId == newItem.personId
                && oldItem.date == newItem.date
                && oldItem.message == newItem.message
    }
}