package com.example.adivan.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.adivan.navigation.Routes
import com.example.adivan.ui.theme.DarkGreen
import com.example.adivan.ui.theme.SoftGreen
import com.example.adivan.ui.theme.TextSecondary

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(Routes.Home, "Home", Icons.Default.Home),
    BottomNavItem(Routes.Scholarships, "Scholarships", Icons.Default.School),
    BottomNavItem(Routes.Funds, "Funds", Icons.Default.AccountBalance),
    BottomNavItem(Routes.Documents, "Documents", Icons.Default.Description),
    BottomNavItem(Routes.Profile, "Profile", Icons.Default.Person),
)

@Composable
fun AdivanBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
) {
    NavigationBar(containerColor = Color.White) {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = if (selected) DarkGreen else TextSecondary,
                    )
                },
                label = {
                    Text(
                        item.label,
                        fontSize = 10.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DarkGreen,
                    selectedTextColor = DarkGreen,
                    indicatorColor = SoftGreen,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary,
                ),
            )
        }
    }
}
