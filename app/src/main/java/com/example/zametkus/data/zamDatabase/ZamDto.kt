package com.example.zametkus.data.zamDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zamTable")
data class ZamDto(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    val title:String,
    val description:String
)
