
package com.example.demo.navigation

import com.arkivanov.decompose.ComponentContext
import com.example.demo.data.repository.EventsRepositoryImpl
import com.example.demo.screens.events.EventComponent
import com.example.demo.screens.events.EventComponentImpl

interface RootComponent {
    val eventComponent: EventComponent

    class Factory(
        private val repository: EventsRepositoryImpl
    ) {
        fun create(componentContext: ComponentContext): RootComponent {
            return RootComponentImpl(
                componentContext = componentContext,
                repository = repository
            )
        }
    }
}

private class RootComponentImpl(
    componentContext: ComponentContext,
    repository: EventsRepositoryImpl
) : RootComponent, ComponentContext by componentContext {

    override val eventComponent: EventComponent =
        EventComponentImpl(
            componentContext = componentContext,
            repository = repository
        )
}
