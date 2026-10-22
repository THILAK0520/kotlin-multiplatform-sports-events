
package com.example.demo.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demo.data.remote.dto.EventDto

@Composable
fun EventCard(
    event: EventDto,
    modifier: Modifier = Modifier
) {
    AppCard(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = event.category,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(text = "Organizer: ${event.organizer}")
            Text(text = "Date: ${event.date}")
            Text(text = "Location: ${event.location}")
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
