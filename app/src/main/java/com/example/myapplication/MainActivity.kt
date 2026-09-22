package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myapplication.ui.tasklist.TaskListScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

// Calls TaskListScreen directly for now. Replaced by AppNavHost once
// navigation is wired up (build step 8), which will also add the
// add/edit destination this screen navigates to.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                TaskListScreen()
            }
        }
    }
}