package com.example.demo.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.demo.shared.database.AppDatabase
import app.cash.sqldelight.async.coroutines.synchronous

class DatabaseDriverFactory(private val context: Context) {
    fun createDriver(): SqlDriver =
        AndroidSqliteDriver(
            schema = AppDatabase.Schema.synchronous(),
            context = context,
            name = "events.db"
        )
}