package com.example.adivan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.data.DemoData
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen
import com.example.adivan.ui.theme.TextSecondary
import com.example.adivan.ui.theme.WarningOrange

private val profileSections = listOf(
    "Personal Information",
    "Academic Information",
    "My Documents",
    "Bank Details",
    "Security Settings",
    "Help & Support",
)

@Composable
fun ProfileScreen(
    contentPadding: PaddingValues = PaddingValues(),
    onMenuClick: () -> Unit,
    onSectionClick: (String) -> Unit,
    onDocuments: () -> Unit,
    onLogout: () -> Unit,
) {
    val student = DemoData.student
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = DarkGreen)
            }
            Text("Profile", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SoftGreen)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(DarkGreen),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    student.name.split(" ").map { it.first() }.joinToString(""),
                    color = androidx.compose.ui.graphics.Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(student.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("${student.category} Student", color = DarkGreen)
            Text("${student.course} • ${student.institution}", fontSize = 13.sp, color = TextSecondary)
        }
        profileSections.forEach { section ->
            ProfileMenuRow(
                title = section,
                onClick = {
                    when (section) {
                        "My Documents" -> onDocuments()
                        else -> onSectionClick(sectionKey(section))
                    }
                },
            )
            HorizontalDivider()
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = WarningOrange),
        ) {
            Text("Logout")
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

private fun sectionKey(title: String): String = when (title) {
    "Help & Support" -> "help"
    else -> title.lowercase().replace(" ", "_")
}

@Composable
private fun ProfileMenuRow(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TextSecondary)
    }
}

@Composable
fun ProfileSectionScreen(section: String, onBack: () -> Unit) {
    val student = DemoData.student
    val title = section.replace("_", " ").replaceFirstChar { it.uppercase() }
    val body = when (section) {
        "personal_information" -> """
            Name: ${student.name}
            Category: ${student.category}
            Student ID: ${student.studentId}
            Mobile: +91 98XXX XXXXX
            Email: rahul.kumar@demo.in
        """.trimIndent()
        "academic_information" -> """
            Course: ${student.course}
            Institution: ${student.institution}
            Year: 3rd Year
            Enrollment: ENR/2024/CSE/8821
        """.trimIndent()
        "bank_details" -> """
            Account: ${DemoData.bankAccountMasked}
            IFSC: SBIN000XXXX
            Bank: State Bank of India
            Status: Active for DBT
        """.trimIndent()
        "security_settings" -> """
            MPIN: Enabled
            Biometric login: Off
            Last login: 26 Sep 2026
        """.trimIndent()
        "help" -> """
            Helpline: 1800-XXX-XXXX
            Email: support@adivan.demo
            Hours: Mon–Sat, 9 AM – 6 PM
        """.trimIndent()
        else -> "Demo information for $title."
    }

    Column(modifier = Modifier.fillMaxSize()) {
        com.example.adivan.ui.components.AdivanTopBar(title = title, onBack = onBack)
        Text(
            text = body,
            modifier = Modifier.padding(20.dp),
            lineHeight = 22.sp,
            color = TextSecondary,
        )
    }
}
