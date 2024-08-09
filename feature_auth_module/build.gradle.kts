plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp.library)
    alias(libs.plugins.gms)
    alias(libs.plugins.parcelize)
}

android {
    namespace = "com.project.feature_auth_module"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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
        kotlinCompilerExtensionVersion = "1.5.0"
    }

}

dependencies {

    implementation (project(":ui_module"))
    implementation (project(":core_module"))
    implementation(project(":core_network_module"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.navigation.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Compose
    implementation(libs.androidx.compose.ui.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation ("androidx.activity:activity-compose:1.9.1")

    //Koin
    implementation (libs.koin.android)
    implementation (libs.koin.core)
    implementation (libs.koin.androidx.compose)

    //Lottie animation
    implementation (libs.lottie)
    
    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore)

    // Google sign in
    implementation("com.google.android.gms:play-services-auth:21.2.0")

}
