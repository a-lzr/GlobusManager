package by.a_lzr.globusmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message (
    @PrimaryKey
    val id: Long,
    val personId: Long,
    val message: String,
    val date: Long,
    val outType: Boolean,
    val status: Byte
)