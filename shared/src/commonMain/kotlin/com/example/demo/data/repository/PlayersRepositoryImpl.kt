package com.example.demo.data.repository

import com.example.demo.data.local.PlayersLocalDataSource
import com.example.demo.data.remote.EventsRemoteDataSource
import com.example.demo.data.remote.dto.PlayerDto
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayersRepositoryImpl(
    private val remoteDataSource: EventsRemoteDataSource,
    private val localDataSource: PlayersLocalDataSource
) {
    suspend fun getPlayers(): List<PlayerDto> {
        val cachedPlayers = try {
            withContext(Dispatchers.Default) {
                localDataSource.getAllPlayers().map { player ->
                    PlayerDto(
                        id = player.id,
                        name = player.name,
                        sport = player.sport,
                        city = player.city
                    )
                }
            }
        } catch (exception: Exception) {
            if (exception is CancellationException) throw exception
            println("Player cache read failed: ${exception.message}")
            emptyList()
        }

        return try {
            val remotePlayers = remoteDataSource.fetchEvents().players
            try {
                withContext(Dispatchers.Default) {
                    localDataSource.replacePlayers(remotePlayers)
                }
            } catch (exception: Exception) {
                if (exception is CancellationException) throw exception
                println("Player cache update failed: ${exception.message}")
            }
            remotePlayers
        } catch (exception: Exception) {
            if (exception is CancellationException) throw exception
            println("Players API request failed: ${exception.message}")
            if (cachedPlayers.isNotEmpty()) cachedPlayers else throw exception
        }
    }
}
