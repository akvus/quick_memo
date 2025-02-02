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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val revealTime by viewModel.revealTime.observeAsState(
        initial = viewModel.revealTime.value ?: 5
    )
    val showCounter by viewModel.showCounter.observeAsState(
        initial = viewModel.showCounter.value ?: true
    )
    val reverseWords by viewModel.reverseWords.observeAsState(
        initial = viewModel.reverseWords.value ?: false
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
                    viewModel.setRevealTime(it.toIntOrNull() ?: 5)
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
                    viewModel.setShowCounter(isChecked)
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
                    viewModel.setReverseWords(isChecked)
                }
            )
        }
    }
}

