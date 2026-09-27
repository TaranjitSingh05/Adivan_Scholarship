package com.example.adivan.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.data.DocumentStatus
import com.example.adivan.data.StudentDocument
import com.example.adivan.ui.components.AdivanCard
import com.example.adivan.ui.components.AdivanTopBar
import com.example.adivan.ui.theme.TextSecondary

@Composable
fun DocumentDetailScreen(document: StudentDocument, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        AdivanTopBar(title = document.name, onBack = onBack)
        AdivanCard(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Column(modifier = Modifier.padding(20.dp)) {
                DetailLine("Document", document.name)
                DetailLine(
                    "Status",
                    when (document.status) {
                        DocumentStatus.Verified -> "Verified"
                        DocumentStatus.ActionRequired -> "Action Required"
                        DocumentStatus.Pending -> "Pending"
                    },
                )
                DetailLine("Source", document.source ?: "Manual upload")
                DetailLine("Last verified", document.lastVerified ?: "—")
                DetailLine("Document number", document.maskedNumber)
            }
        }
    }
}

@Composable
private fun DetailLine(label: String, value: String) {
    Text(label, fontWeight = FontWeight.Medium, fontSize = 13.sp)
    Text(value, color = TextSecondary, modifier = Modifier.padding(bottom = 12.dp))
}
