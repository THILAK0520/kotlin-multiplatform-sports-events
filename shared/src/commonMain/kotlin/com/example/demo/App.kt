
package com.example.demo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.ComponentContext
import com.example.demo.data.local.EventsLocalDataSource
import com.example.demo.data.remote.EventsRemoteDataSource
import com.example.demo.data.remote.createHttpClient
import com.example.demo.data.repository.EventsRepositoryImpl
import com.example.demo.designsystem.theme.AppTheme
import com.example.demo.navigation.RootComponent
import com.example.demo.screens.events.EventsScreen
import com.example.demo.shared.database.AppDatabase

@Composable
fun App(
    database: AppDatabase,
    componentContext: ComponentContext
) {
    val rootComponent = remember(database, componentContext) {
        val client = createHttpClient()

        val repository = EventsRepositoryImpl(
            remoteDataSource = EventsRemoteDataSource(client),
            localDataSource = EventsLocalDataSource(database)
        )

        RootComponent.Factory(repository).create(componentContext)
    }

    AppTheme {
        EventsScreen(component = rootComponent.eventComponent)
    }
}
