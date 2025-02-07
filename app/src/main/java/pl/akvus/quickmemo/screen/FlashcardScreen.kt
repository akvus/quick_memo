package pl.akvus.quickmemo.screen

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun FlashcardScreen(
    wordViewModel: WordViewModel,
    settingsViewModel: SettingsViewModel,
) {
    val unlearnedWords by wordViewModel.unlearnedWords.observeAsState(initial = emptyList())

    val reverseWords = settingsViewModel.reverseWords.value ?: DEFAULT_REVERSE_WORDS
    var currentWordIndex by remember { mutableIntStateOf(0) }
    var showTranslation by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = showTranslation) {
        if (showTranslation) {
            delay(10000)
            showTranslation = false
        }
    }

    val nextWord: () -> Unit = {
        currentWordIndex =
            (Random.nextInt(
                0,
                unlearnedWords.size
            ))
        showTranslation = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (showTranslation) {
                    nextWord()
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
            val currentWord = unlearnedWords[currentWordIndex % unlearnedWords.size]

            val showCounter = settingsViewModel.showCounter.value ?: DEFAULT_SHOW_COUNTER
            val revealTime = settingsViewModel.revealTime.value ?: DEFAULT_REVEAL_TIME
            val color =
                if (currentWord.color != null)
                    Color(color = currentWord.color)
                else Color.Unspecified

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = if (reverseWords) currentWord.wordB else currentWord.wordA,
                    color = color,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(16.dp)
                )
            }

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
                        color = color,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(16.dp)
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
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(16.dp)
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
                    wordViewModel.viewModelScope.launch {
                        wordViewModel.reverseLearnt(currentWord)
                        nextWord()
                    }
                }) {
                    Text("Learned")
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = {
                    nextWord()
                }) {
                    Text("Next")
                }
            }

        }
    }
}
