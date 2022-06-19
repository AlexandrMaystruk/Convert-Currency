plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}
android {
    compileSdk = Dependencies.android.compileSdk
    buildToolsVersion = Dependencies.android.buildTools

    defaultConfig {
        applicationId = Android.applicationId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    core()
    di()
    jetPack()
    network()
    log()
    test()
}

fun DependencyHandlerScope.core() {
    implementation(Dependencies.other.kotlin)
    implementation(Dependencies.other.kotlinxSerialization)
    implementation(Dependencies.other.material)
}

fun DependencyHandlerScope.jetPack() {
    implementation(Dependencies.jetpack.dataStorePreferences)
    compose()
    room()
}

fun DependencyHandlerScope.room() {
    implementation(Dependencies.room.runtime)
    kapt(Dependencies.room.compiler)
    implementation(Dependencies.room.ktx)
}

fun DependencyHandlerScope.di() {
    implementation(Dependencies.di.daggerHilt)
    kapt(Dependencies.di.daggerHiltCompiler)
}


fun DependencyHandlerScope.network() {
    implementation(Dependencies.retrofit.retrofit2)
    implementation(Dependencies.retrofit.serializationConverter)
    implementation(Dependencies.other.okHttp)
}

fun DependencyHandlerScope.async() {
    implementation(Dependencies.async.coroutinesCore)
    implementation(Dependencies.async.coroutinesAndroid)
}

fun DependencyHandlerScope.compose() {
    implementation(Dependencies.compose.runtime)
    implementation(Dependencies.compose.activity)
    implementation(Dependencies.compose.ui)
    debugImplementation(Dependencies.compose.uiTooling)
    debugImplementation(Dependencies.compose.uiToolingPreview)
    implementation(Dependencies.compose.material)
    implementation(Dependencies.compose.materialIcons)
    implementation(Dependencies.compose.navigation)
    implementation(Dependencies.compose.hiltNavigation)
    implementation(Dependencies.compose.lifecycleViewModel)
    implementation(Dependencies.compose.animation)
    implementation(Dependencies.compose.constraintLayoutCompose)
}

fun DependencyHandlerScope.log() {
    implementation(Dependencies.other.timber)
}

fun DependencyHandlerScope.test() {
    testImplementation(Dependencies.test.junit)
    testImplementation(Dependencies.test.mockk)
    testImplementation(Dependencies.test.core)
    testImplementation(Dependencies.test.coreTest)
    testImplementation(Dependencies.test.coroutineTest)
    testImplementation(Dependencies.test.robolectric)
}

kapt {
    correctErrorTypes = true
}