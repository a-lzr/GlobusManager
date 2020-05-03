package by.a_lzr.globusmanager.data.entity

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class MessageDetail (
    @Embedded
    val message: Message,
    val countFiles: Int
)