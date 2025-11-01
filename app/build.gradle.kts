plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.duoihinhbatchugame"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.duoihinhbatchugame"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.github.bumptech.glide:glide:5.0.5")
    implementation("com.squareup.okhttp:okhttp:2.5.0")
    implementation(platform("com.google.firebase:firebase-bom:34.4.0")) // Dùng phiên bản mới nhất
    implementation("com.google.firebase:firebase-database") // Realtime Database
    implementation("com.google.firebase:firebase-analytics") // Analytics (Để giải quyết lỗi)
    implementation("com.google.android.material:material:1.10.0") // lib cho material design
    implementation("com.google.android.gms:play-services-ads:23.0.0") // dùng cho gg admob
}