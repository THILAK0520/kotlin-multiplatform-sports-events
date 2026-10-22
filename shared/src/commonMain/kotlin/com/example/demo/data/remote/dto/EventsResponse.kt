package com.example.demo.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventsResponse(
    val events: List<EventDto> = emptyList(),
    val players: List<PlayerDto> = emptyList()
)
