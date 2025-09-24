// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        maven {
            isAllowInsecureProtocol = true
            setUrl("http://maven.aliyun.com/nexus/content/groups/public/")
        }
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")

        classpath("com.vanniktech:gradle-maven-publish-plugin:0.33.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        maven {
            isAllowInsecureProtocol = true
            setUrl("http://maven.aliyun.com/nexus/content/groups/public/")
        }
        google()
        mavenCentral()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}