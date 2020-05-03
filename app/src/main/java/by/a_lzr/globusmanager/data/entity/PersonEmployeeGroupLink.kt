package by.a_lzr.globusmanager.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import by.a_lzr.globusmanager.data.entity.PersonEmployee
import by.a_lzr.globusmanager.data.entity.PersonEmployeeGroup

@Entity(
    primaryKeys = ["groupId", "linkId"],
    foreignKeys = [
        ForeignKey(
            entity = PersonEmployeeGroup::class,
            childColumns = ["groupId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonEmployee::class,
            childColumns = ["linkId"],
            parentColumns = ["personId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PersonEmployeeGroupLink (
    val groupId: Int,
    val linkId: Long
)