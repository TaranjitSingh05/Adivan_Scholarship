package com.example.adivan.ui.jago

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adivan.data.jago.ChatMessageDto
import com.example.adivan.data.jago.JagoChatResult
import com.example.adivan.data.jago.JagoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class JagoUiMessage(
    val id: Long,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: String,
    val isError: Boolean = false,
)

data class JagoUiState(
    val messages: List<JagoUiMessage> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false,
)

class JagoViewModel(
    private val screenContext: String,
    private val repository: JagoRepository = JagoRepository(),
) : ViewModel() {

    private val _state = MutableStateFlow(JagoUiState())
    val state: StateFlow<JagoUiState> = _state.asStateFlow()

    private var nextId = 0L

    fun onInputChange(text: String) {
        _state.update { it.copy(inputText = text) }
    }

    fun sendMessage() {
        val text = _state.value.inputText.trim()
        if (text.isEmpty() || _state.value.isLoading) return
        _state.update { it.copy(inputText = "") }
        sendUserMessage(text)
    }

    fun sendQuickAction(question: String) {
        if (_state.value.isLoading) return
        sendUserMessage(question)
    }

    fun retryLastUserMessage() {
        if (_state.value.isLoading) return
        val messages = _state.value.messages
        if (messages.lastOrNull()?.isError != true) return
        val lastUser = messages.lastOrNull { it.isFromUser } ?: return
        _state.update { it.copy(messages = messages.dropLast(1), isLoading = true) }

        viewModelScope.launch {
            val history = buildConversationHistory()
            when (val result = repository.sendMessage(lastUser.text, history, screenContext)) {
                is JagoChatResult.Success -> appendAssistantMessage(result.reply)
                is JagoChatResult.Error -> appendErrorMessage(result.message)
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun sendUserMessage(text: String) {
        appendUserMessage(text)
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val history = buildConversationHistory()
            when (val result = repository.sendMessage(text, history, screenContext)) {
                is JagoChatResult.Success -> appendAssistantMessage(result.reply)
                is JagoChatResult.Error -> appendErrorMessage(result.message)
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun buildConversationHistory(): List<ChatMessageDto> {
        var msgs = _state.value.messages.filter { !it.isError }
        if (msgs.lastOrNull()?.isFromUser == true) {
            msgs = msgs.dropLast(1)
        }
        return msgs.map {
            ChatMessageDto(
                role = if (it.isFromUser) "user" else "assistant",
                content = it.text,
            )
        }
    }

    private fun appendUserMessage(text: String) {
        _state.update { it.copy(messages = it.messages + createMessage(text, isFromUser = true)) }
    }

    private fun appendAssistantMessage(text: String) {
        _state.update { it.copy(messages = it.messages + createMessage(text, isFromUser = false)) }
    }

    private fun appendErrorMessage(text: String) {
        _state.update {
            it.copy(messages = it.messages + createMessage(text, isFromUser = false, isError = true))
        }
    }

    private fun createMessage(
        text: String,
        isFromUser: Boolean,
        isError: Boolean = false,
    ): JagoUiMessage {
        nextId += 1
        return JagoUiMessage(
            id = nextId,
            text = text,
            isFromUser = isFromUser,
            timestamp = timeNow(),
            isError = isError,
        )
    }

    private fun timeNow(): String =
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
}
