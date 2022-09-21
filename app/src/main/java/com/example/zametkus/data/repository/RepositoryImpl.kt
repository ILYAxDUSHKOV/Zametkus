package com.example.zametkus.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.zametkus.data.mappers.*
import com.example.zametkus.data.zamDatabase.ZamDao
import com.example.zametkus.domain.repository.repository
import com.example.zametkus.domain.zamData.GroupData
import com.example.zametkus.domain.zamData.HistoryData
import com.example.zametkus.domain.zamData.ZamData
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: ZamDao
) : repository {

    //ZamDto
    override fun getAll(): LiveData<List<ZamData>> {
        return Transformations.map(dao.getAll()) { list ->
            list.map { item ->
                item.toZamData()
            }
        }
    }
    override fun getAllByGroup(nameGroup: String): LiveData<List<ZamData>> {
        return Transformations.map(dao.getAllZamByGroup(name = nameGroup)) { list ->
            list.map { item ->
                item.toZamData()
            }
        }
    }
    override suspend fun insertZam(zametka: ZamData) {
        dao.insertZam(zametka.toZamDto())
    }
    override suspend fun updateZam(zametka: ZamData) {
        dao.updateZam(zametka.toZamDto())
    }
    override suspend fun deleteZam(zametka: ZamData) {
        dao.deleteZam(zametka.toZamDto())
    }

    //GroupDto
    override fun getAllGroup(): LiveData<List<GroupData>> {
        return Transformations.map(dao.getAllGroup()) { list ->
            list.map { item ->
                item.toGroupData()
            }
        }
    }
    override suspend fun insertGroup(group: GroupData) {
        dao.insertGroup(group = group.toGroupDto())
    }
    override suspend fun deleteGroup(group: GroupData) {
        dao.deleteGroup(group = group.toGroupDto())
    }

    //HistoryDto
    override fun getAllHistory(): LiveData<List<HistoryData>> {
        return Transformations.map(dao.getAllHistory()){ list ->
            list.map { item ->
                item.toHistoryData()
            }
        }
    }
    override suspend fun insertHistory(history: HistoryData) {
        dao.insertHistory(history = history.toHistoryDto())
    }

    override suspend fun deleteHistory() {
        dao.deleteAllHistory()
    }
}