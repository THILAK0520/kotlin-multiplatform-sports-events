package com.example.demo.data.local

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import com.example.demo.data.remote.dto.PlayerDto
import com.example.demo.shared.database.AppDatabase
import com.example.demo.shared.database.Player

class PlayersLocalDataSource(
    private val database: AppDatabase
) {
    private val queries = database.playerQueries

    suspend fun getAllPlayers(): List<Player> =
        queries.selectAllPlayers().awaitAsList()

    suspend fun getPlayerById(id: Long): Player? =
        queries.selectPlayerById(id).awaitAsOneOrNull()

    suspend fun replacePlayers(players: List<PlayerDto>) {
        database.transaction {
            queries.deleteAllPlayers()
            players.forEach { player ->
                queries.insertPlayer(
                    id = player.id,
                    name = player.name,
                    sport = player.sport,
                    city = player.city
                )
            }
        }
    }
}
