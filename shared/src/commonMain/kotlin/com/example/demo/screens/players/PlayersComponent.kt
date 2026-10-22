package com.example.demo.screens.players

import com.example.demo.data.remote.dto.PlayerDto
import kotlinx.coroutines.flow.StateFlow

interface PlayersComponent {
    val state: StateFlow<State>

    fun onVisible()
    fun onRefresh()
    fun onPlayerSelected(player: PlayerDto)

    data class State(
        val players: List<PlayerDto> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )
}
