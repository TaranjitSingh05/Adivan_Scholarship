package com.example.adivan.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.data.DemoData
import com.example.adivan.data.StepState
import com.example.adivan.ui.components.AdivanCard
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen
import com.example.adivan.ui.theme.SuccessGreen
import com.example.adivan.ui.theme.TextPrimary
import com.example.adivan.ui.theme.TextSecondary

private data class HomeUpdate(val title: String, val date: String, val onClick: () -> Unit)

@Composable
fun HomeScreen(
    modifierPadding: PaddingValues = PaddingValues(),
    onMenuClick: () -> Unit,
    onApplicationStatus: () -> Unit,
    onFunds: () -> Unit,
    onDocuments: () -> Unit,
    onProfile: () -> Unit,
    onScheme: (String) -> Unit,
    onScholarships: () -> Unit,
    onJago: () -> Unit,
) {
    var showNotifications by remember { mutableStateOf(false) }
    val scroll = rememberScrollState()
    val updates = listOf(
        HomeUpdate(
            "Post-Matric Scholarship application deadline extended till 30 Sep 2026",
            "25 Aug 2026",
            onApplicationStatus,
        ),
        HomeUpdate(
            "Document verification process is now online.",
            "20 Aug 2026",
            onDocuments,
        ),
    )

    if (showNotifications) {
        AlertDialog(
            onDismissRequest = { showNotifications = false },
            title = { Text("Notifications", fontWeight = FontWeight.Bold, color = DarkGreen) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    updates.forEach { item ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showNotifications = false
                                    item.onClick()
                                },
                        ) {
                            Text(item.title, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                            Text(item.date, fontSize = 12.sp, color = TextSecondary)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showNotifications = false }) {
                    Text("Close", color = DarkGreen)
                }
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8F6))
            .padding(modifierPadding)
            .verticalScroll(scroll)
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        HomeHeader(
            onLogoClick = onMenuClick,
            onNotifications = { showNotifications = true },
            onProfile = onProfile,
        )
        Spacer(modifier = Modifier.height(14.dp))
        HeroBanner(onKnowMore = onScholarships)
        Spacer(modifier = Modifier.height(16.dp))
        CurrentScholarshipCard(onClick = onApplicationStatus)
        Spacer(modifier = Modifier.height(14.dp))
        SummaryRow(onFunds = onFunds)
        Spacer(modifier = Modifier.height(14.dp))
        ActionRequiredCard(onResolve = onDocuments)
        Spacer(modifier = Modifier.height(18.dp))
        SectionTitle("Explore Other Scholarships", "View All", onScholarships)
        Spacer(modifier = Modifier.height(10.dp))
        ExploreRow(onScheme = onScheme)
        Spacer(modifier = Modifier.height(16.dp))
        JagoHomeCard(onClick = onJago)
        Spacer(modifier = Modifier.height(18.dp))
        SectionTitle("Latest Updates", "View All", { showNotifications = true })
        Spacer(modifier = Modifier.height(8.dp))
        updates.forEachIndexed { index, item ->
            UpdateRow(
                icon = if (index == 0) Icons.Default.Campaign else Icons.Default.Description,
                title = item.title,
                date = item.date,
                onClick = item.onClick,
            )
        }
        Spacer(modifier = Modifier.height(88.dp))
    }
}

@Composable
private fun HomeHeader(
    onLogoClick: () -> Unit,
    onNotifications: () -> Unit,
    onProfile: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onLogoClick),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LeafMark()
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("Adivan", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = DarkGreen)
                Text("Tribal Scholarships", fontSize = 12.sp, color = TextSecondary)
            }
        }
        Box {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(onClick = onNotifications),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = DarkGreen)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE53935)),
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(SoftGreen)
                .clickable(onClick = onProfile),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Default.Person, contentDescription = "Profile", tint = DarkGreen)
        }
    }
}

