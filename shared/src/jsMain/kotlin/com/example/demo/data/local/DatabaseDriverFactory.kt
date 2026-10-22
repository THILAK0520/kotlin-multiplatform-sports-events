
package com.example.demo.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import com.example.demo.shared.database.AppDatabase
import org.w3c.dom.Worker

class DatabaseDriverFactory
{
    fun createDriver(): SqlDriver {
        val worker = Worker(
            js(
                "new URL('@cashapp/sqldelight-sqljs-worker/sqljs.worker.js', import.meta.url)"
            )
        )

        return WebWorkerDriver(worker)
    }
}