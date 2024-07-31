plugins {
   alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp.library)
    alias(libs.plugins.kotlin.serialization)
   // alias(libs.plugins.sonarqube)
/*    id("com.android.application")
    id("org.jetbrains.kotlin.android")*/
}

android {
    namespace = "com.project.booktrails"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.booktrails"
        minSdk = 24
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
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation (project(":feature_auth_module"))
    implementation (project(":ui_module"))
    implementation (project(":core_module"))
    implementation (project(":feature_reading_module"))
    implementation (project(":feature_bestseller_books_module"))
    implementation (project(":feature_profile_module"))
    implementation(project(":feature_book_management_module"))

    //implementation(libs.sonarqube.gradle.plugin)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.material3.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.androidx.material)
    implementation (libs.ui.tooling.preview)

    //Koin
    implementation (libs.koin.android)
    implementation (libs.koin.core)
    implementation (libs.koin.androidx.compose)

    //Compose navigation
    implementation (libs.androidx.navigation.compose)
    implementation (libs.accompanist.navigation.animation)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //Splash screen
    implementation (libs.androidx.core.splashscreen)

    //DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.preferences.core)

    //Coroutines
    implementation (libs.kotlinx.coroutines.core)


}