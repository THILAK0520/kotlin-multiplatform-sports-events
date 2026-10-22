package com.example.demo.navigation

import com.arkivanov.decompose.ComponentContext
import com.example.demo.data.remote.dto.PlayerDto
import com.example.demo.data.repository.EventsRepositoryImpl
import com.example.demo.data.repository.PlayersRepositoryImpl
import com.example.demo.screens.events.EventComponent
import com.example.demo.screens.events.EventComponentImpl
import com.example.demo.screens.players.PlayersComponent
import com.example.demo.screens.players.PlayersComponentImpl
import com.example.demo.screens.players.details.PlayerDetailsComponent
import com.example.demo.screens.players.details.PlayerDetailsComponentImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface RootComponent {
    val eventComponent: EventComponent
    val playersComponent: PlayersComponent
    val playerDetailsComponent: PlayerDetailsComponent?
    val state: StateFlow<State>

    fun onTabSelected(tab: Tab)
    fun onPlayerSelected(player: PlayerDto)
    fun onPlayerDetailsBack()

    enum class Tab { EVENTS, PLAYERS }

    data class State(
        val selectedTab: Tab = Tab.EVENTS,
        val isPlayerDetailsVisible: Boolean = false
    )

    class Factory(
        private val eventsRepository: EventsRepositoryImpl,
        private val playersRepository: PlayersRepositoryImpl
    ) {
        fun create(componentContext: ComponentContext): RootComponent =
            RootComponentImpl(componentContext, eventsRepository, playersRepository)
    }
}

private class RootComponentImpl(
    componentContext: ComponentContext,
    eventsRepository: EventsRepositoryImpl,
    playersRepository: PlayersRepositoryImpl
) : RootComponent, ComponentContext by componentContext {

    override val eventComponent: EventComponent =
        EventComponentImpl(componentContext, eventsRepository)

    override val playersComponent: PlayersComponent =
        PlayersComponentImpl(componentContext, playersRepository, ::onPlayerSelected)

    private val _state = MutableStateFlow(RootComponent.State())
    override val state: StateFlow<RootComponent.State> = _state.asStateFlow()

    override var playerDetailsComponent: PlayerDetailsComponent? = null
        private set

    override fun onTabSelected(tab: RootComponent.Tab) {
        playerDetailsComponent = null
        _state.value = _state.value.copy(selectedTab = tab, isPlayerDetailsVisible = false)
        if (tab == RootComponent.Tab.PLAYERS) playersComponent.onVisible()
    }

    override fun onPlayerSelected(player: PlayerDto) {
        playerDetailsComponent = PlayerDetailsComponentImpl(player, ::onPlayerDetailsBack)
        _state.value = _state.value.copy(isPlayerDetailsVisible = true)
    }

    override fun onPlayerDetailsBack() {
        playerDetailsComponent = null
        _state.value = _state.value.copy(isPlayerDetailsVisible = false)
    }
}
