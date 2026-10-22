package com.example.demo.data.mapper

import com.example.demo.data.remote.dto.EventDto

fun EventDto.toDatabaseId(): Long = id.toLong()

fun EventDto.toDatabaseTitle(): String = title

fun EventDto.toDatabaseCategory(): String = category

fun EventDto.toDatabaseOrganizer(): String = organizer

fun EventDto.toDatabaseDescription(): String = description

fun EventDto.toDatabaseDate(): String = date

fun EventDto.toDatabaseLocation(): String = location
