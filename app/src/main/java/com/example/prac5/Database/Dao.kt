package com.example.prac5.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    suspend fun insertItem(item: UserDB)
    @Query("SELECT * FROM users")
    fun getItems(): Flow<List<UserDB>>
}