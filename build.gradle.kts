plugins {
    id("com.android.application") version "9.5.0" apply false
    // AGP 9.5 includes built-in Kotlin support. The Compose compiler plugin
    // pins the Kotlin Gradle Plugin version used by this project.
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.21" apply false
}
