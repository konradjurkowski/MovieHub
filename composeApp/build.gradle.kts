import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.build.konfig)
}

val versionPropertiesFile = file("../version.properties")
val versionProperties = Properties()
versionProperties.load(FileInputStream(versionPropertiesFile))

buildkonfig {
    packageName = "com.konradjurkowski.weatherapp"

    defaultConfigs {
        buildConfigField(STRING, "VERSION_NAME", versionProperties["versionName"].toString(), const = true)
    }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        all {
            languageSettings.optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // DI
            implementation(libs.koin.android)

            // Network
            implementation(libs.ktor.client.okhttp)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Navigation
            implementation(libs.jetbrains.navigation.compose)

            // Kotlin
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlin.coroutines)

            // DI
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Network
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.resources)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            // Firebase
            implementation(libs.firebase.analytics)
            implementation(libs.firebase.crashlytics)

            // Logger
            implementation(libs.kermit)

            // Storage
            implementation(libs.datastore.preferences)

            // Permissions
            implementation(libs.moko.permissions.compose)
            implementation(libs.moko.permissions.camera)
            implementation(libs.moko.permissions.gallery)
            implementation(libs.moko.permissions.notifications)

            // UI Helpers
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.paging.compose)
            implementation(libs.snackbarkmm)
            implementation(libs.qr.kit)
            implementation(libs.image.picker)
            implementation(libs.compottie)
        }

        iosMain.dependencies {
            // Network
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.konradjurkowski.moviehub"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.konradjurkowski.moviehub"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

