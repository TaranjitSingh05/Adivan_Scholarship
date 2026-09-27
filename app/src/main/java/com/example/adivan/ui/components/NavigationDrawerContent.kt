package com.example.adivan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.data.DemoData
import com.example.adivan.navigation.Routes
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen

private data class DrawerItem(val label: String, val route: String)

private val drawerItems = listOf(
    DrawerItem("Home", Routes.Home),
    DrawerItem("Scholarship Schemes", Routes.Scholarships),
    DrawerItem("Funds & Disbursement", Routes.Funds),
    DrawerItem("My Documents", Routes.Documents),
    DrawerItem("Profile", Routes.Profile),
    DrawerItem("Help & Support", Routes.profileSection("help")),
)

@Composable
fun NavigationDrawerContent(onNavigate: (String) -> Unit) {
    val student = DemoData.student
    ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(SoftGreen)
                .padding(24.dp),
        ) {
            Text(student.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("${student.category} Student", color = DarkGreen)
            Text(student.studentId, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))
        drawerItems.forEach { item ->
            Text(
                text = item.label,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(item.route) }
                    .padding(horizontal = 24.dp, vertical = 14.dp),
                fontWeight = FontWeight.Medium,
            )
            HorizontalDivider()
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Adivan",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 4.dp),
            fontWeight = FontWeight.Bold,
            color = DarkGreen,
            fontSize = 16.sp,
        )
        Text(
            "Version 1.0.0",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp),
            fontSize = 12.sp,
            color = DarkGreen.copy(alpha = 0.7f),
        )
    }
}
