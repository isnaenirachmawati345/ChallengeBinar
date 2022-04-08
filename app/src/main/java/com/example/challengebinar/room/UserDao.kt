package com.example.challengebinar.room

import android.provider.ContactsContract
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface UserDao {
    @Query(value = "SELECT * FROM user WHERE email like :email and password like :password")
    fun userCek(email:String,password:String):Boolean
    @Insert(onConflict = REPLACE)
    fun addUser(user: User): Long
}