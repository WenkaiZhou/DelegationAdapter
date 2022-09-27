plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 14
        targetSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

}

dependencies {
    compileOnly("androidx.recyclerview:recyclerview:1.2.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
}

// Release maven configuration
setProperty("POM_ARTIFACT_ID", "delegationadapter")
setProperty("POM_NAME", "DelegationAdapter")
setProperty("VERSION_CODE", "26")
setProperty("VERSION_NAME", "2.0.8")
setProperty("POM_DESCRIPTION", "Delegation Adapter")

apply(plugin = "com.vanniktech.maven.publish")