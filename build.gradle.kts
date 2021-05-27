// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath (Dependencies.ANDROID_GRADLE_PLUGIN)
        classpath (Dependencies.KOTLIN_GRADLE_PLUGIN)
        classpath (Dependencies.DaggerHilt.GRADLE_PLUGIN)
        classpath (Dependencies.Navigation.SAFE_ARGS_GRADLE_PLUGIN)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean").configure {
    delete("build")
}