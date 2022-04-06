package com.example.challengebinar.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class LaundryDatabase : RoomDatabase () {
    abstract fun laudryDao():LaundryDao

    companion object {
        private var INSTANCE : LaundryDatabase? = null

        fun getInstance(context: Context) : LaundryDatabase? {
            if (INSTANCE == null){
                synchronized(LaundryDatabase::class){
                INSTANCE = Room.databaseBuilder(context.applicationContext, LaundryDatabase::class.java,"Laundry.db").build()
            }
        }
        return INSTANCE

    }
    fun destroyInstance(){
        INSTANCE = null
    }
}

}