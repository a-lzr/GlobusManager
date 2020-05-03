package by.a_lzr.globusmanager.data

import by.a_lzr.globusmanager.data.entity.File

object MessagesHelper {
    var personId: Long = 0
    var countNotRead: Int = 0
    var posIndex: Int = 0
    var files: ArrayList<File>? = null

    fun addFile(name: String, extension: String, body: ByteArray) {
        if (files == null)
            files = ArrayList()
        files!!.add(File(name, extension, body))
    }
}