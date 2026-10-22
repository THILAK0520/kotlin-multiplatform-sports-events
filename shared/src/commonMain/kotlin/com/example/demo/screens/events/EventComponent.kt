
package com.example.demo.screens.events

import com.example.demo.data.remote.dto.EventDto
import kotlinx.coroutines.flow.StateFlow

interface EventComponent {

    val state: StateFlow<State>

    fun onRefresh()

    data class State(
        val events: List<EventDto> = emptyList(),
        val isLoading: Boolean = true,
        val errorMessage: String? = null
    )
}
