# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Android app that opens URLs in Hatena Bookmark (b.hatena.ne.jp). The app serves as a browser handler that intercepts URL sharing and web links, then redirects them to the corresponding Hatena Bookmark entry page via Custom Tabs.

## Build and Development Commands

### Build Commands
- `./gradlew build` - Build the entire project
- `./gradlew assembleDebug` - Build debug APK
- `./gradlew assembleRelease` - Build release APK
- `./gradlew bundleRelease` - Build release AAB (Android App Bundle)

### Testing Commands
- `./gradlew test` - Run unit tests
- `./gradlew connectedAndroidTest` - Run instrumented tests (requires device/emulator)
- `./gradlew testDebugUnitTest` - Run debug unit tests specifically

### Other Useful Commands
- `./gradlew clean` - Clean build artifacts
- `./gradlew installDebug` - Install debug build to connected device
- `./gradlew lint` - Run lint checks

## Project Architecture

### Core Components
- **MainActivity** (`app/src/main/java/jp/sane/openforhatenabookmark/MainActivity.kt`): Simple launcher activity with basic UI
- **HatebuActivity** (`app/src/main/java/jp/sane/openforhatenabookmark/HatebuActivity.kt`): Main functionality - handles URL intents and redirects to Hatena Bookmark

### Intent Handling Flow
The app registers as a handler for:
1. `ACTION_VIEW` intents with http/https schemes (direct URL opening)
2. `ACTION_SEND` intents with text/plain MIME type (URL sharing from other apps)

Processing flow:
1. Extract URL from intent (direct or from shared text using WebURLFinder)
2. Fetch the target page and extract canonical URL if available
3. Construct Hatena Bookmark entry URL: `http://b.hatena.ne.jp/entry/{encoded_url}`
4. Open result in Custom Tabs

### Key Dependencies
- **Kotlin Coroutines**: Async operations for URL fetching and processing
- **AndroidX Browser**: Custom Tabs implementation
- **Jsoup**: HTML parsing for canonical URL extraction
- **Mozilla Components**: WebURLFinder for extracting URLs from shared text

### Testing Setup
- Unit tests in `app/src/test/` using JUnit 4
- Instrumented tests in `app/src/androidTest/` using AndroidJUnit4
- Test resources in `app/src/test/res/` for HTML parsing tests

## Configuration Details

- **Package**: `jp.sane.openforhatebu` (changed from original in v5.0.0)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34
- **Compile SDK**: 34
- **View Binding**: Enabled
- **Kotlin Version**: 1.9.23