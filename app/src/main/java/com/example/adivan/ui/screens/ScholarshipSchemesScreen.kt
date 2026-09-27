package com.example.adivan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.data.DemoData
import com.example.adivan.data.ScholarshipScheme
import com.example.adivan.ui.components.AdivanCard
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.TextSecondary

@Composable
fun ScholarshipSchemesScreen(
    contentPadding: PaddingValues = PaddingValues(),
    onMenuClick: () -> Unit,
    onSchemeClick: (String) -> Unit,
) {
    var query by remember { mutableStateOf("") }
    val filtered = DemoData.schemes.filter {
        query.isBlank() || it.name.contains(query, ignoreCase = true) ||
            it.shortDescription.contains(query, ignoreCase = true)
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
            Text("Scholarship Schemes", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search schemes...") },
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(filtered) { scheme ->
                SchemeListCard(scheme = scheme, onClick = { onSchemeClick(scheme.id) })
                Spacer(modifier = Modifier.height(12.dp))
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

private fun schemeIconColor(schemeId: String): Color = when (schemeId) {
    "pre_matric" -> Color(0xFF2E7D32)
    "post_matric" -> Color(0xFF1B5E3B)
    "top_class" -> Color(0xFF1565C0)
    "nfst" -> Color(0xFF6A1B9A)
    "nos" -> Color(0xFFE65100)
    else -> DarkGreen
}

@Composable
fun SchemeListCard(scheme: ScholarshipScheme, onClick: () -> Unit) {
    AdivanCard(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(schemeIconColor(scheme.id), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Default.School, contentDescription = null, tint = Color.White)
            }
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(scheme.name, fontWeight = FontWeight.SemiBold)
                Text(scheme.shortDescription, fontSize = 13.sp, color = TextSecondary)
                Text("View Details", fontSize = 12.sp, color = DarkGreen, fontWeight = FontWeight.Medium)
            }
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = DarkGreen)
        }
    }
}
