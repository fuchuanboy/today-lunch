plugins {
    id("com.android.application") version "8.7.3"
    id("org.jetbrains.kotlin.android") version "2.0.21"
}

android {
    namespace = "com.fuchuanboy.todaylunch"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fuchuanboy.todaylunch"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    tasks.withType<JavaCompile>().configureEach {
        options.release.set(17)
    }
}

kotlin {
    jvmToolchain(17)
}
