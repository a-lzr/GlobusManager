package by.a_lzr.globusmanager.ui.contacts

import by.a_lzr.globusmanager.data.entity.Person
import by.a_lzr.globusmanager.data.DatabaseHelper

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