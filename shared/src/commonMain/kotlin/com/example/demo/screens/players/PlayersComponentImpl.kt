package com.example.demo.screens.players

import com.arkivanov.decompose.ComponentContext
import com.example.demo.data.remote.dto.PlayerDto
import com.example.demo.data.repository.PlayersRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayersComponentImpl(
    componentContext: ComponentContext,
    private val repository: PlayersRepositoryImpl,
    private val onPlayerSelected: (PlayerDto) -> Unit
) : PlayersComponent, ComponentContext by componentContext {

    private val componentScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _state = MutableStateFlow(PlayersComponent.State())
    override val state: StateFlow<PlayersComponent.State> = _state.asStateFlow()

    private var hasLoaded = false
    private var requestId = 0

    override fun onVisible() {
        if (!hasLoaded) {
            hasLoaded = true
            loadPlayers()
        }
    }

    override fun onRefresh() {
        hasLoaded = true
        loadPlayers()
    }

    override fun onPlayerSelected(player: PlayerDto) {
        onPlayerSelected.invoke(player)
    }

    private fun loadPlayers() {
        val thisRequest = ++requestId
        componentScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            try {
                val players = repository.getPlayers()
                if (thisRequest == requestId) {
                    _state.value = PlayersComponent.State(
                        players = players,
                        isLoading = false,
                        errorMessage = if (players.isEmpty()) "No players available." else null
                    )
                }
            } catch (exception: Exception) {
                if (thisRequest == requestId) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = if (_state.value.players.isEmpty()) {
                            "Unable to load players. Check your connection and try again."
                        } else {
                            "Couldn't refresh players. Showing the last loaded list."
                        }
                    )
                }
            }
        }
    }
}
