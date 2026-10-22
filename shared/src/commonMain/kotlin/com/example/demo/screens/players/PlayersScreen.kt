package com.example.demo.screens.players

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
import com.example.demo.data.remote.dto.PlayerDto
import com.example.demo.designsystem.components.AppButton
import com.example.demo.designsystem.components.AppLoadingIndicator
import com.example.demo.designsystem.components.PlayerCard

@Composable
fun PlayersScreen(
    component: PlayersComponent,
    modifier: Modifier = Modifier
) {
    val state by component.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth().then(modifier),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        state.errorMessage?.let { message ->
            Text(
                text = message,
                color = if (state.players.isEmpty()) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        when {
            state.isLoading && state.players.isEmpty() -> AppLoadingIndicator(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterHorizontally)
            )
            state.players.isEmpty() -> Text("No players available.", modifier = Modifier.weight(1f))
            else -> LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = state.players, key = PlayerDto::id) { player ->
                    PlayerCard(
                        player = player,
                        onClick = { component.onPlayerSelected(player) }
                    )
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
