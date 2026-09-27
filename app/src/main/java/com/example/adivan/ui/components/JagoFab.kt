package com.example.adivan.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.ui.theme.DarkGreen

@Composable
fun JagoFab(
    visible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn(animationSpec = tween(280)) + scaleIn(
            initialScale = 0.92f,
            animationSpec = tween(280),
        ),
    ) {
        Surface(
            onClick = onClick,
            shape = CircleShape,
            color = DarkGreen,
            shadowElevation = 8.dp,
            modifier = Modifier.size(72.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    Icons.Default.SmartToy,
                    contentDescription = "Open JAGO",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp),
                )
                Text(
                    text = "JAGO",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                )
            }
        }
    }
}
