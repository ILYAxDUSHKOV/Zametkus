package com.example.zametkus.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.zametkus.data.mappers.toZamData
import com.example.zametkus.data.mappers.toZamDto
import com.example.zametkus.data.zamDatabase.ZamDao
import com.example.zametkus.domain.repository.repository
import com.example.zametkus.domain.zamData.ZamData
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: ZamDao
):repository {
    override fun getAll(): LiveData<List<ZamData>> {
        return Transformations.map(dao.getAll()) {
                list -> list.map {
                item -> item.toZamData()
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
        Log.d("MyTag","RepositoryImpl отработал")
        dao.deleteZam(zametka.toZamDto())
    }
}