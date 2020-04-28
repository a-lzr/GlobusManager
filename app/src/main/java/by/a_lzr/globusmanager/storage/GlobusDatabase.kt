package by.a_lzr.globusmanager.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import by.a_lzr.globusmanager.storage.entity.*


@Database(
    entities = [
        PersonCompany::class,
        PersonDepartment::class,
        PersonEmployeeGroup::class,
        PersonVehicleGroupType::class,
        PersonVehicleGroup::class,
        Person::class,
        PersonContact::class,
        PersonEmployee::class,
        PersonEmployeeGroupLink::class,
        PersonVehicle::class,
        PersonVehicleGroupLink::class,

        Message::class
    ],
    version = DB_VERSION
)
abstract class GlobusDatabase : RoomDatabase() {
    abstract val personDao: PersonDao

/*    companion object {
        val database by lazy {
            Room.databaseBuilder(GlobusApplication.appContext, GlobusDatabase::class.java, DB_NAME)
                .addMigrations(DatabaseHelper.MIGRATION_1_2)
//            .addCallback()
                .build()
        }

/*        @Volatile private var INSTANCE: GlobusDatabase? = null

        fun getInstance(): GlobusDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase().also { INSTANCE = it }
            }

        private fun buildDatabase() =
            Room.databaseBuilder(GlobusApplication.appContext, GlobusDatabase::class.java, DB_NAME)
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
//                        ioThread {
//                            getInstance(context).dataDao().insertData(PREPOPULATE_DATA)
//                        }
                    }
                })
                .build()

//        val PREPOPULATE_DATA = listOf(Data("1", "val"), Data("2", "val 2")) */
    } */
}