package com.example.zametkus.data.mappers

import com.example.zametkus.data.zamDatabase.ZamDto
import com.example.zametkus.domain.zamData.HistoryData
import com.example.zametkus.domain.zamData.ZamData
import java.text.SimpleDateFormat
import java.util.*

fun ZamDto.toZamData(): ZamData {
    val mapperId = id
    val mapperTitle = title
    val mapperDescription = description
    val ownerId = ownerId
    val color = color
    val time = date
    return ZamData(
        id = mapperId,
        title = mapperTitle,
        description = mapperDescription,
        ownerId = ownerId,
        color = color,
        date = time
    )
}

fun ZamData.toZamDto(): ZamDto {
    val mapperId = id
    val mapperTitle = title
    val mapperDescription = description
    val ownerId = ownerId
    val color = color
    val date = date
    return ZamDto(
        id = mapperId,
        title = mapperTitle,
        description = mapperDescription,
        ownerId = ownerId,
        color = color,
        date = date
    )
}

fun HistoryData.toZamData():ZamData {
    return ZamData(id, title, description, ownerId, color,date)
}

fun ZamData.toHistoryData():HistoryData{
    return HistoryData(
        id,
        title,
        description,
        ownerId,
        color,
        SimpleDateFormat("dd.MM").format(Date())
    )
}