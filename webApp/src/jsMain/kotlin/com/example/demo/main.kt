
package com.example.demo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.demo.data.local.DatabaseDriverFactory

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val driver = DatabaseDriverFactory().createDriver()

    val lifecycle = LifecycleRegistry()
    val componentContext = DefaultComponentContext(lifecycle)

    ComposeViewport {
        DatabaseApp(
            driver = driver,
            componentContext = componentContext
        )
    }
}
