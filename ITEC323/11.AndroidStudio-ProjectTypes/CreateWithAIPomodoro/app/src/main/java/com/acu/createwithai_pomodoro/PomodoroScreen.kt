package com.acu.createwithai_pomodoro

import android.media.RingtoneManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.acu.createwithai_pomodoro.ui.theme.CreateWithAIPomodoroTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PomodoroScreen(viewModel: TimerViewModel = viewModel()) {
    val timeLeft by viewModel.timeLeft.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val currentMode by viewModel.currentMode.collectAsState()
    
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onTimerFinished.collectLatest {
            // Play notification sound
            try {
                val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val r = RingtoneManager.getRingtone(context, notification)
                r.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            // Show Snackbar
            snackbarHostState.showSnackbar(
                message = "Time's up! ${currentMode.label} session finished."
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Pomodoro App",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            // Segmented Button Row for Mode Selection
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                PomodoroMode.values().forEachIndexed { index, mode ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = PomodoroMode.values().size),
                        onClick = { viewModel.setMode(mode) },
                        selected = currentMode == mode,
                        label = { Text(mode.label) }
                    )
                }
            }

            // Circular Progress and Timer Text
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(300.dp)
            ) {
                val totalSeconds = currentMode.durationMinutes * 60f
                val progress by animateFloatAsState(
                    targetValue = if (totalSeconds > 0) timeLeft / totalSeconds else 0f,
                    label = "TimerProgress"
                )

                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 12.dp,
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                    strokeCap = StrokeCap.Round,
                )

                Text(
                    text = viewModel.formatTime(timeLeft),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            // Control Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.resetTimer() },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

                Spacer(modifier = Modifier.size(32.dp))

                LargeFloatingActionButton(
                    onClick = { viewModel.toggleTimer() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isRunning) "Pause" else "Play",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PomodoroScreenPreview() {
    CreateWithAIPomodoroTheme {
        PomodoroScreen()
    }
}
