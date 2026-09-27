package com.example.adivan.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.ui.components.AdivanCard
import com.example.adivan.ui.components.AdivanTopBar
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen
import com.example.adivan.ui.theme.SuccessGreen
import com.example.adivan.ui.theme.TextSecondary

@Composable
fun EligibilityCheckScreen(
    schemeName: String,
    onBack: () -> Unit,
    onApplyNow: () -> Unit,
) {
    var showResult by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        AdivanTopBar(title = "Eligibility Check", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            Text(schemeName, fontWeight = FontWeight.Medium, color = TextSecondary, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { if (showResult) 1f else 0.75f },
                modifier = Modifier.fillMaxWidth(),
                color = DarkGreen,
            )
            Text("Step 4 of 4", fontSize = 12.sp, color = TextSecondary, modifier = Modifier.padding(top = 4.dp))

            if (!showResult) {
                Spacer(modifier = Modifier.height(20.dp))
                StepContent("Step 1 — Category", "ST (Scheduled Tribe)")
                Spacer(modifier = Modifier.height(12.dp))
                StepContent("Step 2 — Education Level", "B.Tech (Undergraduate)")
                Spacer(modifier = Modifier.height(12.dp))
                StepContent("Step 3 — Current Year", "3rd Year")
                Spacer(modifier = Modifier.height(12.dp))
                StepContent("Step 4 — Annual Family Income", "₹2,00,000 or below")
                Spacer(modifier = Modifier.height(12.dp))
                StepContent("Institution Type", "Government / Recognized")
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { showResult = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                ) {
                    Text("Check Eligibility →")
                }
            } else {
                Spacer(modifier = Modifier.height(24.dp))
                AdivanCard(modifier = Modifier.fillMaxWidth(), containerColor = SoftGreen) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("✓ You appear eligible for this scheme.", fontWeight = FontWeight.Bold, color = SuccessGreen)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Based on the information provided, you can proceed with the application.",
                            fontSize = 14.sp,
                            color = TextSecondary,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onApplyNow,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                ) {
                    Text("Apply Now")
                }
            }
        }
    }
}

@Composable
private fun StepContent(label: String, value: String) {
    Text(label, fontWeight = FontWeight.SemiBold, color = DarkGreen, fontSize = 13.sp)
    Spacer(modifier = Modifier.height(6.dp))
    AdivanCard(modifier = Modifier.fillMaxWidth()) {
        Text(value, modifier = Modifier.padding(16.dp), fontSize = 15.sp)
    }
}
