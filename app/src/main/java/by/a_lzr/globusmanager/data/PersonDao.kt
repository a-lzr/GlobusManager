package by.a_lzr.globusmanager.data

import androidx.paging.DataSource
import androidx.room.*
import by.a_lzr.globusmanager.data.entity.*

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

    @Insert
    fun addMessageFile(data: List<MessageFile>)

    @Query(
        "UPDATE Message SET status = 1 WHERE personId = :personId AND outType = 0 AND status = 0 AND id <= :id"
    )
    fun updateMessageStatus(personId: Long, id: Long): Int


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

//    @Query("SELECT * FROM Message ")
//    suspend fun getAllMessages(): List<Message>

    @Query(
        "WITH Links AS (SELECT Max(id) AS id, " +
                "Sum(CASE WHEN outType = 0 AND status = 0 THEN 1 ELSE 0 END) AS countNoRead, " +
                "Sum(CASE WHEN outType = 1 AND status = 0 THEN 1 ELSE 0 END) AS countNoDelivery " +
                "FROM Message GROUP BY personId) " +
                "SELECT m.*, src.countNoRead, src.countNoDelivery " +
                "FROM Links AS src " +
                "JOIN Message AS m ON m.id = src.id " +
                "ORDER BY m.id DESC"
    )
    fun getAllMessageGroups(): DataSource.Factory<Int, MessageGroup>

    @Query(
        "SELECT Min(id) FROM Message WHERE personId = :id AND outType = 0 AND status = 0"
    )
    fun getMessagesPosByPerson2(id: Long): Int

    @Query(
        "WITH Pos AS (SELECT Min(id) AS id FROM Message WHERE personId = :id AND outType = 0 AND status = 0) " +
                "SELECT COUNT(*) - 1 " +
                "FROM Message " +
                "WHERE personId = :id AND (id <= (SELECT id FROM Pos) OR (SELECT id FROM Pos) IS NULL)"
    )
    fun getMessagesPosByPerson(id: Long): Int

    @Query(
        "SELECT COUNT(*)  " +
                "FROM Message " +
                "WHERE outType = 0 AND status = 0"
    )
    fun getMessagesCountNotRead(): Int

    @Query("SELECT m.*, 1 AS countFiles " +
            "FROM Message AS m " +
//            "OUTER APPLY (SELECT COUNT(mf.id) AS countFiles FROM MessageFile AS mf WHERE mf.messageId = m.id) AS mf " +
            "WHERE m.personId = :id ORDER BY m.id")
    fun getMessagesDetailsByPerson(id: Long): DataSource.Factory<Int,  MessageDetail>

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