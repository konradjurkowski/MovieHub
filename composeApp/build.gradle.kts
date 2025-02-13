import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.serialization)
    alias(libs.plugins.buildKonfig)
}

val versionPropertiesFile = file("../version.properties")
val versionProperties = Properties()
versionProperties.load(FileInputStream(versionPropertiesFile))

buildkonfig {
    packageName = "com.konradjurkowski.moviehub"

    defaultConfigs {
        buildConfigField(STRING, "VERSION_NAME", versionProperties["versionName"].toString(), const = true)
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
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
            export(libs.kmpnotifier)
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            languageSettings.optIn("com.mohamedrejeb.calf.permissions.ExperimentalPermissionsApi")
        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)

            // Navigation
            implementation(libs.navigator)
            implementation(libs.navigator.screen.model)
            implementation(libs.navigator.transitions)
            implementation(libs.navigator.tabs)
            implementation(libs.navigator.koin)

            // DI
            implementation(libs.koin.core)

            // Coroutines
            implementation(libs.kotlin.coroutines)
            implementation(libs.stately.common)

            // Firebase
            implementation(libs.firebase.crashlytics)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.storage)
            implementation(libs.firebase.remote.config)

            // Internal libs
            implementation(project(":snackbarkmp"))

            // Logger
            implementation(libs.kermit)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            // Datetime
            implementation(libs.kotlinx.datetime)

            // Image
            implementation(libs.kamel.image)

            // Paging
            implementation(libs.paging.compose.common)

            // connectivity
            // TODO CHANGE IT
            implementation("com.plusmobileapps:konnectivity:0.1-alpha01")

            // Image Picker
            implementation(libs.peekaboo.image.picker)

            // Permissions
            implementation(libs.calf.permissions)

            // Notifications
            api(libs.kmpnotifier)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "org.konradjurkowski.moviehub"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.konradjurkowski.moviehub"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionName = versionProperties["versionName"].toString()
        versionCode = versionProperties["versionCode"].toString().toInt()
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
        coreLibraryDesugaring(libs.desugar.jdk.libs)
    }
}

