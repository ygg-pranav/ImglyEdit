plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    compileSdk = rootProject.ext["compileSdkVersion"] as Int

    defaultConfig {
        minSdk = rootProject.ext["minSdkVersion"] as Int
        targetSdk = rootProject.ext["targetSdkVersion"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        compose = true
    }

    namespace="com.yougotagift.app.personalization"
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {

    implementation(project(":photo_video"))

    //region Hilt
    implementation(libs.hilt)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    ksp(libs.hilt.compiler)

    implementation(libs.fragment.ktx)

    // Import the Compose BOM
    api(platform("androidx.compose:compose-bom:2025.05.01"))

    debugApi("androidx.compose.ui:ui-tooling")
    debugApi("androidx.compose.ui:ui-test-manifest")
    api("androidx.compose.ui:ui")
    api("androidx.compose.material:material")
    api("androidx.compose.ui:ui-tooling-preview")
    debugApi("androidx.compose.foundation:foundation")
    debugApi("androidx.compose.foundation:foundation-layout")
    api("androidx.compose.animation:animation")
    api("androidx.compose.runtime:runtime-livedata")
    api("androidx.compose.runtime:runtime")
    api("androidx.compose.ui:ui-viewbinding")
    api("androidx.compose.ui:ui-util")

    api(libs.androidx.activity.compose)
    api(libs.coil.compose)
    api(libs.coil.gif)
    api(libs.coil.svg)
    api(libs.androidx.navigation.compose)
    api(libs.accompanist.pager)
    api(libs.lottie.compose)
    debugApi(libs.androidx.customview)
    debugApi(libs.androidx.customview.poolingcontainer)
    api(libs.accompanist.placeholder.material)
    api(libs.accompanist.systemuicontroller)

    implementation(libs.compose.paging)
}
