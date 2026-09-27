package com.example.adivan.data.jago

import android.os.Build
import com.example.adivan.BuildConfig

object JagoEndpoint {
    private const val PRODUCTION_BASE_URL = "https://adivan-scholarship-backend.onrender.com/"

    fun baseUrl(): String {
        if (!BuildConfig.DEBUG) {
            val configured = BuildConfig.JAGO_BASE_URL.trim()
            val production = if (configured.isNotEmpty()) configured else PRODUCTION_BASE_URL
            return if (production.endsWith("/")) production else "$production/"
        }
        val host = if (isEmulator()) "10.0.2.2" else BuildConfig.JAGO_LAN_HOST
        return "http://$host:3000/"
    }

    private fun isEmulator(): Boolean {
        val fingerprint = Build.FINGERPRINT
        val model = Build.MODEL
        val hardware = Build.HARDWARE
        val product = Build.PRODUCT
        return fingerprint.startsWith("generic")
            || fingerprint.contains("emulator", ignoreCase = true)
            || fingerprint.contains("sdk_gphone", ignoreCase = true)
            || model.contains("Emulator", ignoreCase = true)
            || model.contains("Android SDK built for", ignoreCase = true)
            || hardware.contains("goldfish", ignoreCase = true)
            || hardware.contains("ranchu", ignoreCase = true)
            || product.contains("sdk", ignoreCase = true)
    }
}
