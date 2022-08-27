package com.example.zametkus.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zametkus.domain.repository.repository
import com.example.zametkus.domain.zamData.ZamData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZamViewModel @Inject constructor(
    val repository: repository
):ViewModel() {

    //Database
    val getAll:LiveData<List<ZamData>> = repository.getAll()
    fun insertZam(zametka:ZamData){
        viewModelScope.launch {
            repository.insertZam(zametka)
        }
    }
    fun updateZam(zametka: ZamData){
        viewModelScope.launch {
            repository.updateZam(zametka)
        }
    }
    fun deleteZam(zametka:ZamData){
        viewModelScope.launch {
            Log.d("MyTag","ViewModel отработала")
            repository.deleteZam(zametka)
        }
    }

    //AddDialog State
    val addDialogState = mutableStateOf(false)
}