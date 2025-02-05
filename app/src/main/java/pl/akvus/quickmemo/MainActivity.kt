package pl.akvus.quickmemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.akvus.quickmemo.screen.FlashcardScreen
import pl.akvus.quickmemo.screen.SettingsScreen
import pl.akvus.quickmemo.screen.SettingsViewModel
import pl.akvus.quickmemo.screen.StatsScreen
import pl.akvus.quickmemo.screen.WordListScreen
import pl.akvus.quickmemo.screen.WordViewModel
import pl.akvus.quickmemo.ui.theme.QuickMemoTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            QuickMemoTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("QuickMemot") },
                            actions = {
                                IconButton(onClick = {
                                    navController.navigate("flashcard")
                                }) {
                                    Icon(
                                        Icons.Filled.PlayArrow,
                                        contentDescription = "Play"
                                    )
                                }
                                IconButton(onClick = {
                                    navController.navigate("word_list")
                                }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.List,
                                        contentDescription = "Word List"
                                    )
                                }
                                IconButton(onClick = {
                                    navController.navigate("stats")
                                }) {
                                    Icon(
                                        Icons.Filled.Done,
                                        contentDescription = "Stats"
                                    )
                                }
                                IconButton(onClick = {
                                    navController.navigate("settings")
                                }) {
                                    Icon(
                                        Icons.Filled.Settings,
                                        contentDescription = "Settings"
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "flashcard",
                        modifier = Modifier
                            .padding(innerPadding),
                    ) {
                        composable("flashcard") {
                            FlashcardScreen(
                                viewModel(factory = WordViewModel.Factory),
                                viewModel(
                                    factory = SettingsViewModel.Factory
                                )
                            )
                        }
                        composable("settings") {
                            SettingsScreen(viewModel(factory = SettingsViewModel.Factory))
                        }
                        composable("word_list") {
                            WordListScreen(viewModel(factory = WordViewModel.Factory)) {
                                navController.navigate("flashcard")
                            }
                        }
                        composable("stats") {
                            StatsScreen(viewModel(factory = WordViewModel.Factory))
                        }
                    }
                }
            }
        }
    }
}
