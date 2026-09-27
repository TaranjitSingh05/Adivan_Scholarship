package com.example.adivan.data.jago

import com.google.gson.annotations.SerializedName

data class ChatMessageDto(
    val role: String,
    val content: String,
)

data class ChatRequest(
    val message: String,
    val conversation: List<ChatMessageDto>,
    @SerializedName("appContext") val appContext: Map<String, Any?>,
)

data class ChatResponse(
    val reply: String?,
    val error: String?,
)

data class ChatErrorResponse(
    val error: String?,
)
