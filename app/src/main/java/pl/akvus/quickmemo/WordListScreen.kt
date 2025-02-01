package pl.akvus.quickmemo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WordListScreen(
    viewModel: WordViewModel,
    navigateToFlashcard: () -> Unit
) {
    val allWords by viewModel.allWords.observeAsState(initial = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }

    Column {
        LazyColumn {
            items(allWords.size) { index ->
                val word = allWords[index]
                var showUpdateDialog by remember { mutableStateOf(false) }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = word.wordA)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = word.wordB)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        showUpdateDialog = true
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Update")
                    }
                    IconButton(onClick = {
                        viewModel.deleteWord(word)
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                    Checkbox(
                        checked = word.isLearned,
                        onCheckedChange = { viewModel.updateWord(word.copy(isLearned = !word.isLearned)) }
                    )

                    if (showUpdateDialog)
                        AddWordDialog(
                            word,
                            onDismiss = { showUpdateDialog = false },
                            onWordAdded = { wordA, wordB ->
                                viewModel.updateWord(word.copy(wordA = wordA, wordB = wordB))
                                showUpdateDialog = false
                            }
                        )
                }
            }
        }

        Button(onClick = navigateToFlashcard) {
            Text("Start Learning")
        }

        Button(onClick = { showAddDialog = true }) {
            Text("Show Add Word Dialog")
        }

        if (showAddDialog) {
            AddWordDialog(
                null,
                onDismiss = { showAddDialog = false },
                onWordAdded = { wordA, wordB ->
                    viewModel.insertWord(wordA, wordB)
                    showAddDialog = false
                }
            )
        }
    }
}
