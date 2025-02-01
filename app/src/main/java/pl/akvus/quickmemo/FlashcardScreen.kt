package pl.akvus.quickmemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun FlashcardScreen(
    viewModel: WordViewModel,
) {
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
        modifier = Modifier.fillMaxSize(),
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
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Color.LightGray),
                verticalArrangement = Arrangement.Center,
            ) {

                Text(
                    text = unlearnedWords[currentWordIndex].wordA,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(CenterHorizontally)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .background(Color.Gray),
                verticalArrangement = Arrangement.Center,
            ) {
                if (showTranslation)
                    Text(
                        text = unlearnedWords[currentWordIndex].wordB,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(CenterHorizontally)
                    )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                TextButton(onClick = {
                    showTranslation = !showTranslation
                }) {
                    Text("Show/Hide Translation")
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = {
                    currentWordIndex = (currentWordIndex + 1) % unlearnedWords.size
                    showTranslation = false
                }) {
                    Text("Next Word")
                }
            }

        }
    }
}