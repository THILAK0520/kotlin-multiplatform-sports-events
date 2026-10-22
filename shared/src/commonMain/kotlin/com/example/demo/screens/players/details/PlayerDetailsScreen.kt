package com.example.demo.screens.players.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demo.designsystem.components.AppCard

@Composable
fun PlayerDetailsScreen(component: PlayerDetailsComponent) {
    val player = component.player
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextButton(onClick = component::onBack) {
            Text("‹  Back to Players")
        }
        Text("Player Details", style = MaterialTheme.typography.headlineMedium)
        AppCard {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(player.name, style = MaterialTheme.typography.headlineSmall)
                Text("Sport", style = MaterialTheme.typography.labelMedium)
                Text(player.sport, style = MaterialTheme.typography.bodyLarge)
                Text("City", style = MaterialTheme.typography.labelMedium)
                Text(player.city, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
