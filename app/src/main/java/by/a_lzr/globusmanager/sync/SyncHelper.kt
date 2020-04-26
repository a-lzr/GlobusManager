package by.a_lzr.globusmanager.sync

import java.lang.Thread.sleep

object SyncHelper {

    fun syncAll(): Boolean {
        sleep(5000)
//        val deferred1 = async(Dispatchers.Default) { getFirstValue() }
//        val deferred2 = async(Dispatchers.IO) { getSecondValue() }
//        useValues(deferred1.await(), deferred2.await())
        return true
    }
}