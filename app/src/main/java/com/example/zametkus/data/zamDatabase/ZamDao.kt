package com.example.zametkus.data.zamDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ZamDao {
    @Query("SELECT*from zamTable")
    fun getAll():LiveData<List<ZamDto>>

    @Insert
    suspend fun insertZam(zametka:ZamDto)

    @Update
    suspend fun updateZam(zametka:ZamDto)

    @Delete
    suspend fun deleteZam(zametka:ZamDto)
}