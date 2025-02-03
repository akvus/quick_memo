package pl.akvus.quickmemo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import pl.akvus.quickmemo.db.WordEntity

@Composable
fun SaveWordDialog(
    word: WordEntity?,
    onDismiss: () -> Unit,
    onWordAdded: (wordA: String, wordB: String, color: Int) -> Unit
) {
    val controller = rememberColorPickerController()
    var wordA by remember { mutableStateOf(word?.wordA ?: "") }
    var wordB by remember { mutableStateOf(word?.wordB ?: "") }
    var color by remember { mutableStateOf(word?.color ?: 0) }

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
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(10.dp),
                    controller = controller,
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        color = colorEnvelope.color.toArgb()
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onWordAdded(wordA, wordB, color)
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
