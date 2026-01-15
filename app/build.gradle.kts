plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.multilangbook"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.multilangbook"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.21")

    implementation("com.google.code.gson:gson:2.8.9")
//    implementation("com.google.mlkit:translate:17.0.3")
    implementation("com.google.mlkit:translate:16.1.1")
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.0")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.media3:media3-common-ktx:1.7.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
//    implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.21")
//
//    implementation("com.google.code.gson:gson:2.10.1")
////    implementation("com.google.mlkit:translate:17.0.3")
//    implementation("com.google.mlkit:translate:17.0.3")
//    implementation("androidx.core:core-ktx:1.17.0")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
//    implementation("androidx.activity:activity-compose:1.12.3")
//    implementation(platform("androidx.compose:compose-bom:2026.01.01"))
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material3:material3")
//    implementation("androidx.appcompat:appcompat:1.7.1")
//    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
//    implementation("com.google.android.material:material:1.12.0")
//    implementation("androidx.media3:media3-common-ktx:1.9.1")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.2.1")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
//    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.01"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//    debugImplementation("androidx.compose.ui:ui-test-manifest")

//    implementation("androidx.core:core-ktx:1.17.0")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
//    implementation("androidx.activity:activity-compose:1.12.2")
//    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material3:material3")
//    implementation("com.google.mlkit:translate:17.0.3")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.3.0")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
//    androidTestImplementation(platform("androidx.compose:compose-bom:2024.04.01"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
}