package com.example.zametkus.data.zamDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GroupDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val color: String
)

@Entity
data class ZamDto(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    val description: String,
    val ownerId: String,
    val color:String,
    val date:String
)

@Entity
data class HistoryDto(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    val description: String,
    val ownerId: String,
    val color:String,
    val date:String
)
