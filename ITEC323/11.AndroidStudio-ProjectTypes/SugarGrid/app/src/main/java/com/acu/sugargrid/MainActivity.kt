package com.acu.sugargrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.acu.sugargrid.ui.SugarGridApp
import com.acu.sugargrid.ui.theme.SugarGridTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SugarGridTheme {
                SugarGridApp()
            }
        }
    }
}
