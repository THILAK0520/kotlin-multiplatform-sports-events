package com.example.demo.data.repository

import com.example.demo.data.remote.EventsRemoteDataSource
import com.example.demo.data.remote.dto.PlayerDto

class PlayersRepositoryImpl(
    private val remoteDataSource: EventsRemoteDataSource
) {
    suspend fun getPlayers(): List<PlayerDto> =
        remoteDataSource.fetchEvents().players
}
