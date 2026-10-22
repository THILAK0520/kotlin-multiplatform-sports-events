package com.example.demo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demo.navigation.RootComponent
import com.example.demo.screens.events.EventsScreen
import com.example.demo.screens.players.PlayersScreen
import com.example.demo.screens.players.details.PlayerDetailsScreen

@Composable
fun SportsHubScreen(component: RootComponent) {
    val state by component.state.collectAsState()

    if (state.isPlayerDetailsVisible) {
        component.playerDetailsComponent?.let { details ->
            PlayerDetailsScreen(component = details)
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "SportsHub",
            style = MaterialTheme.typography.headlineMedium
        )

        val selectedIndex = if (state.selectedTab == RootComponent.Tab.EVENTS) 0 else 1
        PrimaryTabRow(selectedTabIndex = selectedIndex) {
            Tab(
                selected = selectedIndex == 0,
                onClick = { component.onTabSelected(RootComponent.Tab.EVENTS) },
                text = { Text("Events") }
            )
            Tab(
                selected = selectedIndex == 1,
                onClick = { component.onTabSelected(RootComponent.Tab.PLAYERS) },
                text = { Text("Players") }
            )
        }

        when (state.selectedTab) {
            RootComponent.Tab.EVENTS -> EventsScreen(
                component = component.eventComponent,
                modifier = Modifier.weight(1f)
            )
            RootComponent.Tab.PLAYERS -> PlayersScreen(
                component = component.playersComponent,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
