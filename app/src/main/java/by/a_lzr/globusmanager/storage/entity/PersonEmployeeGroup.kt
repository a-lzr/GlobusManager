package by.a_lzr.globusmanager.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonEmployeeGroup(
    @PrimaryKey
    val id: Int,
    val name: String
)