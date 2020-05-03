package by.a_lzr.globusmanager.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import by.a_lzr.globusmanager.data.entity.Person

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Person::class,
            childColumns = ["personId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class PersonContact(
    @PrimaryKey
    val id: Long,
    val personId: Long,
    val typeId: Byte, // 1 - мобильный телефон, 2 - email, 3 - globus
    val contact: String
)