package com.example.challengebinar.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Laundry(
     @PrimaryKey(autoGenerate = true) var id: Int?,
     @ColumnInfo (name = "name") var name: String,
     @ColumnInfo (name = "date") var date: String,
     @ColumnInfo (name = "address") var address: String,
     @ColumnInfo (name = "jenis") var jenis: String,
     @ColumnInfo (name = "jumlah") var jumlah: String,
) : Parcelable