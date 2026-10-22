
package com.example.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.example.demo.data.local.DatabaseDriverFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val driver = DatabaseDriverFactory(applicationContext).createDriver()

        setContent {
            DatabaseApp(
                driver = driver,
                componentContext = defaultComponentContext()
            )
        }
    }
}
