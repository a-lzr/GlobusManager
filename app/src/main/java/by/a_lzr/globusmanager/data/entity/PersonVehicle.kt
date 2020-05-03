package by.a_lzr.globusmanager.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import by.a_lzr.globusmanager.data.entity.Person
import by.a_lzr.globusmanager.data.entity.PersonEmployee

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Person::class,
            childColumns = ["personId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonEmployee::class,
            childColumns = ["leaderId"],
            parentColumns = ["personId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PersonVehicle(
    @PrimaryKey
    val personId: Long,
    val companyId: Int?,
    val leaderId: Long?,
    val dateStart: String?
//    @Relation(parentColumn = "companyId", entity = PersonCompany::class, entityColumn = "Id")
//    val company: PersonCompany?,
//    @Relation(parentColumn = "leaderId", entity = PersonEmployee::class, entityColumn = "Id")
//    val leader: PersonEmployee?,
//    @Relation(parentColumn = "Id", entity = PersonVehicleGroupLink::class, entityColumn = "linkId")
//    val groupLinks: List<PersonVehicleGroupLink>
)