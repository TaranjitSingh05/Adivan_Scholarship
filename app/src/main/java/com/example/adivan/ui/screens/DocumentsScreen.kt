package com.example.adivan.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.data.DemoData
import com.example.adivan.data.DocumentStatus
import com.example.adivan.data.StudentDocument
import com.example.adivan.ui.components.AdivanCard
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SuccessGreen
import com.example.adivan.ui.theme.TextSecondary
import com.example.adivan.ui.theme.WarningOrange

@Composable
fun DocumentsScreen(
    contentPadding: PaddingValues = PaddingValues(),
    onMenuClick: () -> Unit,
    onDocumentClick: (String) -> Unit,
) {
    var tab by remember { mutableIntStateOf(0) }
    val tabs = listOf("All Documents", "Verified", "Pending")
    val filtered = when (tab) {
        1 -> DemoData.documents.filter { it.status == DocumentStatus.Verified }
        2 -> DemoData.documents.filter { it.status != DocumentStatus.Verified }
        else -> DemoData.documents
    }

    Column(modifier = Modifier.fillMaxSize().padding(contentPadding)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = DarkGreen)
            }
            Text("My Documents", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }
        TabRow(selectedTabIndex = tab) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = tab == index, onClick = { tab = index }, text = { Text(title, fontSize = 12.sp) })
            }
        }
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(filtered) { doc ->
                DocumentRow(doc = doc, onClick = { onDocumentClick(doc.id) })
                Spacer(modifier = Modifier.height(10.dp))
            }
            item {
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkGreen),
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text(" Add Document")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DocumentRow(doc: StudentDocument, onClick: () -> Unit) {
    AdivanCard(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val prefix = when (doc.status) {
                DocumentStatus.Verified -> "✓"
                DocumentStatus.ActionRequired -> "⚠"
                DocumentStatus.Pending -> "○"
            }
            Text(prefix, fontSize = 18.sp, color = when (doc.status) {
                DocumentStatus.Verified -> SuccessGreen
                DocumentStatus.ActionRequired -> WarningOrange
                DocumentStatus.Pending -> TextSecondary
            })
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(doc.name, fontWeight = FontWeight.Medium)
                val statusLine = buildString {
                    when (doc.status) {
                        DocumentStatus.Verified -> append("Verified")
                        DocumentStatus.ActionRequired -> append("Action Required")
                        DocumentStatus.Pending -> append("Pending")
                    }
                    doc.source?.let { append(" • $it") }
                }
                Text(statusLine, fontSize = 12.sp, color = TextSecondary)
            }
            if (doc.status == DocumentStatus.ActionRequired) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = WarningOrange)
            }
        }
    }
}
