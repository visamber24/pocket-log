plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)

    //serialization plugin
    id("org.jetbrains.kotlin.plugin.serialization") version "2.4.0"

    //firebase
//    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    composeOptions {
        kotlinCompilerExtensionVersion = "1.6.10"
    }
    namespace = "com.lazysloth.pocketlog"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.lazysloth.pocketlog"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
//    implementation("com.google.cloud:google-cloud-vision:3.91.0")
    //json deserialization dependency
    implementation(libs.kotlinx.serialization.json.v171)

    //Camera X
    implementation(libs.androidx.camera.compose)
    implementation( libs.androidx.camera.core)
    implementation( libs.androidx.camera.lifecycle)
    implementation( libs.androidx.camera.view)
    implementation(libs.androidx.camera2)

    implementation(libs.accompanist.permissions)
    //firebase
    implementation(platform("com.google.firebase:firebase-bom:34.16.0"))
    implementation("com.google.firebase:firebase-ai")
    implementation("com.google.firebase:firebase-appcheck-debug")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    //coil for display image
    implementation(libs.coil.compose)

    // alignment
    implementation(libs.androidx.compose.foundation.layout.v1103)

    //material
    implementation(libs.androidx.material.icons.extended)
    //Navigation dependency
    implementation(libs.androidx.compose.navigation.suite)
    //datastore dependency
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    //hilt dagger


   //koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    //room database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.play.services.mlkit.text.recognition)
    implementation(libs.play.services.mlkit.text.recognition.common)
    implementation(libs.vision.common)
    ksp(libs.androidx.room.compiler)




    //coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    //serialization dependency
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.material)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}