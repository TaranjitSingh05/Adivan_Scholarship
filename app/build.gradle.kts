import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.Collections
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

fun devLanHost(): String {
    val local = rootProject.file("local.properties")
    if (local.exists()) {
        val props = Properties()
        local.inputStream().use { props.load(it) }
        props.getProperty("jago.dev.host")?.trim()?.takeIf { it.isNotEmpty() }?.let { return it }
    }
    val virtualName = Regex("virtual|vbox|vmware|wsl|hyper-v|docker|loopback", RegexOption.IGNORE_CASE)
    val candidates = Collections.list(NetworkInterface.getNetworkInterfaces())
        .filter { nic -> nic.isUp && !nic.isLoopback && !virtualName.containsMatchIn(nic.displayName) && !virtualName.containsMatchIn(nic.name) }
        .flatMap { nic -> Collections.list(nic.inetAddresses) }
        .filterIsInstance<Inet4Address>()
        .mapNotNull { it.hostAddress }
        .filter { address -> !address.startsWith("169.254.") && !address.startsWith("192.168.56.") }
    return candidates.firstOrNull { it.startsWith("192.168.") }
        ?: candidates.firstOrNull { it.startsWith("10.") }
        ?: candidates.firstOrNull()
        ?: "10.0.2.2"
}

android {
    namespace = "com.example.adivan"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.adivan"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        // Debug: JagoEndpoint uses JAGO_LAN_HOST (or emulator 10.0.2.2). Release overrides JAGO_BASE_URL.
        buildConfigField("String", "JAGO_BASE_URL", "\"\"")
        buildConfigField("String", "JAGO_LAN_HOST", "\"${devLanHost()}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            buildConfigField(
                "String",
                "JAGO_BASE_URL",
                "\"https://adivan-scholarship-backend.onrender.com/\"",
            )
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}