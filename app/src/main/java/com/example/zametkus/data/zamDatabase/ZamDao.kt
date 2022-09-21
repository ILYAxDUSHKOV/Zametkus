package com.example.zametkus.data.zamDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ZamDao {
    // TODO Никитин запрос. Посмотреть потом.
    /*@Query("select z.* from zamdto z join groupdto g on g.id=z.gId where g.id=:id")
    fun getAllZamByGroup(id: Int):LiveData<List<ZamDto>>*/

    //Zam
    @Query("select * from zamdto z where z.ownerId=:name")
    fun getAllZamByGroup(name: String): LiveData<List<ZamDto>>
    @Query("SELECT*from zamdto")
    fun getAll(): LiveData<List<ZamDto>>
    @Insert
    suspend fun insertZam(zametka: ZamDto)
    @Update
    suspend fun updateZam(zametka: ZamDto)
    @Delete
    suspend fun deleteZam(zametka: ZamDto)

    //Group
    @Query("select*from groupdto")
    fun getAllGroup(): LiveData<List<GroupDto>>
    @Insert
    suspend fun insertGroup(group: GroupDto)
    @Delete
    suspend fun deleteGroup(group: GroupDto)

    //History
    @Query("select*from historydto")
    fun getAllHistory():LiveData<List<HistoryDto>>
    @Insert
    suspend fun insertHistory(history:HistoryDto)
    @Query("delete from historydto")
    suspend fun deleteAllHistory()
}

// Это для вставки изначальных данных в таблицу.
// Используется в самом файле базы данных.
// По сути обычная Insert функция
    //@Insert
    //fun insertInitialGroup(group: GroupDto)
