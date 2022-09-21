package com.example.zametkus.domain.zamData

data class ZamData(
    var id: Int,
    val title: String,
    val description: String,
    val ownerId: String,
    val color:String,
    val date:String
)
