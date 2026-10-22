
package com.example.demo.screens.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demo.designsystem.components.AppButton
import com.example.demo.designsystem.components.AppLoadingIndicator
import com.example.demo.designsystem.components.EventCard

@Composable
fun EventsScreen(component: EventComponent) {
    val state by component.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Sports Events",
            style = MaterialTheme.typography.headlineMedium
        )

        AppButton(
            text = "Refresh",
            onClick = component::onRefresh,
            modifier = Modifier.fillMaxWidth()
        )

        if (state.isLoading && state.events.isEmpty()) {
            AppLoadingIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        state.errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (state.events.isEmpty() && !state.isLoading) {
            Text("No sports events available.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = state.events,
                    key = { it.id }
                ) { event ->
                    EventCard(event = event)
                }
            }
        }
    }
}
