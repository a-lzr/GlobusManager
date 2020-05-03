package by.a_lzr.globusmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonDepartment(
    @PrimaryKey
    val id: Int,
    val name: String
)