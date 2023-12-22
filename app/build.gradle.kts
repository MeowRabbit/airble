plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "stm.airble"
    compileSdk = 34

    defaultConfig {
        applicationId = "stm.airble"
        minSdk = 26
        targetSdk = 34
        versionCode = 12
        versionName = "0.9.14"

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
    sourceSets {
        getByName("main") {
            res {
                srcDirs(
                    "src\\main\\res",
                    "src\\main\\res\\_0_public_res",
                    "src\\main\\res\\_1_splash_res",
                    "src\\main\\res\\_2_tutorial_res",
                    "src\\main\\res\\_3_registration_res",
                    "src\\main\\res\\_4_main_res",
                    "src\\main\\res\\_4_main_res\\_1_home_res",
                    "src\\main\\res\\_4_main_res\\_2_graph_res",
                    "src\\main\\res\\_4_main_res\\_3_information_res",
                    "src\\main\\res\\_4_main_res\\_4_faq_res", 
                    "src\\main\\res\\_4_main_res\\_5_setting_res",
                    "src\\main\\res\\_5_add_airble_res"
                )
            }
        }
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    // Viewpager2
    implementation ("com.tbuonomo:dotsindicator:4.2")

    // MPAndroidChart
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // material-calendar
    implementation ("com.github.prolificinteractive:material-calendarview:1.4.3")

    // RxJava
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.6")

    // Google Login
    implementation (platform("com.google.firebase:firebase-bom:32.1.1"))
    implementation ("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-auth")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")

    // SwipeRefreshLayout
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
}