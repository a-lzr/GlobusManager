package by.a_lzr.globusmanager.ui.main.messages

import by.a_lzr.globusmanager.storage.DatabaseHelper
import by.a_lzr.globusmanager.storage.entity.*

class MessagesCollection {
//    val groups = ArrayList<Message>()
//    val messages = ArrayList<Message>()
    var personId: Long = 0
    var posIndex: Int = 0
    var countNotRead: Int = 0

//    suspend fun loadGroups() {
//        groups.clear()
//        groups.addAll(DatabaseHelper.db.personDao.getAllMessagesGroups())
//    }

//    suspend fun loadMessages() {
//        messages.clear()
//        messages.addAll(DatabaseHelper.db.personDao.getMessagesByPerson(personId))
//    }

    companion object {
        val instance by lazy { MessagesCollection() }
    }
}