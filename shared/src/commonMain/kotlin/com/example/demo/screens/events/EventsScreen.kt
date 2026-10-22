
package com.example.demo.screens.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
fun EventsScreen(component: EventComponent, modifier: Modifier = Modifier) {
    val state by component.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        state.errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        when {
            state.isLoading && state.events.isEmpty() -> AppLoadingIndicator(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            )
            state.events.isEmpty() -> Text("No sports events available.", modifier = Modifier.weight(1f))
            else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
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

        AppButton(
            text = if (state.isLoading) "Refreshing…" else "Refresh",
            onClick = component::onRefresh,
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        )
    }
}
