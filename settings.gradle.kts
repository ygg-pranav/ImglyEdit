pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://zendesk.jfrog.io/zendesk/repo") }
        maven { url = uri("https://artifactory.img.ly/artifactory/imgly") }
        maven(url = "https://sdk.uxcam.com/android/")
    }
    plugins {
        id("com.android.application") version "7.4.0"
        id("com.android.library") version "7.4.0"
        kotlin("android") version "1.7.0"
        kotlin("kapt") version "1.7.0"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://artifactory.img.ly/artifactory/imgly") }
        maven { url = uri("https://zendesk.jfrog.io/zendesk/repo") }
        maven(url = "https://sdk.uxcam.com/android/")
    }
}

rootProject.name = "ygag_revamp"



include(":app")
include(":Personalization")
include(":photo_video")
//For better discoverability, add dependencies in the Alphabetical order, please.
