package com.example.adivan.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.adivan.navigation.Routes

@Composable
fun AppScaffold(
    navController: NavHostController,
    currentRoute: String?,
    showBottomBar: Boolean,
    showJagoFab: Boolean = false,
    onJagoClick: (() -> Unit)? = null,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { topBar() },
        bottomBar = {
            if (showBottomBar) {
                AdivanBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Routes.Home) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
        content = { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                content(padding)
                if (showJagoFab && onJagoClick != null) {
                    JagoFab(
                        visible = true,
                        onClick = onJagoClick,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(
                                end = 20.dp,
                                bottom = padding.calculateBottomPadding() + 16.dp,
                            ),
                    )
                }
            }
        },
    )
}
