package com.example.demo.data.local

import app.cash.sqldelight.db.SqlDriver
import com.example.demo.shared.database.AppDatabase

suspend fun createAppDatabase(driver: SqlDriver): AppDatabase {
    initializeAppDatabaseSchema(driver)
    return AppDatabase(driver)
}

internal expect suspend fun initializeAppDatabaseSchema(driver: SqlDriver)
