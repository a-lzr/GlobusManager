package by.a_lzr.globusmanager.storage.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import by.a_lzr.globusmanager.storage.entity.PersonCompany
import java.math.BigInteger

@Entity
data class PersonDepartment(
    @PrimaryKey
    val id: Int,
    val name: String
)