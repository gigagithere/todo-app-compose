# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build
./gradlew assembleDebug
./gradlew assembleRelease

# Test
./gradlew test                    # Unit tests
./gradlew connectedAndroidTest    # Instrumented tests (requires device/emulator)

# Run a single unit test class
./gradlew test --tests "com.example.myapplication.ExampleUnitTest"

# Clean
./gradlew clean
```

Android Lint runs as part of the build. To run it explicitly:
```bash
./gradlew lint
```

## Architecture

Single-module, single-activity Jetpack Compose app with no additional architecture layers yet.

- **Entry point:** `MainActivity.kt` — uses `ComponentActivity` with `setContent` and `enableEdgeToEdge`
- **UI:** Compose with Material 3 (`androidx.compose.material3`)
- **Theme:** `ui/theme/` — `Theme.kt` handles dynamic color (Android 12+) and dark/light mode; `Color.kt` defines the palette; `Type.kt` defines typography

## Key Configuration

- **Compile/Target SDK:** 36 | **Min SDK:** 28
- **Kotlin:** 2.2.10 | **AGP:** 9.1.0
- **Compose BOM:** `2024.09.00`
- Dependency versions are centralized in `gradle/libs.versions.toml`
- `gradle.properties` sets JVM heap to 2048m and Kotlin code style to `official`
