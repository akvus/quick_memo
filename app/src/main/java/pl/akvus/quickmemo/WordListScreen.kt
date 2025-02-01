import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WordListScreen(
    viewModel: WordViewModel,
    navigateToFlashcard: () -> Unit
) {
    val allWords by viewModel.allWords.observeAsState(initial = emptyList())

    Column {
        LazyColumn {
            items(allWords.size) { index ->
                var word = allWords[index]

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = word.wordA)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = word.wordB)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /* TODO: Implement edit functionality */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { /* TODO: Implement delete functionality */ }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                    Checkbox(
                        checked = word.isLearned,
                        onCheckedChange = { viewModel.updateWord(word.copy(isLearned = it)) }
                    )
                }
            }
        }

        Button(onClick = navigateToFlashcard) {
            Text("Start Learning")
        }
    }
}