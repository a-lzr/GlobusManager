package by.a_lzr.globusmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonVehicleGroupType(
    @PrimaryKey
    val id: Byte, // 1 - колонна
    val name: String
)