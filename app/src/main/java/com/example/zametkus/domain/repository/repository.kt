package com.example.zametkus.domain.repository

import androidx.lifecycle.LiveData
import com.example.zametkus.domain.zamData.GroupData
import com.example.zametkus.domain.zamData.HistoryData
import com.example.zametkus.domain.zamData.ZamData

interface repository {
    //ZamDto
    fun getAll(): LiveData<List<ZamData>>
    fun getAllByGroup(nameGroup: String): LiveData<List<ZamData>>
    suspend fun insertZam(zametka: ZamData)
    suspend fun updateZam(zametka: ZamData)
    suspend fun deleteZam(zametka: ZamData)

    //GroupDto
    fun getAllGroup(): LiveData<List<GroupData>>
    suspend fun insertGroup(group: GroupData)
    suspend fun deleteGroup(group: GroupData)

    //HistoryDto
    fun getAllHistory(): LiveData<List<HistoryData>>
    suspend fun insertHistory(history:HistoryData)
    suspend fun deleteHistory() //Называть методы конкретнее. Это метод для удаления всех записей.
    suspend fun deleteHis(history: HistoryData)
}