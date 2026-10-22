package com.example.demo.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    val id: Long,
    val name: String,
    val sport: String,
    val city: String
)
