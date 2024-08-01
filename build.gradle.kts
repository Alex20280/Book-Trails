// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp.library) apply false
    //id("org.sonarqube") version "5.1.0.4882"

}

/*sonar {
    properties {
        property("sonar.projectKey", "alex-basanets-org_book-trails")
        property("sonar.organization", "alex-basanets-org")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}*/

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        //classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:5.1.0.4882")
    }
}