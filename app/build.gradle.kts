plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.weatherforcastapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherforcastapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.components)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    val room_version = "2.6.1"
    val  hilt_version = "2.49"
    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:$hilt_version")

    // Dagger - Hilt
    kapt ("com.google.dagger:hilt-android-compiler:$hilt_version")

    /*the androidx.hilt:hilt-lifecycle-viewmodel artifacts were deprecated in the Dagger 2.34 release
         in favor of native Hilt API. The missing DefaultActivityViewModelFactory class is no longer in the Hilt codebase
     */
    //    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")

    implementation ("com.google.dagger:hilt-android:$hilt_version")

    kapt ("androidx.hilt:hilt-compiler:1.2.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    //material icons - use with caution!
    // implementation "androidx.compose.material:material-icons-extended:$compose_version"
    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    //lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")
    // Coil
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

    // JSON Converter
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
//    implementation("com.squareup.retrofit:converter-gson:2.0.0-beta2")

    //Room
    implementation ("androidx.room:room-runtime:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt) MUST HAVE!
    kapt("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
}