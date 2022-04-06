package com.example.challengebinar.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

interface LaundryDao {
    @Query("SELECT * FROM laundry")
    fun getAllLaundry() : List<Laundry>

    @Insert (onConflict = REPLACE)
    fun insertLaundry(laundry: Laundry):Long

    @Update
    fun updateLaundry(laudry : Laundry?): Int

    @Delete
    fun deleteLaundry(laundry: Laundry): Int
}