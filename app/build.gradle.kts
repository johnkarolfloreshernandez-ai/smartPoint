plugins {
    alias(libs.plugins.android.application)
    kotlin ("plugin.serialization") version "1.9.24"
}

android {
    namespace = "com.example.smartpoint"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.smartpoint"
        minSdk = 28
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // Activa la ofuscación y optimización de código con R8
            isMinifyEnabled = true
            // Elimina recursos XML/imágenes sin usar para reducir peso del APK
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    //agregamos la lista
    //2. Librería principal de Kotlinx Serialization Json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    // 3. Convertidor oficial de Retrofit para Kotlinx Serialization
    implementation("com.squareup.retrofit2:converter-kotlinx-serialization:2.11.0")
    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    // Para imágenes
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Corrutinas (requerido en punto 1.3 de la bitácora)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    // Interceptor de OkHttp (muy útil para depurar respuestas JSON en Logcat)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Firebase BoM y Analytics
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
}