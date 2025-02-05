package pl.akvus.quickmemo.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    val revealTime by settingsViewModel.revealTime.observeAsState(
        initial = settingsViewModel.revealTime.value ?: DEFAULT_REVEAL_TIME
    )
    val showCounter by settingsViewModel.showCounter.observeAsState(
        initial = settingsViewModel.showCounter.value ?: DEFAULT_SHOW_COUNTER
    )
    val reverseWords by settingsViewModel.reverseWords.observeAsState(
        initial = settingsViewModel.reverseWords.value ?: DEFAULT_REVERSE_WORDS
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Settings", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Reveal Time (seconds):")
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = if (revealTime == 0) "" else revealTime.toString(),
                onValueChange = {
                    settingsViewModel.setRevealTime(it.toIntOrNull() ?: DEFAULT_REVEAL_TIME)
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Show Counter:")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = showCounter,
                onCheckedChange = { isChecked ->
                    settingsViewModel.setShowCounter(isChecked)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Reverse words:")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = reverseWords,
                onCheckedChange = { isChecked ->
                    settingsViewModel.setReverseWords(isChecked)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (false)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Export")
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {
                    settingsViewModel.exportData()
                }) {
                    Text(text = "Share")
                }
            }

        Spacer(modifier = Modifier.height(16.dp))

        if (false)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Inpurt")
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {
                    //' TODO
                }) {
                    Text(text = "From file")
                }
            }
    }
}
