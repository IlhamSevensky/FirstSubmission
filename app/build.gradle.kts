plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.KOTLIN_ANDROID_EXTENSIONS)
    id(Plugins.DAGGER_HILT_ANDROID)
    id(Plugins.SAFE_ARGS_KOTLIN)
}

apply(from = Files.SHARED_DEPENDENCY_GRADLE)

android {

    compileSdkVersion(DefaultConfig.Version.compileSdk)

    defaultConfig {
        applicationId = DefaultConfig.APPLICATION_ID
        minSdkVersion(DefaultConfig.Version.minSdk)
        targetSdkVersion(DefaultConfig.Version.targetSdk)
        versionCode = DefaultConfig.Version.appVersionCode
        versionName = DefaultConfig.Version.appVersionName
        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName("release") {
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    bundle {
        language.enableSplit = false
        density.enableSplit = true
        abi.enableSplit = true
    }

    dynamicFeatures = mutableSetOf(
        ":favorite", ":search"
    )

}

dependencies {
    implementation(project(":core"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN_STANDARD_LIBRARY)

    /**
     * This is just workaround instead change 'implementation' to 'api' each dependency without exposes library from core module
     * because there is a bug in the dagger at the following link.
     * https://github.com/google/dagger/issues/1991
     * https://github.com/google/dagger/issues/970
     */
    // Retrofit
    implementation(Dependencies.Retrofit.MAIN)
    implementation(Dependencies.Retrofit.CONVERTER_MOSHI)

    // Room
    implementation(Dependencies.Room.RUNTIME)
    implementation(Dependencies.Room.KTX)
    kapt(Dependencies.Room.COMPILER)

    // Leak Canary
    debugImplementation(Dependencies.PerformanceTest.LEAK_CANARY)

}