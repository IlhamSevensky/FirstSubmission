object Dependencies {

    object Version {
        const val ANDROID_GRADLE_PLUGIN = "4.0.1"
        const val KOTLIN_GRADLE_PLUGIN = "1.4.10"
        const val SUPPORT_LEGACY = "1.0.0"
        const val SUPPORT_LIBRARY = "1.2.0"
        const val CONSTRAINT_LAYOUT = "2.0.1"
        const val MATERIAL = "1.3.0"
        const val JUNIT = "4.13"
        const val ANDROID_TEST_JUNIT = "1.1.2"
        const val ANDROID_TEST_ESPRESSO = "3.3.0"
        const val KTX_CORE = "1.5.0"
        const val KTX_FRAGMENT = "1.3.4"
        const val KTX_ACTIVITY = "1.2.3"
        const val NAVIGATION = "2.3.5"
        const val COIL = "1.2.1"
        const val LIFECYCLE = "2.3.1"
        const val HILT = "1.0.0"
        const val DAGGER_HILT = "2.35"
        const val FLOW_BINDING = "1.0.0"
        const val ROOM = "2.3.0"
        const val PAGING = "3.0.0"
        const val RETROFIT = "2.9.0"
        const val OKHTTP = "4.9.1"
        const val COROUTINES = "1.5.0"
        const val LEAK_CANARY = "2.7"
        const val SQL_CIPHER = "4.4.3"
        const val SQLITE_KTX = "2.1.0"
        const val TIMBER = "4.1.2"
        const val MOCKITO = "3.10.0"
        const val MOCKITO_KOTLIN = "3.2.0"
    }

    object PerformanceTest {
        const val LEAK_CANARY = "com.squareup.leakcanary:leakcanary-android:${Version.LEAK_CANARY}"
    }

    object Obfuscation {
        const val SQL_CIPHER = "net.zetetic:android-database-sqlcipher:${Version.SQL_CIPHER}"
        const val SQLITE_KTX = "androidx.sqlite:sqlite-ktx:${Version.SQLITE_KTX}"
    }

    object AndroidX {
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Version.SUPPORT_LIBRARY}"
        const val SUPPORT_LEGACY = "androidx.legacy:legacy-support-v4:${Version.SUPPORT_LEGACY}"
        const val CONSTRAINT_LAYOUT =
            "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
    }

    const val KOTLIN_STANDARD_LIBRARY =
        "org.jetbrains.kotlin:kotlin-stdlib:${Version.KOTLIN_GRADLE_PLUGIN}"

    const val ANDROID_GRADLE_PLUGIN =
        "com.android.tools.build:gradle:${Version.ANDROID_GRADLE_PLUGIN}"

    const val KOTLIN_GRADLE_PLUGIN =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.KOTLIN_GRADLE_PLUGIN}"

    const val KTX_CORE = "androidx.core:core-ktx:${Version.KTX_CORE}"

    const val KTX_ACTIVITY = "androidx.activity:activity-ktx:${Version.KTX_ACTIVITY}"

    const val KTX_FRAGMENT = "androidx.fragment:fragment-ktx:${Version.KTX_FRAGMENT}"

    const val COIL = "io.coil-kt:coil:${Version.COIL}"

    const val TIMBER = "com.jakewharton.timber:timber:${Version.TIMBER}"

    object Test {
        const val JUNIT = "junit:junit:${Version.JUNIT}"
        const val MOCKITO = "org.mockito:mockito-core:${Version.MOCKITO}"
        const val MOCKITO_INLINE = "org.mockito:mockito-inline:${Version.MOCKITO}"
        const val MOCKITO_KOTLIN = "org.mockito.kotlin:mockito-kotlin:${Version.MOCKITO_KOTLIN}"
    }

    object AndroidTest {
        const val JUNIT = "androidx.test.ext:junit:${Version.ANDROID_TEST_JUNIT}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Version.ANDROID_TEST_ESPRESSO}"
    }

    object Google {
        const val MATERIAL = "com.google.android.material:material:${Version.MATERIAL}"
    }

    object Navigation {
        const val UI = "androidx.navigation:navigation-ui-ktx:${Version.NAVIGATION}"
        const val FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Version.NAVIGATION}"
        const val DYNAMIC_FEATURE =
            "androidx.navigation:navigation-dynamic-features-fragment:${Version.NAVIGATION}"
        const val SAFE_ARGS_GRADLE_PLUGIN =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.NAVIGATION}"
    }

    object Lifecycle {
        const val VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.LIFECYCLE}"
        const val LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.LIFECYCLE}"
        const val COMMON = "androidx.lifecycle:lifecycle-common-java8:${Version.LIFECYCLE}"
    }

    object DaggerHilt {
        const val GRADLE_PLUGIN =
            "com.google.dagger:hilt-android-gradle-plugin:${Version.DAGGER_HILT}"
        const val ANDROID = "com.google.dagger:hilt-android:${Version.DAGGER_HILT}"
        const val COMPILER = "com.google.dagger:hilt-android-compiler:${Version.DAGGER_HILT}"
    }

    object Hilt {
        const val COMPILER = "androidx.hilt:hilt-compiler:${Version.HILT}"
    }

    object FlowBinding {
        const val ANDROID =
            "io.github.reactivecircus.flowbinding:flowbinding-android:${Version.FLOW_BINDING}"
        const val APPCOMPAT =
            "io.github.reactivecircus.flowbinding:flowbinding-appcompat:${Version.FLOW_BINDING}"
    }

    object Retrofit {
        const val MAIN = "com.squareup.retrofit2:retrofit:${Version.RETROFIT}"
        const val CONVERTER_MOSHI = "com.squareup.retrofit2:converter-moshi:${Version.RETROFIT}"
    }

    object OKHTTP3 {
        const val MAIN = "com.squareup.okhttp3:okhttp:${Version.OKHTTP}"
        const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Version.OKHTTP}"
    }

    object Room {
        const val RUNTIME = "androidx.room:room-runtime:${Version.ROOM}"
        const val KTX = "androidx.room:room-ktx:${Version.ROOM}"
        const val COMPILER = "androidx.room:room-compiler:${Version.ROOM}"
    }

    object Coroutines {
        const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.COROUTINES}"
        const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.COROUTINES}"
    }

    object Paging {
        const val RUNTIME = "androidx.paging:paging-runtime:${Version.PAGING}"
    }
}