plugins {
    id(Plugins.ANDROID_LIBRARY)
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
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            DefaultConfig.FieldType.TYPE_STRING,
            DefaultConfig.FieldKey.TMDB_API_KEY,
            "\"378aa10ea738ce4cb6ab3dd53569c97d\""
        )
        buildConfigField(
            DefaultConfig.FieldType.TYPE_STRING,
            DefaultConfig.FieldKey.BASE_URL_TMDB,
            "\"https://api.themoviedb.org/3/\""
        )
        buildConfigField(
            DefaultConfig.FieldType.TYPE_STRING,
            DefaultConfig.FieldKey.BASE_URL_IMAGE_TMDB,
            "\"https://image.tmdb.org/t/p/\""
        )
        buildConfigField(
            DefaultConfig.FieldType.TYPE_STRING,
            DefaultConfig.FieldKey.BASE_URL_TMDB_HOSTNAME,
            "\"api.themoviedb.org\""
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isMinifyEnabled = true
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

    lintOptions {
        isAbortOnError = false
        isCheckReleaseBuilds = false
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN_STANDARD_LIBRARY)

    implementation(Dependencies.Coroutines.CORE)
    implementation(Dependencies.Coroutines.ANDROID)

    implementation(Dependencies.Retrofit.MAIN)
    implementation(Dependencies.Retrofit.CONVERTER_MOSHI)

    implementation(Dependencies.OKHTTP3.MAIN)
    implementation(Dependencies.OKHTTP3.LOGGING_INTERCEPTOR)

    kapt(Dependencies.Room.COMPILER)
    implementation(Dependencies.Room.RUNTIME)
    implementation(Dependencies.Room.KTX)

    implementation(Dependencies.Obfuscation.SQL_CIPHER)
    implementation(Dependencies.Obfuscation.SQLITE_KTX)

    api(Dependencies.Lifecycle.LIVEDATA_KTX)
    api(Dependencies.Paging.RUNTIME)

}