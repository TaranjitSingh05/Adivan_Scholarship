package com.example.adivan.data.jago

import retrofit2.http.Body
import retrofit2.http.POST

interface JagoApiService {
    @POST("api/chat")
    suspend fun chat(@Body request: ChatRequest): ChatResponse
}
