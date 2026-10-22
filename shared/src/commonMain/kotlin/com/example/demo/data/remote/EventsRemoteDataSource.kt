
package com.example.demo.data.remote

import com.example.demo.data.remote.dto.EventsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

class EventsRemoteDataSource(
    private val client: HttpClient
) {
    suspend fun fetchEvents(): EventsResponse {
        val response = client.get(
            "https://raw.githubusercontent.com/THILAK0520/SportsEventsJson/main/events.json"
        )

        val responseText: String = response.body<String>()

        println("Sports API response: $responseText")

        return Json {
            ignoreUnknownKeys = true
            isLenient = true
        }.decodeFromString<EventsResponse>(responseText)
    }
}
