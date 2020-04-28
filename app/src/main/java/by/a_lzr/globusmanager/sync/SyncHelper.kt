package by.a_lzr.globusmanager.sync

import android.annotation.SuppressLint
import by.a_lzr.globusmanager.storage.DatabaseHelper
import by.a_lzr.globusmanager.storage.entity.*
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object SyncHelper {

    //    val database = DatabaseHelper.getInstance(this).database
    //private val db = DatabaseHelper.db

    suspend fun syncAll(): Boolean {
        clear()
//        sleep(2000)
        loadPersons()
//        sleep(2000)
        loadMessages()
//        sleep(2000)
//        val deferred1 = async(Dispatchers.Default) { getFirstValue() }
//        val deferred2 = async(Dispatchers.IO) { getSecondValue() }
//        useValues(deferred1.await(), deferred2.await())
        return true
    }

    fun updatePerson() {
        clearPerson()
        loadPersons()
    }

    fun updateMessages() {
        clearMessages()
        loadMessages()
    }

    private fun clear() {
        clearPerson()
        clearMessages()
    }

    private fun clearPerson() {
        with(DatabaseHelper.db.personDao) {
            deletePersonVehicleGroupLinkAll()
            deletePersonVehicleAll()
            deletePersonEmployeeGroupLinkAll()
            deletePersonEmployeeAll()
            deletePersonContactAll()
            deletePersonAll()
            deletePersonVehicleGroupAll()
            deletePersonVehicleGroupTypeAll()
            deletePersonEmployeeGroupAll()
            deletePersonDepartmentAll()
            deletePersonCompanyAll()
        }
    }

    private fun clearMessages() {
        with(DatabaseHelper.db.personDao) {
            deleteMessageAll()
        }
    }

//    private fun dropTable(name: String) {
//        DatabaseHelper.db.queryExecutor.execute("DROP TABLE IF EXISTS '" + name + "'" )
//    }

    private fun loadPersons() {
        val companies = ArrayList<PersonCompany>()
        companies.add(PersonCompany(1, "Компания 1", null, null))
        companies.add(PersonCompany(2, "Компания 2", null, null))
        companies.add(PersonCompany(3, "Компания 3", null, null))
        companies.add(PersonCompany(4, "Компания 4", null, null))
        DatabaseHelper.db.personDao.addPersonCompany(companies)

        val departments = ArrayList<PersonDepartment>()
        departments.add(PersonDepartment(1, "Отдел импорта"))
        departments.add(PersonDepartment(2, "Отдел экспорта"))
        departments.add(PersonDepartment(3, "Отдел продаж"))
        departments.add(PersonDepartment(4, "Отдел эксплуатации"))
        DatabaseHelper.db.personDao.addPersonDepartment(departments)

        val employeeGroups = ArrayList<PersonEmployeeGroup>()
        employeeGroups.add(PersonEmployeeGroup(1, "Оперативный экспедитор"))
        employeeGroups.add(PersonEmployeeGroup(2, "Эксплутация Колонна 1"))
        employeeGroups.add(PersonEmployeeGroup(3, "Эксплутация Колонна 2"))
        employeeGroups.add(PersonEmployeeGroup(4, "Эксплутация Колонна 3"))
        employeeGroups.add(PersonEmployeeGroup(5, "Эксплутация Колонна 4"))
        employeeGroups.add(PersonEmployeeGroup(6, "План импорт"))
        employeeGroups.add(PersonEmployeeGroup(7, "План экспорт'"))
        employeeGroups.add(PersonEmployeeGroup(8, "СТО механик"))
        employeeGroups.add(PersonEmployeeGroup(9, "Страхование"))
        employeeGroups.add(PersonEmployeeGroup(10, "ЭПИ"))
        DatabaseHelper.db.personDao.addPersonEmployeeGroup(employeeGroups)

        val vehicleGroupTypes = ArrayList<PersonVehicleGroupType>()
        vehicleGroupTypes.add(PersonVehicleGroupType(1, "Колонна"))
        DatabaseHelper.db.personDao.addPersonVehicleGroupType(vehicleGroupTypes)

        val vehicleGroups = ArrayList<PersonVehicleGroup>()
        vehicleGroups.add(PersonVehicleGroup(1, 1, "Колонна 1"))
        vehicleGroups.add(PersonVehicleGroup(2, 1, "Колонна 2"))
        vehicleGroups.add(PersonVehicleGroup(3, 1, "Колонна 3"))
        vehicleGroups.add(PersonVehicleGroup(4, 1, "Колонна 4"))
        DatabaseHelper.db.personDao.addPersonVehicleGroup(vehicleGroups)

        val persons = ArrayList<Person>()
        persons.add(Person(1L, "Лазерко Александр", null))
        persons.add(Person(2L, "Петрова Анна", null))
        persons.add(Person(3L, "Пупкин Василий", null))
        persons.add(Person(4L, "Иванов Иван", null))
        persons.add(Person(5L, "Сидоров Илья", null))
        persons.add(Person(6L, "Васечкин Василий", null))
        persons.add(Person(7L, "Дежурный", null))
        persons.add(Person(8L, "База 1", null))
        persons.add(Person(9L, "База 2", null))
        persons.add(Person(10L, "База 3", null))
        persons.add(Person(11L, "База 4", null))
        persons.add(Person(12L, "AP 5567-5", null))
        persons.add(Person(13L, "AP 3367-7", null))
        persons.add(Person(14L, "AP 5542-5", null))
        persons.add(Person(15L, "AP 3457-5", null))
        persons.add(Person(16L, "AP 1569-5", null))
        persons.add(Person(17L, "AP 2264-5", null))
        DatabaseHelper.db.personDao.addPerson(persons)

        val contacts = ArrayList<PersonContact>()
        contacts.add(PersonContact(1, 1L, 1, "+375336391519"))
        contacts.add(PersonContact(2, 1L, 2, "test.alexander666@gmail.com"))
        contacts.add(PersonContact(3, 1L, 3, "+375336391519"))
        contacts.add(PersonContact(4, 7L, 1, "+375297798825"))
        contacts.add(PersonContact(5, 8L, 1, "+375297586900"))
        contacts.add(PersonContact(6, 9L, 1, "+375333060055"))
        contacts.add(PersonContact(7, 10L, 1, "+375336950055"))
        contacts.add(PersonContact(8, 11L, 1, "+375333730055"))
        DatabaseHelper.db.personDao.addPersonContact(contacts)

        val employees = ArrayList<PersonEmployee>()
        employees.add(PersonEmployee(1L, 1, null, null, null, null))
        employees.add(PersonEmployee(2L, 1, null, null, null, null))
        employees.add(PersonEmployee(3L, 1, null, null, null, null))
        employees.add(PersonEmployee(4L, 1, null, null, null, null))
        employees.add(PersonEmployee(5L, 2, null, null, null, null))
        employees.add(PersonEmployee(6L, 2, null, null, null, null))
        employees.add(PersonEmployee(7L, 1, null, null, null, null))
        employees.add(PersonEmployee(8L, 1, null, null, null, null))
        employees.add(PersonEmployee(9L, 2, null, null, null, null))
        employees.add(PersonEmployee(10L, 3, null, null, null, null))
        employees.add(PersonEmployee(11L, 4, null, null, null, null))
        DatabaseHelper.db.personDao.addPersonEmployee(employees)

        val vehicles = ArrayList<PersonVehicle>()
        vehicles.add(PersonVehicle(12L, 1, null, null))
        vehicles.add(PersonVehicle(13L, 1, null, null))
        vehicles.add(PersonVehicle(14L, 1, null, null))
        vehicles.add(PersonVehicle(15L, 1, null, null))
        vehicles.add(PersonVehicle(16L, 2, null, null))
        vehicles.add(PersonVehicle(17L, 2, null, null))
        DatabaseHelper.db.personDao.addPersonVehicle(vehicles)

        val vehicleLinks = ArrayList<PersonVehicleGroupLink>()
        vehicleLinks.add(PersonVehicleGroupLink(1, 12L))
        vehicleLinks.add(PersonVehicleGroupLink(1, 13L))
        vehicleLinks.add(PersonVehicleGroupLink(1, 14L))
        vehicleLinks.add(PersonVehicleGroupLink(2, 15L))
        vehicleLinks.add(PersonVehicleGroupLink(3, 16L))
        vehicleLinks.add(PersonVehicleGroupLink(3, 17L))
        DatabaseHelper.db.personDao.addPersonVehicleGroupLink(vehicleLinks)
    }

    @SuppressLint("SimpleDateFormat")
    private fun loadMessages() {
        val messages = ArrayList<Message>()
        messages.add(Message(1L, 1L, "Привет", convertDateToLong("2020-05-01 08:12:12"), false, 1))
        messages.add(Message(2L, 1L, "Сбрось телефон AP 5542-5", convertDateToLong("2020-05-01 08:12:14"), false, 1))
        messages.add(Message(3L, 1L, "Привет", convertDateToLong("2020-05-01 08:13:18"), true, 1))
        messages.add(Message(4L, 1L, "+37529XXXXXXX", convertDateToLong("2020-05-01 08:13:18"), true, 1))
        messages.add(Message(5L, 1L, "Спасибо", convertDateToLong("2020-05-01 08:14:50"), false, 1))
        messages.add(Message(6L, 1L, "Привет", convertDateToLong("2020-05-02 08:12:12"), false, 1))
        messages.add(Message(7L, 1L, "Сбрось телефон AP 5542-5", convertDateToLong("2020-05-02 08:12:14"), false, 1))
        messages.add(Message(8L, 1L, "Привет", convertDateToLong("2020-05-02 08:13:18"), true, 1))
        messages.add(Message(9L, 1L, "+37529XXXXXXX", convertDateToLong("2020-05-02 08:13:18"), true, 1))
        messages.add(Message(10L, 1L, "Спасибо", convertDateToLong("2020-05-02 08:14:50"), false, 1))
        messages.add(Message(11L, 1L, "Привет", convertDateToLong("2020-05-03 08:12:12"), false, 1))
        messages.add(Message(12L, 1L, "Сбрось телефон AP 5542-5", convertDateToLong("2020-05-03 08:12:14"), false, 1))
        messages.add(Message(13L, 1L, "Привет", convertDateToLong("2020-05-03 08:13:18"), true, 1))
        messages.add(Message(14L, 1L, "+37529XXXXXXX", convertDateToLong("2020-05-03 08:13:18"), true, 1))
        messages.add(Message(15L, 1L, "Спасибо", convertDateToLong("2020-05-03 08:14:50"), false, 1))
        messages.add(Message(16L, 1L, "Привет", convertDateToLong("2020-05-04 08:12:12"), false, 1))
        messages.add(Message(17L, 1L, "Сбрось телефон AP 5542-5", convertDateToLong("2020-05-04 08:12:14"), false, 1))
        messages.add(Message(18L, 1L, "Привет", convertDateToLong("2020-05-04 08:13:18"), true, 1))
        messages.add(Message(19L, 1L, "+37529XXXXXXX", convertDateToLong("2020-05-04 08:13:18"), true, 1))
        messages.add(Message(20L, 1L, "Спасибо", convertDateToLong("2020-05-04 08:14:50"), false, 1))
        messages.add(Message(21L, 1L, "Привет", convertDateToLong("2020-05-05 08:12:12"), false, 1))
        messages.add(Message(22L, 1L, "Сбрось телефон AP 5542-5", convertDateToLong("2020-05-05 08:12:14"), false, 1))
        messages.add(Message(23L, 1L, "Привет", convertDateToLong("2020-05-05 08:13:18"), true, 1))
        messages.add(Message(24L, 1L, "+37529XXXXXXX", convertDateToLong("2020-05-05 08:13:18"), true, 1))
        messages.add(Message(25L, 1L, "Спасибо", convertDateToLong("2020-05-05 08:14:50"), false, 1))
        messages.add(Message(26L, 1L, "Привет", convertDateToLong("2020-05-06 08:12:12"), false, 1))
        messages.add(Message(27L, 1L, "Сбрось телефон AP 5542-5", convertDateToLong("2020-05-06 08:12:14"), false, 1))
        messages.add(Message(28L, 1L, "Привет", convertDateToLong("2020-05-06 08:13:18"), true, 1))
        messages.add(Message(29L, 1L, "+37529XXXXXXX", convertDateToLong("2020-05-06 08:13:18"), true, 1))
        messages.add(Message(30L, 1L, "Спасибо", convertDateToLong("2020-05-06 08:14:50"), false, 1))
        messages.add(Message(31L, 1L, "Привет", convertDateToLong("2020-05-07 08:12:12"), false, 1))
        messages.add(Message(32L, 1L, "Сбрось телефон AP 5542-5", convertDateToLong("2020-05-07 08:12:14"), false, 1))
        messages.add(Message(33L, 1L, "Привет", convertDateToLong("2020-05-07 08:13:18"), true, 1))
        messages.add(Message(34L, 1L, "+37529XXXXXXX", convertDateToLong("2020-05-07 08:13:18"), true, 1))
        messages.add(Message(35L, 1L, "Спасибо", convertDateToLong("2020-05-07 08:14:50"), false, 0))
        messages.add(Message(36L, 2L, "Привет", convertDateToLong("2020-05-08 08:12:12"), false, 0))
        messages.add(Message(37L, 2L, "Сбрось телефон AP 5542-5", convertDateToLong("2020-05-08 08:12:14"), false, 0))
        messages.add(Message(38L, 2L, "Привет", convertDateToLong("2020-05-08 08:13:18"), true, 1))
        messages.add(Message(39L, 2L, "+37529XXXXXXX", convertDateToLong("2020-05-08 08:13:18"), true, 1))
        messages.add(Message(40L, 2L, "Спасибо", convertDateToLong("2020-05-08 08:14:50"), false, 0))
        messages.add(Message(41L, 2L, "Привет", convertDateToLong("2020-05-09 08:12:12"), false, 0))
        messages.add(Message(42L, 2L, "Сбрось телефон AP 5542-5", convertDateToLong("2020-05-09 08:12:14"), false, 0))
        DatabaseHelper.db.personDao.addMessage(messages)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateToLong(date: String): Long {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).time
    }
}