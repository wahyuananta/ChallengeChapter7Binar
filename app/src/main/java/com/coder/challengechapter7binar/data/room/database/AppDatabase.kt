package com.coder.challengechapter7binar.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.coder.challengechapter7binar.data.room.dao.UserDao
import com.coder.challengechapter7binar.data.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase?{
            if (INSTANCE == null) {
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "AppDatabase.db").build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }

}