
package com.example.demo

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.demo.data.local.DatabaseDriverFactory
import platform.UIKit.UIViewController

@Suppress("FunctionName")
fun MainViewController(): UIViewController {
    // Keep the driver outside Compose content. The content lambda is recomposed,
    // and creating a SQLite driver there can open multiple connections to events.db.
    val driver = DatabaseDriverFactory().createDriver()
    val lifecycle = LifecycleRegistry()
    val componentContext = DefaultComponentContext(lifecycle)

    return ComposeUIViewController {
        DatabaseApp(
            driver = driver,
            componentContext = componentContext
        )
    }
}
