package pl.akvus.quickmemo.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import pl.akvus.quickmemo.db.WordEntity

@Composable
fun DeleteWordWidget(word: WordEntity, wordViewModel: WordViewModel) {
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
                    wordViewModel.deleteWord(word)
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
}