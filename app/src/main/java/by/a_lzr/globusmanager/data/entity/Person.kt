package by.a_lzr.globusmanager.data.entity

import androidx.room.*
import java.util.*

@Entity
data class Person(
    @PrimaryKey
    val id: Long,
    val name: String,
    val dateBirth: String?
//    @Relation(parentColumn = "id", entity = PersonEmployee::class, entityColumn = "personId")
//    val employee: PersonEmployee?,
//    @Relation(parentColumn = "id", entity = PersonVehicle::class, entityColumn = "personId")
//    val vehicle: PersonVehicle?,
//    @Relation(parentColumn = "id", entity = PersonContact::class, entityColumn = "personId")
//    val contacts: List<PersonContact>
)

//data class Face(val id: BigInteger, val firstName: String, val lastName: String, val dateBirth: String)
//data class FaceContact(val id: BigInteger, val faceId: BigInteger, val typeId: Byte, val contact: String)
//data class FaceGroup(val id: Int, val name: String, val virtualFlag: Boolean)
//data class FaceGroupLink(val faceId: BigInteger, val groupId: Int)
//data class FaceEmployee(val faceId: BigInteger, val typeId: Byte, val dateStart: String)