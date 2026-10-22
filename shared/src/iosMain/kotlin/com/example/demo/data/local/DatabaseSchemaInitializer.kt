package com.example.demo.data.local

import app.cash.sqldelight.db.SqlDriver

// NativeSqliteDriver creates or migrates the schema when it opens the database.
internal actual suspend fun initializeAppDatabaseSchema(driver: SqlDriver) = Unit
