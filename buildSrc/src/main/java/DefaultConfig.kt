object DefaultConfig {

    const val APPLICATION_ID = "com.app.firstsubmission"

    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

    object Version {
        const val compileSdk = 30
        const val minSdk = 21
        const val targetSdk = 30
        const val appVersionCode = 1
        const val appVersionName = "1.0"
    }

    object FieldType {
        const val TYPE_STRING = "String"
    }

    object FieldKey {
        const val TMDB_API_KEY = "TMDB_API_KEY"
        const val BASE_URL_TMDB = "BASE_URL_TMDB"
        const val BASE_URL_IMAGE_TMDB = "BASE_URL_IMAGE_TMDB"
    }

}