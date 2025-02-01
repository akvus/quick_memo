import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun FlashcardScreen(
    viewModel: WordViewModel,
    navigateToWordList: () -> Unit
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
        Text(
            text = if (showTranslation) unlearnedWords[currentWordIndex].wordA + " - " + unlearnedWords[currentWordIndex].wordB else unlearnedWords[currentWordIndex].wordA,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            showTranslation = !showTranslation
        }) {
            Text("Show/Hide Translation")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            currentWordIndex = (currentWordIndex + 1) % unlearnedWords.size
            showTranslation = false
        }) {
            Text("Next Word")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = navigateToWordList) {
            Text("Word List")
        }
    }
}