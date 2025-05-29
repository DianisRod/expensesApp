plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "2.1.20-2.0.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
    id("de.jensklingenberg.ktorfit") version "2.5.1"
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.10"
}

android {
    namespace = "com.dr.expensesapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dr.expensesapp"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

val ktorfitVersion = "2.5.1"
val ktor = "3.1.0"
val compose_ui_version = "1.7.8"
dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.fontawesomecompose)
    implementation (libs.compose.currencytext)
    implementation("androidx.compose.material3:material3:1.4.0-alpha15")
    implementation ("androidx.compose.material:material-icons-extended:$1.8.0")

    implementation("androidx.compose.ui:ui:$compose_ui_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")
    implementation("androidx.compose.material:material:$compose_ui_version")
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_ui_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_ui_version")

    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_ui_version")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_ui_version")

    implementation("de.jensklingenberg.ktorfit:ktorfit-lib:$ktorfitVersion")
    implementation("io.ktor:ktor-client-serialization:$ktor")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor")
    implementation("de.jensklingenberg.ktorfit:ktorfit-converters-response:$ktorfitVersion")
    implementation("de.jensklingenberg.ktorfit:ktorfit-converters-call:$ktorfitVersion")
    implementation("de.jensklingenberg.ktorfit:ktorfit-converters-flow:$ktorfitVersion")

    // https://mvnrepository.com/artifact/io.ktor/ktor-client-okhttp
    implementation("io.ktor:ktor-client-okhttp:3.1.3")

    //implementation("org.json:json:20231018")
}