plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.velvet.hearyou"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
            abiFilters.add("x86_64")
            abiFilters.add("x86")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }

    namespace = "com.velvet.hearyou"
}

dependencies {
    implementation(project(":models"))
    implementation(deps.gson)
    implementation("net.java.dev.jna", "jna", "5.12.1", ext = "aar")
    implementation(deps.bundles.implementation)
    implementation(deps.vosk) {
        exclude("net.java.dev.jna", "jna")
    }
    testImplementation(deps.bundles.testImplementation)
    androidTestImplementation(deps.bundles.androidTestImplementation)
    debugImplementation(deps.bundles.debugImplementation)
}