plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 33
    namespace = "com.kevin.delegationadapter.extras"

    defaultConfig {
        minSdk = 14
        targetSdk = 33

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
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.6.21")
    compileOnly("androidx.databinding:databinding-runtime:4.2.1")
    compileOnly(project(":delegationadapter"))
}

// Release maven configuration
setProperty("POM_ARTIFACT_ID", "delegationadapter-extras")
setProperty("POM_NAME", "DelegationAdapter-extras")
setProperty("VERSION_CODE", "28")
setProperty("VERSION_NAME", "2.1.0")
setProperty("POM_DESCRIPTION", "Delegation Adapter extras")

apply(plugin = "com.vanniktech.maven.publish")