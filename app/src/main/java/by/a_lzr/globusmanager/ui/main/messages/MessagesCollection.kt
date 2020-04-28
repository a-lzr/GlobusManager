package by.a_lzr.globusmanager.ui.main.messages

import by.a_lzr.globusmanager.storage.DatabaseHelper
import by.a_lzr.globusmanager.storage.entity.*

class MessagesCollection {
    val messages = ArrayList<Message>()

    suspend fun loadMessages() {
        messages.clear()
        messages.addAll(DatabaseHelper.db.personDao.getAllMessages())
    }

    companion object {
        val instance by lazy { MessagesCollection() }
    }
}