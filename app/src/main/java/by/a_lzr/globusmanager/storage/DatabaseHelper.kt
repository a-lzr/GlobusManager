package by.a_lzr.globusmanager.storage

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import by.a_lzr.globusmanager.GlobusApplication


const val DB_NAME = "globus.db"
const val DB_VERSION = 1

object DatabaseHelper {

    val db by lazy {
        Room.databaseBuilder(GlobusApplication.appContext, GlobusDatabase::class.java, DB_NAME)
//            .addMigrations(MIGRATION_1_2)
//            .addCallback()
            .build()
    }

/*    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE `Fruit` (`id` INTEGER, "
                        + "`name` TEXT, PRIMARY KEY(`id`))"
            )
        }
    }

    class SQLHelper : SQLiteOpenHelper(GlobusApplication.appContext, DB_NAME, null, DB_VERSION) {
        override fun onCreate(db: SQLiteDatabase?) {
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        }
    } */

/*    val database by lazy {
        Room.databaseBuilder(
            context,
            Database::class.java,
            DB_NAME
        ).build()
    }

    companion object {
        fun getInstance(context: Context) = DatabaseHelper(context)
    } */


//    database = Room.databaseBuilder(this, AppDatabase.class, "database")
//    .build();

    /*class DBSingleton(context: Context) {
        val database by lazy {
            Room.databaseBuilder(
                context,
                Database::class.java, DB_NAME
            ).build()
        }

        companion object {
            fun getInstance() = DBSingleton(GlobusApplication.appContext)
        }
    } */
//    fun getInstance() = PinguinSingleton(context)

//    fun getInstance(context: Context) = Lesson6BookObject(context)

    private fun dropTable() {
        //db.execSQL("DROP TABLE IF EXISTS ' " + DATABASE_TABLE + "'");
    }

//    private fun clearAll() {
//        db.close()
//        GlobusApplication.appContext.deleteDatabase(DB_NAME)
//    }
}