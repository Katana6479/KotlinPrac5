package com.example.prac5.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDB (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name="First name")
    var firstName: String,
    @ColumnInfo(name="Last name")
    var lastName: String,
    @ColumnInfo(name="Maiden name")
    var maidenName: String,
    @ColumnInfo(name="Age")
    var age: Int
)