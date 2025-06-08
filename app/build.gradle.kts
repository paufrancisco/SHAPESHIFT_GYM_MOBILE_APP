import org.apache.tools.ant.util.JavaEnvUtils.VERSION_1_8


plugins {
    id("com.android.application")
    id("com.google.gms.google-services")


}

android {
    namespace = "com.jtdev.shape_shift"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jtdev.shape_shift"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        testOptions {
            unitTests.isReturnDefaultValues = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_16
        targetCompatibility = JavaVersion.VERSION_16
    }
    buildToolsVersion = "34.0.0"
}

dependencies {

    implementation ("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-database:20.3.1")



    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")

    //for giph
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.google.firebase:firebase-auth:23.2.1")
    implementation("androidx.credentials:credentials:1.5.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")





    //add test
    testImplementation ("org.mockito:mockito-core:3.11.2")
    testImplementation("junit:junit:4.12")
    testImplementation("junit:junit:4.12")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test:core:1.2.0")
    androidTestImplementation ("androidx.test:core:1.4.0")




}