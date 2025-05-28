package com.example.cocktails

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun TimerComponent() {
    val presetOptions = listOf(60, 120, 300)
    var selectedPreset by rememberSaveable { mutableStateOf(presetOptions[1]) }
    var timeLeft by rememberSaveable { mutableStateOf(selectedPreset) }
    var isRunning by rememberSaveable { mutableStateOf(false) }
    var startTimeMillis by rememberSaveable { mutableStateOf<Long?>(null) }
    var currentTimeMillis by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            if (startTimeMillis == null) {
                startTimeMillis = System.currentTimeMillis() - ((selectedPreset - timeLeft) * 1000)
            }

            while (isRunning && timeLeft > 0) {
                delay(1000)
                currentTimeMillis = System.currentTimeMillis()
                val elapsedSeconds = ((currentTimeMillis - (startTimeMillis ?: currentTimeMillis)) / 1000).toInt()
                timeLeft = (selectedPreset - elapsedSeconds).coerceAtLeast(0)

                if (timeLeft <= 0) {
                    isRunning = false
                    startTimeMillis = null
                }
            }
        }
    }

    LaunchedEffect(selectedPreset) {
        if (!isRunning) {
            timeLeft = selectedPreset
            startTimeMillis = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = formatTime(timeLeft),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 50.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    if (timeLeft >= 10) timeLeft -= 10 else timeLeft = 0
                    if (isRunning) {
                        startTimeMillis = System.currentTimeMillis() - ((selectedPreset - timeLeft) * 1000)
                    }
                },
                enabled = !isRunning && timeLeft > 0,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("-10 sek")
            }

            Button(
                onClick = {
                    timeLeft += 10
                    if (isRunning) {
                        startTimeMillis = System.currentTimeMillis() - ((selectedPreset - timeLeft) * 1000)
                    }
                },
                enabled = !isRunning,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text("+10 sek")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    isRunning = true
                    startTimeMillis = System.currentTimeMillis() - ((selectedPreset - timeLeft) * 1000)
                },
                enabled = !isRunning && timeLeft > 0
            ) {
                Text("Start")
            }

            Button(
                onClick = {
                    isRunning = false
                },
                enabled = isRunning
            ) {
                Text("Stop")
            }

            Button(
                onClick = {
                    isRunning = false
                    timeLeft = selectedPreset
                    startTimeMillis = null
                }
            ) {
                Text("Reset")
            }
        }
    }
}

@SuppressLint("DefaultLocale")
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}
