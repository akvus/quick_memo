package pl.akvus.quickmemo.screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.akvus.quickmemo.AddWordDialog

@Composable
fun WordListScreen(
    viewModel: WordViewModel,
    navigateToFlashcard: () -> Unit
) {
    val allWords by viewModel.allWords.observeAsState(initial = emptyList())

    var showAddDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredWords = allWords.filter {
        it.wordA.contains(searchQuery, ignoreCase = true) ||
                it.wordB.contains(searchQuery, ignoreCase = true)
    }

    Column {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = { Text("Search...") }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(filteredWords.size) { index ->
                val word = filteredWords[index]
                var showUpdateDialog by remember { mutableStateOf(false) }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Checkbox(
                        checked = word.isLearned,
                        onCheckedChange = { viewModel.updateWord(word.copy(isLearned = !word.isLearned)) }
                    )
                    Text(
                        text = word.wordA + " - " + word.wordB,
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        showUpdateDialog = true
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Update")
                    }

                    var showDeleteDialog by remember { mutableStateOf(false) }

                    IconButton(onClick = {
                        showDeleteDialog = true
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }

                    if (showDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            title = { Text("Confirm Delete") },
                            text = { Text("Are you sure you want to delete this word?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    viewModel.deleteWord(word)
                                    showDeleteDialog = false
                                }) {
                                    Text("Delete")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteDialog = false }) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }

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


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(onClick = navigateToFlashcard) {
                Text("Start Learning")
            }

            TextButton(onClick = { showAddDialog = true }) {
                Text("Add Word")
            }

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
