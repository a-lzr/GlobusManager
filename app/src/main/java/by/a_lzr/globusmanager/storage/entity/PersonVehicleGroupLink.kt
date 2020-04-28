package by.a_lzr.globusmanager.storage.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import by.a_lzr.globusmanager.storage.entity.PersonVehicle
import by.a_lzr.globusmanager.storage.entity.PersonVehicleGroup
import java.math.BigInteger

@Entity(
    primaryKeys = ["groupId", "linkId"],
    foreignKeys = [
        ForeignKey(
            entity = PersonVehicleGroup::class,
            childColumns = ["groupId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonVehicle::class,
            childColumns = ["linkId"],
            parentColumns = ["personId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PersonVehicleGroupLink (
    val groupId: Int,
    val linkId: Long
)