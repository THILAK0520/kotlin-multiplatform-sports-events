
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    androidLibrary {
        namespace = "com.example.demo.shared"
        compileSdk = 37
        minSdk = 24
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { target ->
        target.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            // Compose Multiplatform
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)

            // SQLDelight
            implementation(libs.sqldelight.runtime)
            implementation("app.cash.sqldelight:coroutines-extensions:2.1.0")
            implementation("app.cash.sqldelight:async-extensions:2.1.0")

            // Decompose (expose ComponentContext to dependent modules)
            api("com.arkivanov.decompose:decompose:3.3.0")

            // Ktor and JSON serialization
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
        }

        androidMain.dependencies {
            implementation(libs.compose.uiTooling)
            implementation(libs.sqldelight.android.driver)
            implementation(libs.ktor.client.android)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
            implementation(libs.ktor.client.darwin)
        }

        jsMain.dependencies {
            implementation(libs.wrappers.browser)
            implementation(libs.ktor.client.js)

            // SQLDelight browser worker
            implementation("app.cash.sqldelight:web-worker-driver:2.1.0")
            implementation(
                npm("@cashapp/sqldelight-sqljs-worker", "2.1.0")
            )
            implementation(npm("sql.js", "1.8.0"))
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            implementation(npm("path-browserify", "1.0.1"))
            implementation(npm("crypto-browserify", "3.12.1"))
            implementation(npm("stream-browserify", "3.0.0"))
            implementation(npm("vm-browserify", "1.1.2"))
        }

        webMain.dependencies {
            implementation("app.cash.sqldelight:web-worker-driver:2.1.0")
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.example.demo.shared.database")
            generateAsync.set(true)
        }
    }
}
