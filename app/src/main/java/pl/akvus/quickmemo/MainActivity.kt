package pl.akvus.quickmemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
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
                            title = { Text("QuickMemo") },
                            actions = {
                                IconButton(onClick = { /* navigate to word list */ }) {
                                    Icon(Icons.Default.List, contentDescription = "Word List")
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
                            FlashcardScreen(viewModel(factory = WordViewModel.Factory)) {
                                navController.navigate("word_list")
                            }
                        }
                        composable("word_list") {
                            WordListScreen(viewModel(factory = WordViewModel.Factory)) {
                                navController.navigate("flashcard")
                            }
                        }
                    }
                }
            }
        }
    }
}