@Composable
private fun LeafMark() {
    Canvas(modifier = Modifier.size(28.dp)) {
        val leaf = Path().apply {
            moveTo(size.width * 0.5f, size.height * 0.08f)
            quadraticBezierTo(size.width * 0.95f, size.height * 0.35f, size.width * 0.55f, size.height * 0.92f)
            quadraticBezierTo(size.width * 0.15f, size.height * 0.55f, size.width * 0.5f, size.height * 0.08f)
        }
        drawPath(leaf, DarkGreen)
        drawLine(
            color = Color.White,
            start = Offset(size.width * 0.5f, size.height * 0.22f),
            end = Offset(size.width * 0.52f, size.height * 0.78f),
            strokeWidth = 2f,
        )
    }
}

@Composable
private fun HeroBanner(onKnowMore: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(168.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Color(0xFFE7F6EA)),
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color(0xFFFFE082),
                radius = size.width * 0.11f,
                center = Offset(size.width * 0.72f, size.height * 0.38f),
            )
            val hill = Path().apply {
                moveTo(size.width * 0.42f, size.height)
                quadraticBezierTo(size.width * 0.62f, size.height * 0.42f, size.width * 0.95f, size.height * 0.62f)
                lineTo(size.width, size.height)
                close()
            }
            drawPath(hill, Color(0xFFC8E6C9))
            val near = Path().apply {
                moveTo(size.width * 0.55f, size.height)
                quadraticBezierTo(size.width * 0.78f, size.height * 0.55f, size.width, size.height * 0.72f)
                lineTo(size.width, size.height)
                close()
            }
            drawPath(near, Color(0xFF81C784))
            drawRoundRect(
                color = Color(0xFF8D6E63),
                topLeft = Offset(size.width * 0.78f, size.height * 0.58f),
                size = Size(size.width * 0.08f, size.height * 0.22f),
                cornerRadius = CornerRadius(8f, 8f),
            )
        }
        Column(modifier = Modifier.padding(18.dp).fillMaxWidth(0.62f)) {
            Text(
                "Your Future\nOur Support",
                color = DarkGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 26.sp,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "Quality Education\nStronger Tribal Communities",
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Know More →",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkGreen)
                    .clickable(onClick = onKnowMore)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            )
        }
    }
}

@Composable
private fun CurrentScholarshipCard(onClick: () -> Unit) {
    val steps = listOf(
        Triple("Submitted", "12 Aug", StepState.Completed),
        Triple("Documents\nVerified", "18 Aug", StepState.Completed),
        Triple("Institution\nVerification", "In Progress", StepState.Current),
        Triple("Department", "Pending", StepState.Pending),
        Triple("Approved", "Pending", StepState.Pending),
    )
    AdivanCard(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SoftGreen),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Default.School, contentDescription = null, tint = DarkGreen, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Current Scholarship", fontSize = 12.sp, color = TextSecondary)
                    Text(DemoData.currentScholarshipName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                    Text(
                        "Application No. ${DemoData.currentApplicationNo}",
                        fontSize = 12.sp,
                        color = TextSecondary,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    DemoData.currentStatusLabel,
                    color = DarkGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(SoftGreen)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                )
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = DarkGreen,
                    modifier = Modifier.padding(start = 4.dp).size(16.dp).align(Alignment.CenterVertically),
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                steps.forEach { (label, caption, state) ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        StepMark(state)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            label,
                            fontSize = 9.sp,
                            lineHeight = 11.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = if (state == StepState.Current) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (state == StepState.Pending) TextSecondary else DarkGreen,
                        )
                        Text(caption, fontSize = 8.sp, color = TextSecondary, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
private fun StepMark(state: StepState) {
    val color = when (state) {
        StepState.Completed -> SuccessGreen
        StepState.Current -> DarkGreen
        StepState.Pending -> Color(0xFFE0E0E0)
    }
    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            StepState.Completed -> Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(12.dp))
            StepState.Current -> Box(
                Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color.White),
            )
            StepState.Pending -> {}
        }
    }
}

