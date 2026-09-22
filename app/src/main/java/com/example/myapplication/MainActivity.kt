package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

// Placeholder root composable. Replaced by AppNavHost once navigation is wired up (build step 8).
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                TodoAppPlaceholder()
            }
        }
    }
}

@Composable
fun TodoAppPlaceholder() {
    Scaffold { innerPadding ->
        Text(
            text = "Todo app coming together — next up: the data layer.",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoAppPlaceholderPreview() {
    MyApplicationTheme {
        TodoAppPlaceholder()
    }
}