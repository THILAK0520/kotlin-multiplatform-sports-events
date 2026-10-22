package com.example.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.cash.sqldelight.db.SqlDriver
import com.arkivanov.decompose.ComponentContext
import com.example.demo.data.local.createAppDatabase
import com.example.demo.shared.database.AppDatabase

@Composable
fun DatabaseApp(
    driver: SqlDriver,
    componentContext: ComponentContext
) {
    var database by remember(driver) { mutableStateOf<AppDatabase?>(null) }

    LaunchedEffect(driver) {
        database = createAppDatabase(driver)
    }

    database?.let {
        App(database = it, componentContext = componentContext)
    }
}
