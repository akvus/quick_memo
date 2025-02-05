package pl.akvus.quickmemo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StatsScreen(wordViewModel: WordViewModel = viewModel()) {
    val allWords by wordViewModel.allWords.observeAsState(initial = emptyList())

    val learntWordsCount = allWords.count { it.isLearned }
    val unlearntWordsCount = allWords.size - learntWordsCount

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Tile(text = "Total words: ${allWords.size}")
        Tile(text = "Learnt words: $learntWordsCount")
        Tile(text = "Unlearnt words: $unlearntWordsCount")
    }
}

@Composable
fun Tile(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(text = text)
    }
}
