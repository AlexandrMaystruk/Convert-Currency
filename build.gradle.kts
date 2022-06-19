buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.0.4")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.gradlePlugin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger}")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}