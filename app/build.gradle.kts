plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("androidx.navigation.safeargs.kotlin")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.wallify"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wallify"
        minSdk = 28
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
         dataBinding = true
         viewBinding = true
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

    //for scalable views and text on different devices
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    implementation("com.intuit.sdp:sdp-android:1.1.0")

    val navVersion = "2.7.7"
    // Kotlin Navigation Fragment
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    val lifecycleVersion = "2.7.0"
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    val activityVersion = "1.8.2"
    // Kotlin
    implementation("androidx.activity:activity-ktx:$activityVersion")


    //paging
    val pagingVersion = "3.2.1"
    implementation("androidx.paging:paging-runtime:$pagingVersion")

    //glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    //Material Design
    implementation("com.google.android.material:material:1.1.0")

    implementation("io.trbl:blurhash:1.0.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //Custom Progess Bar
    implementation("com.github.ybq:Android-SpinKit:1.4.0")
    implementation("com.flaviofaria:kenburnsview:1.0.7")

}