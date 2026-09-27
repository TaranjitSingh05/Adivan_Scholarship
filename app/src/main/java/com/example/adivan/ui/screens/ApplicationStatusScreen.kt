package com.example.adivan.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.data.DemoData
import com.example.adivan.ui.components.AdivanCard
import com.example.adivan.ui.components.AdivanTopBar
import com.example.adivan.ui.components.VerticalTimeline
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen
import com.example.adivan.ui.theme.TextSecondary

@Composable
fun ApplicationStatusScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        AdivanTopBar(title = "Application Status", onBack = onBack)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            AdivanCard(modifier = Modifier.fillMaxWidth(), containerColor = SoftGreen) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(DemoData.currentScholarshipName, fontWeight = FontWeight.Bold, color = DarkGreen)
                    Text("Application ID: ${DemoData.currentApplicationNo}", fontSize = 13.sp, color = TextSecondary)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            VerticalTimeline(items = DemoData.applicationTimeline)
            Spacer(modifier = Modifier.height(16.dp))
            AdivanCard(modifier = Modifier.fillMaxWidth(), containerColor = SoftGreen) {
                Text(
                    "You will be notified once the next stage is completed.",
                    fontSize = 13.sp,
                    color = DarkGreen,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}
