package com.example.demo.data.local

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import com.example.demo.shared.database.AppDatabase

internal actual suspend fun initializeAppDatabaseSchema(driver: SqlDriver) {
    AppDatabase.Schema.awaitCreate(driver)
}
