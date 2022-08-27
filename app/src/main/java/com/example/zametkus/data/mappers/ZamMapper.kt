package com.example.zametkus.data.mappers

import com.example.zametkus.data.zamDatabase.ZamDto
import com.example.zametkus.domain.zamData.ZamData

fun ZamDto.toZamData():ZamData{
    val mapperId = id
    val mapperTitle = title
    val mapperDescription = description
    return ZamData(
        id = mapperId,
        title = mapperTitle,
        description = mapperDescription
    )
}

fun ZamData.toZamDto():ZamDto{
    val mapperId = id
    val mapperTitle = title
    val mapperDescription = description
    return ZamDto(
        id = mapperId,
        title = mapperTitle,
        description = mapperDescription
    )
}