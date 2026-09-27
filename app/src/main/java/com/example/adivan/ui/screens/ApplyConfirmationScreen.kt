package com.example.adivan.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.data.DemoData
import com.example.adivan.ui.components.AdivanCard
import com.example.adivan.ui.components.AdivanTopBar
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen
import com.example.adivan.ui.theme.SuccessGreen
import com.example.adivan.ui.theme.TextSecondary

@Composable
fun ApplyConfirmationScreen(
    schemeName: String,
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AdivanTopBar(title = "Application", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("✓", fontSize = 48.sp, color = SuccessGreen)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Application Submitted", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(12.dp))
            AdivanCard(modifier = Modifier.fillMaxWidth(), containerColor = SoftGreen) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(schemeName, fontWeight = FontWeight.SemiBold, color = DarkGreen)
                    Text(
                        "Your application has been recorded for ${DemoData.student.name}. " +
                            "You will receive updates as verification progresses.",
                        textAlign = TextAlign.Start,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onDone,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            ) {
                Text("Back to Home")
            }
        }
    }
}
