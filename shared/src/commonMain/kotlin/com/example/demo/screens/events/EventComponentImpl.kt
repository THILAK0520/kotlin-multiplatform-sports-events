
package com.example.demo.screens.events

import com.arkivanov.decompose.ComponentContext
import com.example.demo.data.remote.dto.EventDto
import com.example.demo.data.repository.EventsRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventComponentImpl(
    componentContext: ComponentContext,
    private val repository: EventsRepositoryImpl
) : EventComponent, ComponentContext by componentContext {

    private val componentScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow(EventComponent.State())
    override val state: StateFlow<EventComponent.State> =
        _state.asStateFlow()

    init {
        loadEvents()
    }

    override fun onRefresh() {
        loadEvents()
    }

    private fun loadEvents() {
        componentScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val databaseEvents = repository.getEvents()

                val events = databaseEvents.map { event ->
                    EventDto(
                        id = event.id,
                        title = event.title,
                        category = event.category,
                        organizer = event.organizer,
                        description = event.description,
                        date = event.date,
                        location = event.location
                    )
                }

                _state.value = EventComponent.State(
                    events = events,
                    isLoading = false,
                    errorMessage = if (events.isEmpty()) {
                        "No sports events available."
                    } else {
                        null
                    }
                )
            } catch (exception: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Unable to load sports events."
                )
            }
        }
    }
}
