plugins {
    id(Plugins.ANDROID_DYNAMIC_FEATURE)
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
        minSdkVersion(DefaultConfig.Version.minSdk)
        targetSdkVersion(DefaultConfig.Version.targetSdk)
        versionCode = DefaultConfig.Version.appVersionCode
        versionName = DefaultConfig.Version.appVersionName
        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    android.buildFeatures.viewBinding = true
}

dependencies {
    implementation(project(":core"))
    implementation(project(":app"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN_STANDARD_LIBRARY)
}