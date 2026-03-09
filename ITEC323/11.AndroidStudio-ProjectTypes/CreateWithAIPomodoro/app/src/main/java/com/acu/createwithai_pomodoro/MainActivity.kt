package com.acu.createwithai_pomodoro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.acu.createwithai_pomodoro.ui.theme.CreateWithAIPomodoroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CreateWithAIPomodoroTheme {
                PomodoroScreen()
            }
        }
    }
}
