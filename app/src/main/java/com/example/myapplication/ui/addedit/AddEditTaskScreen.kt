package com.example.myapplication.ui.addedit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box

// Stub for now — real add/edit form (title field, save button, load-by-id
// for edit mode) lands in build steps 9-10.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(onDone: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Task") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Add/edit form coming next.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
