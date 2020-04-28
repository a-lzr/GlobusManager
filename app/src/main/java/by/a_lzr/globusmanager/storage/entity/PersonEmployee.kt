package by.a_lzr.globusmanager.storage.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigInteger

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Person::class,
            childColumns = ["personId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonCompany::class,
            childColumns = ["companyId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonDepartment::class,
            childColumns = ["departmentId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonEmployee::class,
            childColumns = ["leaderId"],
            parentColumns = ["personId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class PersonEmployee(
    @PrimaryKey
    val personId: Long,
    val companyId: Int?,
    val departmentId: Int?,
    val appointment: String?,
    val leaderId: Long?,
    val dateStart: String?
//    @Relation(parentColumn = "departmentId", entityColumn = "Id")
//    val department: PersonCompanyDepartment?,
//    @Relation(parentColumn = "leaderId", entity = PersonEmployee::class, entityColumn = "Id")
//    val leader: PersonEmployee?,
//    @Relation(parentColumn = "Id", entity = PersonEmployeeGroupLink::class, entityColumn = "linkId")
//    val groupLinks: List<PersonEmployeeGroupLink>
)