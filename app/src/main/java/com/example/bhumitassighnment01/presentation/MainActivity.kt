package com.example.bhumitassighnment01.presentation

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import com.example.bhumitassighnment01.presentation.theme.BhumitAssighnment01Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    BhumitAssighnment01Theme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Stopwatch()
        }
    }
}

@Composable
fun Stopwatch() {
    var time by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(10L)
            time += 10L
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatTime(time),
            style = MaterialTheme.typography.display1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = {
                if (!isRunning) {
                    isRunning = true
                }
            }, enabled = !isRunning) {
                Text("Start")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (isRunning) {
                    isRunning = false
                }
            }, enabled = isRunning) {
                Text("Stop")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                isRunning = false
                time = 0L
            }, enabled = !isRunning && time > 0) {
                Text("Reset")
            }
        }
    }
}

fun formatTime(time: Long): String {
    val milliseconds = (time % 1000) / 10
    val seconds = (time / 1000) % 60
    val minutes = (time / (1000 * 60)) % 60
    val hours = (time / (1000 * 60 * 60)) % 24
    return String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds)
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}