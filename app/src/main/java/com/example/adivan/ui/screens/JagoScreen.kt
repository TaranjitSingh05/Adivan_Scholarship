package com.example.adivan.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adivan.ui.jago.JagoUiMessage
import com.example.adivan.ui.jago.JagoViewModel
import com.example.adivan.ui.jago.JagoViewModelFactory
import com.example.adivan.ui.theme.Cream
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen
import com.example.adivan.ui.theme.SuccessGreen
import com.example.adivan.ui.theme.TextPrimary
import com.example.adivan.ui.theme.TextSecondary

private data class QuickQuestion(val label: String, val prompt: String)

private val quickQuestions = listOf(
    QuickQuestion("Am I eligible?", "Am I eligible?"),
    QuickQuestion("Required documents", "What documents do I need?"),
    QuickQuestion("Check my application", "What's my application status?"),
    QuickQuestion("Scholarship schemes", "Which scholarship can I apply for?"),
    QuickQuestion("Fund status", "When will I receive my funds?"),
)

@Composable
fun JagoScreen(
    screenContext: String,
    onBack: () -> Unit,
) {
    val viewModel: JagoViewModel = viewModel(factory = JagoViewModelFactory(screenContext))
    val state by viewModel.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val showWelcome = state.messages.none { it.isFromUser }

    LaunchedEffect(state.messages.size, state.isLoading, showWelcome) {
        if (!showWelcome) {
            val extra = if (state.isLoading) 1 else 0
            val last = state.messages.size + extra
            if (last > 0) listState.animateScrollToItem(last - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .imePadding(),
    ) {
        JagoHeader(onBack = onBack)
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFFF7F8F6)),
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (showWelcome) {
                item(key = "welcome") {
                    WelcomeSection(onSelect = viewModel::sendQuickAction, enabled = !state.isLoading)
                }
            }
            items(state.messages, key = { it.id }) { message ->
                ChatBubble(message = message, onRetry = viewModel::retryLastUserMessage)
            }
            if (state.isLoading) {
                item(key = "typing") { TypingIndicator() }
            }
        }
        JagoInputBar(
            text = state.inputText,
            onTextChange = viewModel::onInputChange,
            onSend = viewModel::sendMessage,
            enabled = !state.isLoading,
        )
    }
}

@Composable
private fun JagoHeader(onBack: () -> Unit) {
    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 16.dp, top = 8.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DarkGreen)
            }
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(DarkGreen),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Default.School,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp),
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("JAGO", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = TextPrimary)
                Text(
                    "Adivan Scholarship Assistant",
                    fontSize = 12.sp,
                    color = TextSecondary,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(SuccessGreen),
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Online", fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Medium)
                }
            }
        }
        HorizontalDivider(color = SoftGreen)
    }
}

@Composable
private fun WelcomeSection(
    onSelect: (String) -> Unit,
    enabled: Boolean,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(DarkGreen),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Default.School, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("JAGO", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = DarkGreen)
                Text("Your Adivan Scholarship Assistant", fontSize = 13.sp, color = TextSecondary)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Hi Rahul 👋\nI'm your Adivan Scholarship Assistant.",
            color = TextPrimary,
            fontSize = 15.sp,
            lineHeight = 22.sp,
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text("How can I help?", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = DarkGreen)
        Spacer(modifier = Modifier.height(10.dp))
        quickQuestions.forEach { question ->
            Surface(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .clickable(enabled = enabled) { onSelect(question.prompt) },
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                shadowElevation = 1.dp,
                border = BorderStroke(1.dp, SoftGreen),
            ) {
                Text(
                    text = question.label,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    color = DarkGreen,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
private fun ChatBubble(
    message: JagoUiMessage,
    onRetry: () -> Unit,
) {
    val bubbleColor = when {
        message.isFromUser -> DarkGreen
        message.isError -> Color(0xFFFFF1F0)
        else -> Cream
    }
    val textColor = when {
        message.isFromUser -> Color.White
        message.isError -> Color(0xFF8C2F2F)
        else -> TextPrimary
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
    ) {
        if (!message.isFromUser) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(DarkGreen),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Default.School,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column(horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start) {
            Box(
                modifier = Modifier
                    .widthIn(max = 280.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (message.isFromUser) 16.dp else 4.dp,
                            bottomEnd = if (message.isFromUser) 4.dp else 16.dp,
                        ),
                    )
                    .background(bubbleColor)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
            ) {
                Text(text = message.text, color = textColor, fontSize = 15.sp, lineHeight = 21.sp)
            }
            Text(
                text = message.timestamp,
                fontSize = 10.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp, start = 2.dp, end = 2.dp),
            )
            if (message.isError) {
                TextButton(onClick = onRetry, contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)) {
                    Text("Retry", color = DarkGreen, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun TypingIndicator() {
    val transition = rememberInfiniteTransition(label = "typing")
    Row(
        modifier = Modifier.padding(start = 34.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("JAGO is typing", color = TextSecondary, fontSize = 13.sp)
        Spacer(modifier = Modifier.width(6.dp))
        repeat(3) { index ->
            val alpha by transition.animateFloat(
                initialValue = 0.25f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 500, delayMillis = index * 140),
                    repeatMode = RepeatMode.Reverse,
                ),
                label = "dot$index",
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(6.dp)
                    .alpha(alpha)
                    .clip(CircleShape)
                    .background(DarkGreen),
            )
        }
    }
}

@Composable
private fun JagoInputBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    enabled: Boolean,
) {
    val canSend = text.isNotBlank() && enabled
    Surface(
        color = Color.White,
        shadowElevation = 8.dp,
        modifier = Modifier
            .navigationBarsPadding()
            .padding(bottom = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, SoftGreen, RoundedCornerShape(28.dp))
                .background(Color(0xFFF7F8F6))
                .padding(start = 16.dp, end = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp),
                enabled = enabled,
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = TextPrimary,
                    fontSize = 15.sp,
                ),
                cursorBrush = SolidColor(DarkGreen),
                maxLines = 4,
                decorationBox = { inner ->
                    Box {
                        if (text.isEmpty()) {
                            Text(
                                "Ask JAGO about scholarships...",
                                color = TextSecondary,
                                fontSize = 15.sp,
                            )
                        }
                        inner()
                    }
                },
            )
            IconButton(onClick = onSend, enabled = canSend) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = if (canSend) DarkGreen else Color(0xFFBDBDBD),
                )
            }
        }
    }
}
