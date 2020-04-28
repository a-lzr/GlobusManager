package by.a_lzr.globusmanager.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonCompany(
    @PrimaryKey
    val id: Int,
    val name: String,
    val address: String?,
    val dateStart: String?
)