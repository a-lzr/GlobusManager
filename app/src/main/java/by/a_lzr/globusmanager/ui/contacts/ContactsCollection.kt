package by.a_lzr.globusmanager.ui.contacts

import by.a_lzr.globusmanager.storage.DatabaseHelper
import by.a_lzr.globusmanager.storage.entity.*

class ContactsCollection {
    val contacts = ArrayList<Person>()

    suspend fun loadContacts() {
        contacts.clear()
        contacts.addAll(DatabaseHelper.db.personDao.getAllPersons())
    }

    companion object {
        val instance by lazy { ContactsCollection() }
    }
}