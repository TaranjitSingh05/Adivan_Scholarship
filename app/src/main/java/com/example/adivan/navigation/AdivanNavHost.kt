package com.example.adivan.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.adivan.data.DemoData
import com.example.adivan.data.jago.JagoAppContextBuilder
import com.example.adivan.ui.components.AppScaffold
import com.example.adivan.ui.components.NavigationDrawerContent
import com.example.adivan.ui.screens.ApplicationStatusScreen
import com.example.adivan.ui.screens.ApplyConfirmationScreen
import com.example.adivan.ui.screens.DocumentDetailScreen
import com.example.adivan.ui.screens.DocumentsScreen
import com.example.adivan.ui.screens.EligibilityCheckScreen
import com.example.adivan.ui.screens.FundsScreen
import com.example.adivan.ui.screens.HomeScreen
import com.example.adivan.ui.screens.JagoScreen
import com.example.adivan.ui.screens.ProfileScreen
import com.example.adivan.ui.screens.ProfileSectionScreen
import com.example.adivan.ui.screens.ScholarshipSchemesScreen
import com.example.adivan.ui.screens.SchemeDetailsScreen
import com.example.adivan.ui.screens.SplashScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdivanNavHost() {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val openDrawer: () -> Unit = { scope.launch { drawerState.open() } }
    val openJago: () -> Unit = {
        val context = JagoAppContextBuilder.screenContextFromRoute(currentRoute)
        navController.navigate(Routes.jago(context)) {
            launchSingleTop = true
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentRoute != Routes.Splash,
        drawerContent = {
            NavigationDrawerContent(
                onNavigate = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        popUpTo(Routes.Home) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        },
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.Splash,
        ) {
            composable(Routes.Splash) {
                SplashScreen(
                    onFinished = {
                        navController.navigate(Routes.Home) {
                            popUpTo(Routes.Splash) { inclusive = true }
                        }
                    },
                )
            }

            composable(Routes.Home) {
                AppScaffold(
                    navController = navController,
                    currentRoute = Routes.Home,
                    showBottomBar = true,
                    showJagoFab = true,
                    onJagoClick = openJago,
                ) { padding ->
                    HomeScreen(
                        modifierPadding = padding,
                        onMenuClick = openDrawer,
                        onApplicationStatus = { navController.navigate(Routes.ApplicationStatus) },
                        onFunds = { navController.navigate(Routes.Funds) },
                        onDocuments = { navController.navigate(Routes.Documents) },
                        onProfile = { navController.navigate(Routes.Profile) },
                        onScheme = { id -> navController.navigate(Routes.schemeDetails(id)) },
                        onScholarships = { navController.navigate(Routes.Scholarships) },
                        onJago = openJago,
                    )
                }
            }

            composable(Routes.ApplicationStatus) {
                ApplicationStatusScreen(onBack = { navController.popBackStack() })
            }

            composable(Routes.Scholarships) {
                AppScaffold(
                    navController = navController,
                    currentRoute = Routes.Scholarships,
                    showBottomBar = true,
                ) { padding ->
                    ScholarshipSchemesScreen(
                        contentPadding = padding,
                        onMenuClick = openDrawer,
                        onSchemeClick = { id -> navController.navigate(Routes.schemeDetails(id)) },
                    )
                }
            }

            composable(
                route = Routes.SchemeDetails,
                arguments = listOf(navArgument("schemeId") { type = NavType.StringType }),
            ) { entry ->
                val schemeId = entry.arguments?.getString("schemeId") ?: return@composable
                val scheme = DemoData.schemeById(schemeId) ?: return@composable
                SchemeDetailsScreen(
                    scheme = scheme,
                    onBack = { navController.popBackStack() },
                    onCheckEligibility = { navController.navigate(Routes.eligibility(schemeId)) },
                    onApplyNow = { navController.navigate(Routes.applyConfirmation(schemeId)) },
                )
            }

            composable(
                route = Routes.Eligibility,
                arguments = listOf(navArgument("schemeId") { type = NavType.StringType }),
            ) { entry ->
                val schemeId = entry.arguments?.getString("schemeId") ?: return@composable
                val scheme = DemoData.schemeById(schemeId)
                EligibilityCheckScreen(
                    schemeName = scheme?.name ?: "Scholarship",
                    onBack = { navController.popBackStack() },
                    onApplyNow = { navController.navigate(Routes.applyConfirmation(schemeId)) },
                )
            }

            composable(Routes.Documents) {
                AppScaffold(
                    navController = navController,
                    currentRoute = Routes.Documents,
                    showBottomBar = true,
                ) { padding ->
                    DocumentsScreen(
                        contentPadding = padding,
                        onMenuClick = openDrawer,
                        onDocumentClick = { id -> navController.navigate(Routes.documentDetail(id)) },
                    )
                }
            }

            composable(
                route = Routes.DocumentDetail,
                arguments = listOf(navArgument("docId") { type = NavType.StringType }),
            ) { entry ->
                val docId = entry.arguments?.getString("docId") ?: return@composable
                val doc = DemoData.documentById(docId) ?: return@composable
                DocumentDetailScreen(document = doc, onBack = { navController.popBackStack() })
            }

            composable(Routes.Funds) {
                AppScaffold(
                    navController = navController,
                    currentRoute = Routes.Funds,
                    showBottomBar = true,
                ) { padding ->
                    FundsScreen(contentPadding = padding, onMenuClick = openDrawer)
                }
            }

            composable(Routes.Profile) {
                AppScaffold(
                    navController = navController,
                    currentRoute = Routes.Profile,
                    showBottomBar = true,
                ) { padding ->
                    ProfileScreen(
                        contentPadding = padding,
                        onMenuClick = openDrawer,
                        onSectionClick = { section ->
                            navController.navigate(Routes.profileSection(section))
                        },
                        onDocuments = { navController.navigate(Routes.Documents) },
                        onLogout = {
                            navController.navigate(Routes.Home) {
                                popUpTo(Routes.Home) { inclusive = true }
                            }
                        },
                    )
                }
            }

            composable(
                route = Routes.ProfileSection,
                arguments = listOf(navArgument("section") { type = NavType.StringType }),
            ) { entry ->
                val section = entry.arguments?.getString("section") ?: return@composable
                ProfileSectionScreen(section = section, onBack = { navController.popBackStack() })
            }

            composable(
                route = Routes.Jago,
                arguments = listOf(navArgument("screenContext") { type = NavType.StringType }),
            ) { entry ->
                val screenContext = entry.arguments?.getString("screenContext") ?: "home"
                JagoScreen(
                    screenContext = screenContext,
                    onBack = { navController.popBackStack() },
                )
            }

            composable(
                route = Routes.ApplyConfirmation,
                arguments = listOf(navArgument("schemeId") { type = NavType.StringType }),
            ) { entry ->
                val schemeId = entry.arguments?.getString("schemeId") ?: return@composable
                val scheme = DemoData.schemeById(schemeId)
                ApplyConfirmationScreen(
                    schemeName = scheme?.name ?: "Scholarship",
                    onBack = { navController.popBackStack() },
                    onDone = {
                        navController.navigate(Routes.Home) {
                            popUpTo(Routes.Home) { inclusive = false }
                        }
                    },
                )
            }
        }
    }
}
