plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}
group = "com.github.langara.sandboxui"
version = "0.0.1"

android {
    compileSdkVersion(Vers.androidCompileSdk)

    defaultConfig {
        minSdkVersion(Vers.androidMinSdk)
        targetSdkVersion(Vers.androidTargetSdk)
        versionCode = 100
        versionName = "0.0.1"
    }
}

dependencies {
    implementation(Deps.kotlinStdlib7)
    implementation(Deps.kotlinReflect)
    api(Deps.splitties)
}

apply(from = "https://raw.githubusercontent.com/sky-uk/gradle-maven-plugin/master/gradle-mavenizer.gradle")
