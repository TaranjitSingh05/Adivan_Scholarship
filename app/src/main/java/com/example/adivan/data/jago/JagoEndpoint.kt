package com.example.adivan.data.jago

import android.os.Build
import com.example.adivan.BuildConfig

object JagoEndpoint {
    fun baseUrl(): String {
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
