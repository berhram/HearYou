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
            version("composeUI", "1.2.1")
            version("appyx", "1.0-alpha08")
            version("koin", "3.2.0")
            version("accompanist", "0.25.1")
            version("orbit", "4.3.2")
            //implementation
            library("vosk", "com.alphacephei", "vosk-android").version("0.3.38")
            library("controller", "com.google.accompanist", "accompanist-systemuicontroller").versionRef("accompanist")
            library("koin", "io.insert-koin", "koin-android").versionRef("koin")
            library("koinCompose", "io.insert-koin", "koin-androidx-compose").versionRef("koin")
            library("appyxCore", "com.bumble.appyx", "core").versionRef("appyx")
            library("core", "androidx.core", "core-ktx").version("1.9.0")
            library("ui", "androidx.compose.ui", "ui").versionRef("composeUI")
            library("material", "androidx.compose.material", "material").version("1.2.1")
            library("toolingPreview", "androidx.compose.ui", "ui-tooling-preview").versionRef("composeUI")
            library("lifecycleRuntime", "androidx.lifecycle", "lifecycle-runtime-ktx").version("2.5.1")
            library("activityCompose", "androidx.activity", "activity-compose").version("1.5.1")
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
                    "appyxCore",
                    "koin",
                    "koinCompose",
                    "orbitCore",
                    "orbitViewModel",
                    "orbitCompose",
                    "controller",
                    "vosk"
                )
            )
            //testImplementation
            library("appyxJUnit", "com.bumble.appyx", "testing-junit4").versionRef("appyx")
            library("appyxTestCommon", "com.bumble.appyx", "testing-unit-common").versionRef("appyx")
            library("jUnit", "junit", "junit").version("4.13.2")
            bundle("testImplementation", listOf("jUnit", "appyxJUnit", "appyxTestCommon"))
            //androidTestImplementation
            library("appyxTestingUI", "com.bumble.appyx", "testing-ui").versionRef("appyx")
            library("jUnitExt", "androidx.test.ext", "junit").version("1.1.3")
            library("espresso", "androidx.test.espresso", "espresso-core").version("3.4.0")
            library("uiTestJUnit", "androidx.compose.ui", "ui-test-junit4").versionRef("composeUI")
            bundle(
                "androidTestImplementation",
                listOf("jUnitExt", "espresso", "uiTestJUnit", "appyxTestingUI")
            )
            //debugImplementation
            library("appyxTestingUIActivity", "com.bumble.appyx", "testing-ui-activity").versionRef("appyx")
            library("uiTooling", "androidx.compose.ui", "ui-tooling").versionRef("composeUI")
            library("testManifest", "androidx.compose.ui", "ui-test-manifest").versionRef("composeUI")
            bundle(
                "debugImplementation",
                listOf("uiTooling", "testManifest", "appyxTestingUIActivity")
            )
        }
    }
}

rootProject.name = "Hear You"
include(":app")