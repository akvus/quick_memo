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

@Composable
fun AddWordDialog(
    onDismiss: () -> Unit,
    onWordAdded: (wordA: String, wordB: String) -> Unit
) {
    var wordA by remember { mutableStateOf("") }
    var wordB by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Word") },
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
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}