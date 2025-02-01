package pl.akvus.quickmemo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import pl.akvus.quickmemo.db.WordEntity

@Composable
fun AddWordDialog(
    word: WordEntity?,
    onDismiss: () -> Unit,
    onWordAdded: (wordA: String, wordB: String) -> Unit
) {
    var wordA by remember { mutableStateOf(word?.wordA ?: "") }
    var wordB by remember { mutableStateOf(word?.wordB ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Save a word") },
        text = {
            Column {
                TextField(
                    value = wordA,
                    onValueChange = { wordA = it },
                    label = { Text("Word A") }
                )
                TextField(
                    value = wordB,
                    onValueChange = { wordB = it },
                    label = { Text("Word B") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onWordAdded(wordA, wordB)
                onDismiss()
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}