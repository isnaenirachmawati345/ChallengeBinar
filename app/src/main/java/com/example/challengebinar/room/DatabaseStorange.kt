package com.example.challengebinar.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Laundry::class], version = 1)
abstract class DatabaseStorange():RoomDatabase(){
    abstract fun storeageDao() : UserDao
    abstract fun laundryDao() : LaundryDao

    companion object{
        private var INSTANCE: DatabaseStorange?=null

        fun getInstance(context: Context):DatabaseStorange? {
            if (INSTANCE == null) {
                synchronized(DatabaseStorange::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseStorange::class.java, "storange.db"
                    ).build()
                }

            }
            return INSTANCE

        }
        fun destoryInstance() {
            INSTANCE = null
        }
    }
}