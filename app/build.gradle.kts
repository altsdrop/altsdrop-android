import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.gms.google.services)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.com.google.firebase.crashlytics)
    kotlin("plugin.serialization") version "2.0.20"
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
// Initialize a new Properties() object called keystoreProperties.
val keystoreProperties = Properties()
// Load your keystore.properties file into the keystoreProperties object.
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.altsdrop"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.altsdrop.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 9
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("prodConfig") {
            keyAlias = keystoreProperties["PROD_KEY_ALIAS"] as String
            keyPassword = keystoreProperties["PROD_KEY_PASSWORD"] as String
            storeFile = file(keystoreProperties["PROD_STORE_FILE"] as String)
            storePassword = keystoreProperties["PROD_STORE_PASSWORD"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("prodConfig")
        }
    }

    flavorDimensions += "environment"

    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            resValue("string", "app_name", "AltsDrop Dev")
        }
        create("prod") {
            dimension = "environment"
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //Firebase
    implementation(platform(libs.com.google.firebase))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.config)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.appcheck.integrity)
    implementation(libs.firebase.appcheck.debug)
    implementation(libs.google.firebase.analytics)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    //Browser
    implementation(libs.androidx.browser)
    implementation(libs.org.jsoup)
    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    //Google fonts
    implementation(libs.androidx.ui.text.google.fonts)

    //Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.kotlinx.serialization.json)

    //Coil
    implementation(libs.coil.compose)

    //In-app Updates
    implementation(libs.play.app.update)

    // For Kotlin users also add the Kotlin extensions library for Play In-App Update:
    implementation(libs.play.app.update.ktx)

    //Preference Datastore
    implementation(libs.androidx.datastore.preferences)

    //Moshi
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi)
    ksp(libs.moshi.kotlin.codegen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}