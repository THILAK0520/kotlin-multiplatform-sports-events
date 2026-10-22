# Kotlin Multiplatform Sports Events

A sports events app built with Kotlin Multiplatform and Compose Multiplatform. It loads event data from a remote JSON API and uses SQLDelight for local storage and caching.

## Platforms

- Android
- iOS
- Web (Kotlin/JS)
- WebAssembly compilation target

## Tech stack

- Kotlin and Compose Multiplatform
- Decompose for component architecture
- Ktor Client and Kotlin Serialization for networking
- SQLDelight for local database storage

## Run the app

### Android

Open the project in Android Studio and run the `androidApp` configuration, or build a debug APK:

```shell
./gradlew :androidApp:assembleDebug
```

### iOS

Open `iosApp/iosApp.xcodeproj` in Xcode and run the `iosApp` scheme on an iOS device or simulator.

### Web (JavaScript)

Start the development server:

```shell
./gradlew :webApp:jsBrowserDevelopmentRun
```

The app is served at `http://localhost:8080/`.

Build the WebAssembly target with:

```shell
./gradlew :shared:compileKotlinWasmJs
```

## Build checks

```shell
./gradlew :shared:compileKotlinJs :shared:compileKotlinWasmJs
./gradlew :androidApp:assembleDebug
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

## Project layout

- `androidApp/` — Android application entry point.
- `iosApp/` — SwiftUI application shell that hosts the shared Compose UI.
- `shared/src/commonMain/` — shared UI, components, repositories, networking, and SQLDelight schema.
- `shared/src/androidMain/`, `iosMain/`, `jsMain/`, and `wasmJsMain/` — platform-specific implementations.
- `webApp/` — browser application entry point and web resources.

## Data and caching

Events are fetched from the public JSON endpoint configured in `shared/src/commonMain/kotlin/com/example/demo/data/remote/EventsRemoteDataSource.kt`. SQLDelight stores events locally so the repository can fall back to cached records when a refresh fails.

## Tests

Run the shared tests for a target with the corresponding Gradle task, for example:

```shell
./gradlew :shared:jsTest
./gradlew :shared:wasmJsTest
./gradlew :shared:iosSimulatorArm64Test
./gradlew :shared:testAndroidHostTest
```
