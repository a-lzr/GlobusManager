package by.a_lzr.globusmanager.storage

import androidx.room.*
import by.a_lzr.globusmanager.storage.entity.*

@Dao
interface PersonDao {
    @Insert
    fun addPersonCompany(data: List<PersonCompany>)

    @Insert
    fun addPersonDepartment(data: List<PersonDepartment>)

    @Insert
    fun addPersonEmployeeGroup(data: List<PersonEmployeeGroup>)

    @Insert
    fun addPersonVehicleGroupType(data: List<PersonVehicleGroupType>)

    @Insert
    fun addPersonVehicleGroup(data: List<PersonVehicleGroup>)

    @Insert
    fun addPerson(data: List<Person>)

    @Insert
    fun addPersonContact(data: List<PersonContact>)

    @Insert
    fun addPersonEmployee(data: List<PersonEmployee>)

    @Insert
    fun addPersonEmployeeGroupLink(data: List<PersonEmployeeGroupLink>)

    @Insert
    fun addPersonVehicle(data: List<PersonVehicle>)

    @Insert
    fun addPersonVehicleGroupLink(data: List<PersonVehicleGroupLink>)

    @Insert
    fun addMessage(data: List<Message>)


    @Query("DELETE FROM PersonCompany")
    fun deletePersonCompanyAll()

    @Query("DELETE FROM PersonDepartment")
    fun deletePersonDepartmentAll()

    @Query("DELETE FROM PersonEmployeeGroup")
    fun deletePersonEmployeeGroupAll()

    @Query("DELETE FROM PersonVehicleGroupType")
    fun deletePersonVehicleGroupTypeAll()

    @Query("DELETE FROM PersonVehicleGroup")
    fun deletePersonVehicleGroupAll()

    @Query("DELETE FROM Person")
    fun deletePersonAll()

    @Query("DELETE FROM PersonCompany")
    fun deletePersonContactAll()

    @Query("DELETE FROM PersonEmployee")
    fun deletePersonEmployeeAll()

    @Query("DELETE FROM PersonEmployeeGroupLink")
    fun deletePersonEmployeeGroupLinkAll()

    @Query("DELETE FROM PersonVehicle")
    fun deletePersonVehicleAll()

    @Query("DELETE FROM PersonVehicleGroupLink")
    fun deletePersonVehicleGroupLinkAll()

    @Query("DELETE FROM Message")
    fun deleteMessageAll()


    @Query("SELECT * FROM Person ")
    suspend fun getAllPersons(): List<Person>

    @Query("SELECT * FROM Message ")
    suspend fun getAllMessages(): List<Message>

    @Query(
        "SELECT * " +
                "FROM Message " +
                "WHERE id in (SELECT Max(id) FROM Message GROUP BY personId) " +
                "ORDER BY date DESC"
    )
    suspend fun getAllMessagesGroups(): List<Message>

    @Query("SELECT * FROM Message WHERE personId = :id ORDER BY date")
    suspend fun getMessagesByPerson(id: Long): List<Message>

//    @Query("SELECT p.id, p.name FROM Person AS p LEFT JOIN ")
//    suspend fun getAllContactInfo(): List<ContactInfo>

    // Добавление Person в бд
//    @Insert
//    fun insertAll(vararg list: PersonCompany?)
//    fun insertAllPersonCompany(vararg list: PersonCompany?)

    // Удаление Person из бд
//    @Delete
//    fun delete(person: Person?)

//    @Query("DROP TABLE IF EXISTS 'PersonCompany'")
//    suspend fun dropPersonCompany()

/*    @Query("DELETE FROM PersonDepartment")
    suspend fun dropPersonDepartment()

    @Query("DELETE FROM PersonEmployeeGroup")
    suspend fun dropPersonEmployeeGroup()

    @Query("DELETE FROM PersonVehicleGroupType")
    suspend fun dropPersonVehicleGroupType()

    @Query("DELETE FROM PersonVehicleGroup")
    suspend fun dropPersonVehicleGroup()

    @Query("DELETE FROM Person")
    suspend fun dropPerson()

    @Query("DELETE FROM PersonContact")
    suspend fun dropPersonContact()

    @Query("DELETE FROM PersonEmployee")
    suspend fun dropPersonEmployee()

    @Query("DELETE FROM PersonVehicle")
    suspend fun dropPersonVehicle()

    @Query("DELETE FROM PersonVehicleGroupLink")
    suspend fun dropPersonVehicleGroupLink() */


/*    @Query("DELETE FROM PersonCompany")
    suspend fun deleteAllPersonCompany()

    @Query("DELETE FROM PersonDepartment")
    suspend fun deleteAllPersonDepartment()

    @Query("DELETE FROM PersonEmployeeGroup")
    suspend fun deleteAllPersonEmployeeGroup()

    @Query("DELETE FROM PersonVehicleGroupType")
    suspend fun deleteAllPersonVehicleGroupType()

    @Query("DELETE FROM PersonVehicleGroup")
    suspend fun deleteAllPersonVehicleGroup()

    @Query("DELETE FROM Person")
    suspend fun deleteAllPerson()

    @Query("DELETE FROM PersonContact")
    suspend fun deleteAllPersonContact()

    @Query("DELETE FROM PersonEmployee")
    suspend fun deleteAllPersonEmployee()

    @Query("DELETE FROM PersonVehicle")
    suspend fun deleteAllPersonVehicle()

    @Query("DELETE FROM PersonVehicleGroupLink")
    suspend fun deleteAllPersonVehicleGroupLink() */

    // Получение всех Person из бд
//    @get:Query("SELECT * FROM person")
//    val allPeople: List<Any?>?


    // Получение всех Person из бд с условием
//    @Query("SELECT * FROM person WHERE favoriteColor LIKE :color")
//    fun getAllPeopleWithFavoriteColor(color: String?): List<Person?>?
}