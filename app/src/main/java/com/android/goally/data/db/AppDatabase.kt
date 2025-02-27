package com.android.goally.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.goally.data.db.dao.GeneralDao
import com.android.goally.data.db.entities.token.Authentication
import com.android.goally.data.model.api.response.converters.ActivitiesConverter
import com.android.goally.data.model.api.response.converters.ScheduleConverter
import com.android.goally.data.model.api.response.copilot.Routines
import com.android.goally.util.LogUtil
import com.getgoally.learnerapp.data.db.DateConverter
import com.getgoally.learnerapp.data.db.StringListConvert


@Database(
    entities = [Authentication::class, Routines::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, StringListConvert::class, ActivitiesConverter::class, ScheduleConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getGeneralDao(): GeneralDao

    companion object {
        const val DATABASE_NAME = "Goally.db"

        var rdc: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                LogUtil.i("Database created!")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                // do something every time database is open
            }
        }

//        val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                // do something when database is migrated
//            }
//        }

    }
}