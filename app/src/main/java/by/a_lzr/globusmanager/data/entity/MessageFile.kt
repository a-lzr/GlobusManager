package by.a_lzr.globusmanager.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageFile(
    @PrimaryKey
    val id: Long,
    val messageId: Long,
    @Embedded
    var file: File
)