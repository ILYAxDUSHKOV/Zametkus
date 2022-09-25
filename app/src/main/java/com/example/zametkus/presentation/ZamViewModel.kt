package com.example.zametkus.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zametkus.domain.repository.repository
import com.example.zametkus.domain.zamData.GroupData
import com.example.zametkus.domain.zamData.HistoryData
import com.example.zametkus.domain.zamData.ZamData
import com.example.zametkus.presentation.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

// Никитин способ получения списка заметок по названию группы
//val getAllByGroup: (String) -> LiveData<List<ZamData>> =
        //{ name -> repository.getAllByGroup(nameGroup = name) }

@HiltViewModel
class ZamViewModel @Inject constructor(
    val repository: repository
) : ViewModel() {

    //Dialog State AddNote
    val addDialogState = mutableStateOf(false)
    //Dialog State EditNote
    val editDialogState = mutableStateOf(false)
    var currentNote = ZamData(0, "", "", "","","")
    fun openEditDialog(zametka: ZamData) {
        currentNote = zametka
        editDialogState.value = true
    }
    //Dialog State AddGroup
    var addGroupDialogState = mutableStateOf(false)

    val ownerId = mutableStateOf("All notes")

    //Zam
    var getAll: LiveData<List<ZamData>> = repository.getAll()
    fun getAllZamByGroup(name:String):LiveData<List<ZamData>>{
        return repository.getAllByGroup(name)
    }
    fun insertZam(zametka: ZamData) {
        viewModelScope.launch {
            repository.insertZam(zametka)
        }
    }
    fun updateZam(zametka: ZamData) {
        viewModelScope.launch {
            repository.updateZam(zametka)
        }
    }
    fun deleteZam(zametka: ZamData) {
        viewModelScope.launch {
            repository.deleteZam(zametka)
        }
    }

    //Group
    val getAllGroup: LiveData<List<GroupData>> = repository.getAllGroup()
    fun insertGroup(group: GroupData) {
        viewModelScope.launch {
            repository.insertGroup(group = group)
        }
    }
    fun deleteGroup(group: GroupData) {
        viewModelScope.launch {
            repository.deleteGroup(group = group)
        }
    }

    //History
    val getAllHistory: LiveData<List<HistoryData>> = repository.getAllHistory()
    fun insertHistory(history: HistoryData){
        viewModelScope.launch {
            repository.insertHistory(history = history)
        }
    }
    fun deleteAllHistory(){
        viewModelScope.launch {
            repository.deleteHistory()
        }
    }
    fun deleteHistory(history: HistoryData){
        viewModelScope.launch {
            repository.deleteHis(history)
        }
    }

    //Color Converter
    fun colorConverter(color: String): Color {
        return when (color) {
            "Red" -> myRed
            "Blue" -> myBlue
            "Green" -> myGreen
            "Orange" -> myOrange
            "Purple" -> myPurple
            else -> Color.Black
        }
    }

    //Get current date
    fun getCurrentDate():String{
        return SimpleDateFormat("dd.MM").format(Date())
    }

}