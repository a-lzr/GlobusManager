package by.a_lzr.globusmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageGroup (
    @PrimaryKey
    val id: Long,
    val personId: Long,
    val message: String,
    val date: Long,
    val outType: Boolean,
    val status: Byte,
    val countNoRead: Int,
    val countNoDelivery: Int
)