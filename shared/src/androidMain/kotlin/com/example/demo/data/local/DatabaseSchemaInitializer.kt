package com.example.demo.data.local

import app.cash.sqldelight.db.SqlDriver

// AndroidSqliteDriver's open-helper callback creates or migrates the schema.
internal actual suspend fun initializeAppDatabaseSchema(driver: SqlDriver) = Unit
