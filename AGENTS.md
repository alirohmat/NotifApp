# NotifApp

Single-module Android app (Kotlin, no tests). Firebase Cloud Messaging + local notifications.

## Build
```bash
./gradlew assembleDebug
```
Artifact: `app/build/outputs/apk/debug/app-debug.apk`

## Critical repo-specific constraints
- `google-services.json` in repo root — do not move, rename, or delete. Package name in it (`com.NotifApp`) must match `app/build.gradle.kts` `applicationId`. Any change breaks Firebase auth.
- `app/build.gradle.kts` `applicationId` and `AndroidManifest.xml` package must stay in sync with `google-services.json`.
- CI generates the Gradle wrapper (`gradle wrapper --gradle-version=8.5`) before calling `./gradlew` — the repo only contains `gradle-wrapper.properties`, no `wrapper/` artifacts.

## Key versions
- Gradle: 8.5 (CI), Kotlin 1.9.22, Java 17, compileSdk 34, minSdk 26

## No tests, no lint, no formatter config
None configured. Do not assume any CI step runs tests or lint.

## Entrypoints (high-signal)
- `com.NotifApp.MainActivity` — launcher activity, local notification button
- `com.NotifApp.MyFirebaseMessagingService` — FCM token and incoming push
