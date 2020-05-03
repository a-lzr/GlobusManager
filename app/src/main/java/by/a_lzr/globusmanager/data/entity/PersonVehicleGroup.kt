package by.a_lzr.globusmanager.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = PersonVehicleGroupType::class,
            childColumns = ["typeId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PersonVehicleGroup(
    @PrimaryKey
    val id: Int,
    val typeId: Byte,
    val name: String
)