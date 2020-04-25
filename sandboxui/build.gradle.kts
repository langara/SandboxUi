plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}
group = "com.github.langara.sandboxui"
version = "0.0.5"

android {
    compileSdkVersion(Vers.androidCompileSdk)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(Vers.androidTargetSdk)
        versionCode = 600
        versionName = "0.0.6"
    }
}

dependencies {
    implementation(Deps.kotlinStdlib7)
    implementation(Deps.splitties)
    api(Deps.androidxFlexboxLayout)
}

apply(from = "https://raw.githubusercontent.com/sky-uk/gradle-maven-plugin/master/gradle-mavenizer.gradle")
