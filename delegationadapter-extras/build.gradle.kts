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
    compileOnly("androidx.recyclerview:recyclerview:1.2.0-beta01")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    compileOnly("androidx.databinding:databinding-runtime:4.2.1")
    compileOnly(project(":delegationadapter"))
}

// Release maven configuration
setProperty("POM_ARTIFACT_ID", "delegationadapter-extras")
setProperty("POM_NAME", "DelegationAdapter-extras")
setProperty("VERSION_CODE", "27")
setProperty("VERSION_NAME", "2.0.9")
setProperty("POM_DESCRIPTION", "Delegation Adapter extras")

apply(plugin = "com.vanniktech.maven.publish")