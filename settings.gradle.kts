pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("deps") {
            //versions
            version("composeUI", "1.6.0")
            version("koin", "3.5.3")
            version("accompanist", "0.34.0")
            version("orbit", "6.1.0")
            //implementation
            library("gson", "com.google.code.gson", "gson").version("2.10.1")
            library("vosk", "com.alphacephei", "vosk-android").version("0.3.47")
            library(
                "controller",
                "com.google.accompanist",
                "accompanist-systemuicontroller"
            ).versionRef("accompanist")
            library("koin", "io.insert-koin", "koin-android").versionRef("koin")
            library("koinCompose", "io.insert-koin", "koin-androidx-compose").versionRef("koin")
            library("core", "androidx.core", "core-ktx").version("1.12.0")
            library("ui", "androidx.compose.ui", "ui").versionRef("composeUI")
            library("material", "androidx.compose.material", "material").version("1.6.0")
            library(
                "toolingPreview",
                "androidx.compose.ui",
                "ui-tooling-preview"
            ).versionRef("composeUI")
            library(
                "lifecycleRuntime",
                "androidx.lifecycle",
                "lifecycle-runtime-ktx"
            ).version("2.7.0")
            library("activityCompose", "androidx.activity", "activity-compose").version("1.8.2")
            library("orbitCore", "org.orbit-mvi", "orbit-core").versionRef("orbit")
            library("orbitViewModel", "org.orbit-mvi", "orbit-viewmodel").versionRef("orbit")
            library("orbitCompose", "org.orbit-mvi", "orbit-compose").versionRef("orbit")
            bundle(
                "implementation",
                listOf(
                    "core",
                    "ui",
                    "material",
                    "toolingPreview",
                    "lifecycleRuntime",
                    "activityCompose",
                    "koin",
                    "koinCompose",
                    "orbitCore",
                    "orbitViewModel",
                    "orbitCompose",
                    "controller"
                )
            )
            //debugImplementation
            library("uiTooling", "androidx.compose.ui", "ui-tooling").versionRef("composeUI")
            library(
                "testManifest",
                "androidx.compose.ui",
                "ui-test-manifest"
            ).versionRef("composeUI")
            bundle(
                "debugImplementation",
                listOf("uiTooling", "testManifest")
            )
        }
    }
}

rootProject.name = "Hear You"
include(":app")
include(":models")