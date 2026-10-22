
package com.example.demo.data.local

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import com.example.demo.shared.database.AppDatabase
import com.example.demo.shared.database.SportEvent

class EventsLocalDataSource(
    private val database: AppDatabase
) {
    private val queries = database.sportEventQueries

    suspend fun getAllEvents(): List<SportEvent> {
        return queries.selectAll().awaitAsList()
    }

    suspend fun getEventById(id: Long): SportEvent? {
        return queries.selectById(id).awaitAsOneOrNull()
    }

    suspend fun saveEvent(
        id: Long,
        title: String,
        category: String,
        organizer: String,
        description: String,
        date: String,
        location: String
    ) {
        queries.insertEvent(
            id = id,
            title = title,
            category = category,
            organizer = organizer,
            description = description,
            date = date,
            location = location
        )
    }

    suspend fun clearEvents() {
        queries.deleteAll()
    }
}
