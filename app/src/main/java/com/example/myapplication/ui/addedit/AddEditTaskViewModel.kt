package com.example.myapplication.ui.addedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Task
import com.example.myapplication.data.TaskRepository
import kotlinx.coroutines.launch

class AddEditTaskViewModel(private val repository: TaskRepository) : ViewModel() {
    var title by mutableStateOf("")
        private set

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun saveTask(onDone: () -> Unit) {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isBlank()) return
        viewModelScope.launch {
            repository.insertTask(Task(title = trimmedTitle))
            onDone()
        }
    }
}
