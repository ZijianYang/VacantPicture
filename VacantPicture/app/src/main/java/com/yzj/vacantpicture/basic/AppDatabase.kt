package com.yzj.vacantpicture.basic

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yzj.vacantpicture.database.dao.UserDao
import com.yzj.vacantpicture.database.entity.User

@Database(version = 1, entities = [User::class],exportSchema=false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        private var instance: AppDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "app_database",)
                .fallbackToDestructiveMigration()
                .build().apply {
                    instance = this
                }
        }
    }
}