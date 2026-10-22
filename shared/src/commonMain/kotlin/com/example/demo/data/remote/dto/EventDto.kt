package com.example.demo.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: Long,
    val title: String,
    val category: String,
    val organizer: String,
    val description: String,
    val date: String,
    val location: String
)
