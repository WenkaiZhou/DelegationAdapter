plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 33
    namespace = "com.kevin.delegationadapter.sample"

    defaultConfig {
        applicationId = "com.kevin.delegationadapter.sample"
        minSdk = 14
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
    }

}

dependencies {
    implementation(fileTree(baseDir = "libs") {
        include("*.jar", "*.aar")
    })
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.zwenkai:loopview:2.0.1")
    implementation(project(":delegationadapter"))
    implementation(project(":delegationadapter-extras"))
//    implementation("com.zwenkai:delegationadapter:2.1.0")
//    implementation("com.zwenkai:delegationadapter-extras:2.1.0")
    implementation("com.scwang.smartrefresh:SmartRefreshLayout:1.1.0-alpha-6")
    implementation("androidx.viewpager:viewpager:1.0.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
}
