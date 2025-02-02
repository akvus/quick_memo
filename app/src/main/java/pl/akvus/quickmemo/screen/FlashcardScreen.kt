package pl.akvus.quickmemo.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun FlashcardScreen(
    viewModel: WordViewModel,
) {
    // TODO move out of here?
    val sharedPreferences =
        LocalContext.current.getSharedPreferences("settings", Context.MODE_PRIVATE)
    val reverseWords = sharedPreferences.getBoolean("reverse_words", false)

    val unlearnedWords by viewModel.unlearnedWords.observeAsState(initial = emptyList())

    var currentWordIndex by remember { mutableStateOf(0) }
    var showTranslation by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = showTranslation) {
        if (showTranslation) {
            delay(10000)
            showTranslation = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (showTranslation) {
                    currentWordIndex =
                        (Random.nextInt(
                            0,
                            unlearnedWords.size - 1
                        ))
                    showTranslation = false
                } else {
                    showTranslation = true
                }
            },
        verticalArrangement = Arrangement.Center,

        ) {

        if (unlearnedWords.isEmpty()) {
            Text(
                text = "No words to display",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(CenterHorizontally)
            )
        } else {
            val currentWord = unlearnedWords[currentWordIndex]

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                verticalArrangement = Arrangement.Center,
            ) {

                Text(
                    text = if (reverseWords) currentWord.wordB else currentWord.wordA,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(CenterHorizontally)
                )
            }

            val showCounter = sharedPreferences.getBoolean("show_counter", true)
            val revealTime = sharedPreferences.getInt("reveal_time", 5)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                verticalArrangement = Arrangement.Center,
            ) {
                if (showTranslation || !showCounter) {
                    Text(
                        text = if (reverseWords) currentWord.wordA else currentWord.wordB,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(CenterHorizontally)
                    )
                } else {
                    var counter by remember { mutableIntStateOf(revealTime) }

                    LaunchedEffect(key1 = counter) {
                        if (counter > 0) {
                            delay(1000)
                            counter--
                        } else {
                            showTranslation = true
                        }
                    }

                    Text(
                        text = "$counter...",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(CenterHorizontally)
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(onClick = {
                    showTranslation = !showTranslation
                }) {
                    Text(if (showTranslation) "Hide" else "Reveal")
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = {
                    currentWordIndex =
                        (Random.nextInt(
                            0,
                            unlearnedWords.size - 1
                        ))
                    showTranslation = false
                }) {
                    Text("Next Word")
                }
            }

        }
    }
}