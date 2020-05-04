package by.a_lzr.globusmanager.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.room.TypeConverter
import by.a_lzr.globusmanager.GlobusApplication
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

object Converter {
    fun getBytes(uri: Uri): ByteArray {
        lateinit var data: ByteArray
        try {
            val cr = GlobusApplication.appContext.contentResolver
            val inputStream: InputStream? = cr.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            data = baos.toByteArray()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return data
    }

    fun getImage(image: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }

    @SuppressLint("SimpleDateFormat")
    fun getTimeString(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("HH:mm")
        return format.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateString(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        return format.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun getLongFromDate(date: String) = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).time


    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    fun getSizeInKilobyte(size: Int): Int {
        return size / 1024
    }

//    fun getUri(buf: ByteArray): Uri? {
//        val s = String(buf, "UTF-8")
//        return Uri.parse(s)
//    }
}