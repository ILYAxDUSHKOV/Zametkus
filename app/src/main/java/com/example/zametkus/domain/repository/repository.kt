package com.example.zametkus.domain.repository

import androidx.lifecycle.LiveData
import com.example.zametkus.domain.zamData.ZamData

interface repository {
    fun getAll(): LiveData<List<ZamData>>
    suspend fun insertZam(zametka:ZamData)
    suspend fun updateZam(zametka:ZamData)
    suspend fun deleteZam(zametka: ZamData)
}