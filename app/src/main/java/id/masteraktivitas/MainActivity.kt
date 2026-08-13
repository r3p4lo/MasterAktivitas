package id.masteraktivitas

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.masteraktivitas.ui.components.QuickAddTaskDialog
import id.masteraktivitas.ui.screens.CalendarScreen
import id.masteraktivitas.ui.screens.DashboardScreen
import id.masteraktivitas.ui.screens.FinanceScreen
import id.masteraktivitas.ui.screens.MoreScreen
import id.masteraktivitas.ui.screens.ProjectsScreen
import id.masteraktivitas.ui.screens.TasksScreen
import id.masteraktivitas.ui.theme.MasterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        setContent {
            MasterAppRoot()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }
}

@Composable
fun MasterAppRoot() {
    MasterTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        var showQuickAdd by rememberSaveable { mutableStateOf(false) }

        val items = listOf(
            "home" to "HOME",
            "projects" to "PROJECT",
            "tasks" to "TASK",
            "finance" to "FINANCE",
            "more" to "MORE"
        )

        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.first,
                            onClick = {
                                if (currentRoute != item.first) {
                                    navController.navigate(item.first) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            label = { Text(item.second) },
                            icon = {
                                when (item.first) {
                                    "home" -> Icon(Icons.Filled.Home, contentDescription = "Home")
                                    "projects" -> Icon(Icons.Filled.Work, contentDescription = "Project")
                                    "tasks" -> Icon(Icons.Filled.List, contentDescription = "Task")
                                    "finance" -> Icon(Icons.Filled.AttachMoney, contentDescription = "Finance")
                                    else -> Icon(Icons.Filled.MoreHoriz, contentDescription = "More")
                                }
                            }
                        )
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { showQuickAdd = true }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(padding)
            ) {
                composable("home") { DashboardScreen() }
                composable("projects") { ProjectsScreen() }
                composable("tasks") { TasksScreen() }
                composable("finance") { FinanceScreen() }
                composable("more") { MoreScreen(navController) }
                composable("calendar") { CalendarScreen() }
            }

            if (showQuickAdd) {
                QuickAddTaskDialog(onDismiss = { showQuickAdd = false })
            }
        }
    }
} 