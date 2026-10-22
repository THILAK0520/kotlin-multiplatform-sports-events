
package com.example.demo.data.repository

import com.example.demo.data.local.EventsLocalDataSource
import com.example.demo.data.remote.EventsRemoteDataSource
import com.example.demo.shared.database.SportEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventsRepositoryImpl(
    private val remoteDataSource: EventsRemoteDataSource,
    private val localDataSource: EventsLocalDataSource
) {
    suspend fun getEvents(): List<SportEvent> {
        // Read cached events first.
        val cachedEvents = try {
            withContext(Dispatchers.Default) {
                localDataSource.getAllEvents()
            }
        } catch (exception: Exception) {
            println("SQLDelight cache read failed: ${exception.message}")
            exception.printStackTrace()
            emptyList()
        }

        return try {
            // Fetch fresh events from the API.
            val response = remoteDataSource.fetchEvents()

            // Save fresh events to the local database.
            response.events.forEach { event ->
                localDataSource.saveEvent(
                    id = event.id,
                    title = event.title,
                    category = event.category,
                    organizer = event.organizer,
                    description = event.description,
                    date = event.date,
                    location = event.location
                )
            }

            // Return the updated local data.
            withContext(Dispatchers.Default) {
                localDataSource.getAllEvents()
            }
        } catch (exception: Exception) {
            println("Events API or database update failed: ${exception.message}")
            exception.printStackTrace()
            println("Cached events count: ${cachedEvents.size}")

            // Fall back to cached data if available.
            cachedEvents
        }
    }
}