@Composable
private fun SummaryRow(onFunds: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        StatCard("2", "Total\nApplications", Icons.Outlined.Description, Color(0xFFE3F2FD), Color(0xFF1565C0), Modifier.weight(1f))
        StatCard("1", "Approved", Icons.Outlined.TaskAlt, SoftGreen, SuccessGreen, Modifier.weight(1f))
        StatCard("1", "In Progress", Icons.Outlined.Schedule, Color(0xFFFFF8E1), Color(0xFFF9A825), Modifier.weight(1f))
        StatCard(
            "₹12,500",
            "Funds\nReceived",
            Icons.Outlined.AccountBalanceWallet,
            Color(0xFFF3E5F5),
            Color(0xFF8E24AA),
            Modifier.weight(1f),
            onClick = onFunds,
        )
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    modifier: Modifier,
    onClick: (() -> Unit)? = null,
) {
    AdivanCard(modifier = modifier, onClick = onClick) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary, textAlign = TextAlign.Center)
            Text(label, fontSize = 9.sp, lineHeight = 11.sp, color = TextSecondary, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun ActionRequiredCard(onResolve: () -> Unit) {
    AdivanCard(modifier = Modifier.fillMaxWidth(), containerColor = Color(0xFFFFF1F0)) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Description, contentDescription = null, tint = Color(0xFFE53935), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Action Required", color = Color(0xFFE53935), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Income Certificate needs verification", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Text(
                    "Please upload a valid income certificate to continue processing your application.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Resolve Now →",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFFE53935))
                    .clickable(onClick = onResolve)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String, action: String, onAction: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
        Text(
            "$action →",
            color = DarkGreen,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable(onClick = onAction),
        )
    }
}

@Composable
private fun ExploreRow(onScheme: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SchemeTile("Pre-Matric", "Class 1–10", "pre_matric", Icons.Default.Description, Color(0xFFFFEBEE), Color(0xFFE53935), Modifier.weight(1f), onScheme)
        SchemeTile("Top Class", "Professional\nCourses", "top_class", Icons.Default.School, Color(0xFFE3F2FD), Color(0xFF1565C0), Modifier.weight(1f), onScheme)
        SchemeTile("NFST", "M.Phil / PhD", "nfst", Icons.Default.School, Color(0xFFFFF3E0), Color(0xFFEF6C00), Modifier.weight(1f), onScheme)
        SchemeTile("NOS", "Study Abroad", "nos", Icons.Default.Public, Color(0xFFF3E5F5), Color(0xFF8E24AA), Modifier.weight(1f), onScheme)
    }
}

@Composable
private fun SchemeTile(
    title: String,
    subtitle: String,
    schemeId: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    modifier: Modifier,
    onScheme: (String) -> Unit,
) {
    AdivanCard(modifier = modifier, onClick = { onScheme(schemeId) }) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, textAlign = TextAlign.Center, color = TextPrimary)
            Text(subtitle, fontSize = 9.sp, lineHeight = 11.sp, textAlign = TextAlign.Center, color = TextSecondary)
        }
    }
}

@Composable
private fun JagoHomeCard(onClick: () -> Unit) {
    AdivanCard(modifier = Modifier.fillMaxWidth(), onClick = onClick, containerColor = Color(0xFFE8F5E9)) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Default.SmartToy, contentDescription = null, tint = DarkGreen, modifier = Modifier.size(32.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Ask JAGO", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DarkGreen)
                Text("Your Adivan Scholarship Assistant", fontSize = 12.sp, color = TextSecondary)
                Text(
                    "Get instant answers about eligibility, documents, application status and more.",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 14.sp,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "Chat Now →",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(DarkGreen)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            )
        }
    }
}

@Composable
private fun UpdateRow(
    icon: ImageVector,
    title: String,
    date: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(SoftGreen),
            contentAlignment = Alignment.Center,
        ) {
            Icon(icon, contentDescription = null, tint = DarkGreen, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
            Text(date, fontSize = 11.sp, color = TextSecondary)
        }
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(16.dp))
    }
}
