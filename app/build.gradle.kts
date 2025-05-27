plugins {
    id("com.android.application")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.firebase.crashlitycs)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
}

val appVersionName = rootProject.ext["appVersionName"] as String
val testBuildVersion = rootProject.ext["testBuildVersion"] as String
val appVersionCode = rootProject.ext["appVersionCode"] as Int

android {
    compileSdk = rootProject.ext["compileSdkVersion"] as Int

    defaultConfig {
        applicationId = "com.yougotagift.YouGotaGiftApp.test"
        minSdk = rootProject.ext["minSdkVersion"] as Int
        targetSdk = rootProject.ext["targetSdkVersion"] as Int
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            versionNameSuffix = ".debug(${testBuildVersion})"
        }

    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
   
    namespace = "com.yougotagift.YouGotaGiftApp"

    lint {
        // Turns off checks for the issue IDs you specify.
        disable += "TypographyFractions" + "TypographyQuotes"
        // Turns on checks for the issue IDs you specify. These checks are in
        // addition to the default lint checks.
        enable += "RtlHardcoded" + "RtlCompat" + "RtlEnabled"
        // To enable checks for only a subset of issue IDs and ignore all others,
        // list the issue IDs with the 'check' property instead. This property overrides
        // any issue IDs you enable or disable using the properties above.
        checkOnly += "NewApi" + "InlinedApi"
        // If set to true, turns off analysis progress reporting by lint.
        quiet = true
        // If set to true (default), stops the build if errors are found.
        abortOnError = false
        // If set to true, lint only reports errors.
        ignoreWarnings = true
        // If set to true, lint also checks all dependencies as part of its analysis.
        // Recommended for projects consisting of an app with library dependencies.
        checkDependencies = true
    }
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {

    implementation(project(":Personalization"))
    implementation(project(":photo_video"))

    implementation(libs.androidx.constraintlayout)
    implementation(libs.okhttp3.okhttp)
    implementation(libs.androidx.paging.runtime.ktx)

    //region Glide
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    //end region

    //region Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    //end region

    implementation(libs.androidx.appcompat)


    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    implementation(libs.lottie.compose)
    implementation(libs.lottie)

    // Support for Java 8 features
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}
