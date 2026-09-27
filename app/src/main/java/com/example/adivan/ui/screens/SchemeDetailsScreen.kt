package com.example.adivan.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.data.ScholarshipScheme
import com.example.adivan.ui.components.AdivanTopBar
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen
import com.example.adivan.ui.theme.TextSecondary

@Composable
fun SchemeDetailsScreen(
    scheme: ScholarshipScheme,
    onBack: () -> Unit,
    onCheckEligibility: () -> Unit,
    onApplyNow: () -> Unit,
) {
    var tab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Overview", "Eligibility", "Documents", "Benefits")

    Column(modifier = Modifier.fillMaxSize()) {
        AdivanTopBar(title = scheme.name, onBack = onBack)
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            Text(scheme.shortDescription, color = TextSecondary, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))
            TabRow(selectedTabIndex = tab, containerColor = SoftGreen) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = tab == index,
                        onClick = { tab = index },
                        text = { Text(title, fontSize = 11.sp) },
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            when (tab) {
                0 -> OverviewTab(scheme)
                1 -> EligibilityTab(scheme)
                2 -> DocumentsTab(scheme)
                3 -> BenefitsTab(scheme)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            OutlinedButton(
                onClick = onCheckEligibility,
                modifier = Modifier.weight(1f),
            ) {
                Text("Check Eligibility")
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Button(
                onClick = onApplyNow,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            ) {
                Text("Apply Now")
            }
        }
    }
}

@Composable
private fun OverviewTab(scheme: ScholarshipScheme) {
    SectionTitle("About")
    Text(scheme.about, fontSize = 14.sp, color = TextSecondary)
}

@Composable
private fun EligibilityTab(scheme: ScholarshipScheme) {
    DetailRow("Level", scheme.level)
    DetailRow("Beneficiaries", scheme.beneficiaries)
}

@Composable
private fun DocumentsTab(scheme: ScholarshipScheme) {
    SectionTitle("Required Documents")
    scheme.documents.forEach { doc ->
        Text("• $doc", fontSize = 14.sp, modifier = Modifier.padding(vertical = 4.dp))
    }
}

@Composable
private fun BenefitsTab(scheme: ScholarshipScheme) {
    SectionTitle("Financial Assistance")
    Text(scheme.financialAssistance, fontSize = 14.sp, color = TextSecondary)
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, fontWeight = FontWeight.SemiBold, color = DarkGreen, modifier = Modifier.padding(bottom = 8.dp))
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(label, fontWeight = FontWeight.Medium, fontSize = 13.sp)
        Text(value, fontSize = 14.sp, color = TextSecondary)
    }
}
