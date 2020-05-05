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
        "SELECT Max(id) FROM MessageFile"
    )
    fun getMessageFileId(): Long

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

    @Query(
        "SELECT COUNT(*)  " +
                "FROM MessageFile"
    )
    fun getFilesCount(): Int

    @Query(
        "SELECT m.*, Count(mf.id) AS countFiles " +
                "FROM Message AS m " +
                "LEFT JOIN MessageFile AS mf ON mf.messageId = m.id " +
                "WHERE m.personId = :id " +
                "GROUP BY m.id, m.personId, m.message, m.date, m.outType, m.status " +
                "ORDER BY m.id"
    )
    fun getMessagesDetailsByPerson(id: Long): DataSource.Factory<Int, MessageDetail>
}