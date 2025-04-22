plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // secrets-gradle-plugin
    alias(libs.plugins.secrets.gradle.plugin)
    alias(libs.plugins.ksp)
    // safe args
    alias(libs.plugins.navigation.safeargs)
    // parcelable generator
    id("kotlin-parcelize")
    // kotlin plugin serializable
    // alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.booksearch"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.booksearch"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "bookApiKey", "${properties["bookApiKey"]}")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // retrofit / moshi
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi)
    ksp(libs.moshi.codegen)

    // okhttp3
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)

    // coroutine
    implementation(libs.coroutine)

    // lifecycle
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.kotlin)

    // coil
    implementation(libs.coil)
    implementation(libs.coil.network)

    // room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // kotlin plugin serializable
    implementation(libs.kotlinx.serialization.json)

}