package com.example.adivan.data.jago

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

sealed class JagoChatResult {
    data class Success(val reply: String) : JagoChatResult()
    data class Error(val message: String, val isNetwork: Boolean = false) : JagoChatResult()
}

class JagoRepository(
    private val api: JagoApiService = defaultApi(),
) {

    suspend fun sendMessage(
        message: String,
        conversation: List<ChatMessageDto>,
        screenContext: String,
    ): JagoChatResult {
        val request = ChatRequest(
            message = message,
            conversation = conversation,
            appContext = JagoAppContextBuilder.build(screenContext),
        )
        return try {
            val response = api.chat(request)
            val reply = response.reply
            if (!reply.isNullOrBlank()) {
                JagoChatResult.Success(reply)
            } else {
                Log.e(TAG, "Empty reply from ${JagoEndpoint.baseUrl()}")
                JagoChatResult.Error(UNAVAILABLE)
            }
        } catch (e: HttpException) {
            val body = e.response()?.errorBody()?.string().orEmpty()
            Log.e(TAG, "Backend HTTP ${e.code()} from ${JagoEndpoint.baseUrl()}: $body")
            JagoChatResult.Error(UNAVAILABLE)
        } catch (e: UnknownHostException) {
            Log.e(TAG, "Host not found for ${JagoEndpoint.baseUrl()}", e)
            JagoChatResult.Error(NO_INTERNET, isNetwork = true)
        } catch (e: ConnectException) {
            Log.e(TAG, "Backend unreachable at ${JagoEndpoint.baseUrl()}", e)
            JagoChatResult.Error(UNAVAILABLE)
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, "Timeout talking to ${JagoEndpoint.baseUrl()}", e)
            JagoChatResult.Error(UNAVAILABLE)
        } catch (e: IOException) {
            Log.e(TAG, "Network error talking to ${JagoEndpoint.baseUrl()}: ${e.message}", e)
            JagoChatResult.Error(NO_INTERNET, isNetwork = true)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected chat failure: ${e.message}", e)
            JagoChatResult.Error(UNAVAILABLE)
        }
    }

    companion object {
        private const val TAG = "JAGO"
        const val UNAVAILABLE = "JAGO is temporarily unavailable. Please try again."
        const val NO_INTERNET = "Please check your internet connection and try again."

        private fun defaultApi(): JagoApiService {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(ensureTrailingSlash(JagoEndpoint.baseUrl()))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(JagoApiService::class.java)
        }

        private fun ensureTrailingSlash(url: String): String =
            if (url.endsWith("/")) url else "$url/"
    }
}
