package com.example.demo.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.async.coroutines.synchronous
import com.example.demo.shared.database.AppDatabase

class DatabaseDriverFactory {
    fun createDriver(): SqlDriver =
        NativeSqliteDriver(
            schema = AppDatabase.Schema.synchronous(),
            name = "events.db"
        )
}