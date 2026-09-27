package com.example.adivan.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.ui.components.TribalPatternBand
import com.example.adivan.ui.theme.Cream
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen
import com.example.adivan.ui.theme.SoftGreenAccent
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2500)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            TribalPatternBand()
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(SoftGreen),
                contentAlignment = Alignment.Center,
            ) {
                Text("🇮🇳", fontSize = 36.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Government of India",
                fontSize = 13.sp,
                color = DarkGreen,
                fontWeight = FontWeight.Medium,
            )
            Text(
                "Ministry of Tribal Affairs",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                "Adivan",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen,
            )
            Text(
                "Tribal Scholarships",
                fontSize = 18.sp,
                color = DarkGreen.copy(alpha = 0.85f),
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Education • Empowerment • Opportunity",
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.weight(1f))
            LandscapeIllustration(modifier = Modifier.fillMaxWidth().height(160.dp))
            Spacer(modifier = Modifier.height(24.dp))
            TribalPatternBand()
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun LandscapeIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.background(Cream)) {
        val w = size.width
        val h = size.height
        drawRect(SoftGreen.copy(alpha = 0.4f), topLeft = Offset(0f, h * 0.35f), size = size.copy(height = h * 0.65f))
        val hill1 = Path().apply {
            moveTo(0f, h * 0.55f)
            quadraticTo(w * 0.25f, h * 0.2f, w * 0.5f, h * 0.5f)
            quadraticTo(w * 0.75f, h * 0.75f, w, h * 0.45f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(hill1, DarkGreen.copy(alpha = 0.35f))
        val hill2 = Path().apply {
            moveTo(0f, h * 0.7f)
            quadraticTo(w * 0.4f, h * 0.45f, w, h * 0.65f)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(hill2, DarkGreen.copy(alpha = 0.55f))
        drawCircle(SoftGreenAccent, radius = h * 0.12f, center = Offset(w * 0.82f, h * 0.22f))
        drawRect(DarkGreen, topLeft = Offset(w * 0.08f, h * 0.38f), size = androidx.compose.ui.geometry.Size(w * 0.04f, h * 0.18f))
        drawCircle(DarkGreen.copy(alpha = 0.7f), radius = w * 0.06f, center = Offset(w * 0.1f, h * 0.32f))
    }
}
