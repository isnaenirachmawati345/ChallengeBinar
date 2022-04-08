package com.example.challengebinar.room

import android.provider.ContactsContract
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface LaundryDao {
    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    fun logIn(email:String, password: Int): Boolean

    @Query("SELECT * FROM laundry")
    fun getAllLaundry() : List<Laundry>

    @Insert (onConflict = REPLACE)
    fun insertLaundry(laundry: Laundry):Long

    @Update
    fun updateLaundry(laudry : Laundry?): Int

    @Delete
    fun deleteLaundry(laundry: Laundry): Int
}