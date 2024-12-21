plugins {
    id("com.android.application")
}

android {
    namespace = "com.saveetha.tutorfinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.saveetha.tutorfinder"
        minSdk = 24
        targetSdk = 34
        versionCode = 7
        versionName = "7.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.squareup.retrofit2:retrofit:2.3.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:3.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.9.0")
    //card view
    implementation ("androidx.cardview:cardview:1.0.0")
    //for text box
    implementation("com.google.android.material:material:1.11.0")
    //for recycler view
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    //image src
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    //for razorpay
    implementation("com.razorpay:checkout:1.6.36")
    //color picker
//    implementation("com.github.duanhong169:colorpicker:1.1.6")
    implementation("com.github.yukuku:ambilwarna:2.0.1")
    //rich editor
//    implementation("jp.wasabeef:richeditor-android:2.0.0")
//    implementation("com.github.chinalwb:are:0.1.7")
//    annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'

    // This dependency is downloaded from the Google’s Maven repository.
    // So, make sure you also include that repository in your project's build.gradle file.
    implementation("com.google.android.play:app-update:2.1.0")
}