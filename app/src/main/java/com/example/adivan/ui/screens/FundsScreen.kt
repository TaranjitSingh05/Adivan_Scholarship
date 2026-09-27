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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivan.data.DemoData
import com.example.adivan.ui.components.AdivanCard
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen
import com.example.adivan.ui.theme.SuccessGreen
import com.example.adivan.ui.theme.TextSecondary

@Composable
fun FundsScreen(
    contentPadding: PaddingValues = PaddingValues(),
    onMenuClick: () -> Unit,
) {
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
            Text("Funds & Disbursement", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        }
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            item {
                AdivanCard(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = DarkGreen,
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Total Approved Amount", color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
                        Text(DemoData.totalApprovedAmount, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 28.sp)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)) {
                    AdivanCard(modifier = Modifier.weight(1f), containerColor = SoftGreen) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Amount Received", fontSize = 12.sp, color = TextSecondary)
                            Text(DemoData.amountReceived, fontWeight = FontWeight.Bold, color = SuccessGreen)
                        }
                    }
                    AdivanCard(modifier = Modifier.weight(1f)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Pending Amount", fontSize = 12.sp, color = TextSecondary)
                            Text(DemoData.pendingAmount, fontWeight = FontWeight.Bold, color = DarkGreen)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text("Transaction History", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(DemoData.transactions) { tx ->
                AdivanCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(tx.title, fontWeight = FontWeight.Medium)
                            Text(tx.date, fontSize = 12.sp, color = TextSecondary)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(tx.amount, fontWeight = FontWeight.Bold, color = SuccessGreen)
                            Text(tx.status, fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Scheme-wise Summary", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                AdivanCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(DemoData.currentScholarshipName, fontWeight = FontWeight.Medium)
                        Text("Received: ${DemoData.schemeReceived}", fontSize = 13.sp, color = TextSecondary)
                        Text("Total: ${DemoData.schemeTotal}", fontSize = 13.sp, color = TextSecondary)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("Bank Account", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                AdivanCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(DemoData.bankAccountMasked, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
                        Text("Active", color = SuccessGreen, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
