package com.example.myapplication.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.TodoApplication
import com.example.myapplication.ui.addedit.AddEditTaskViewModel
import com.example.myapplication.ui.tasklist.TaskListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TaskListViewModel(todoApplication().taskRepository)
        }
        initializer {
            AddEditTaskViewModel(
                savedStateHandle = createSavedStateHandle(),
                repository = todoApplication().taskRepository
            )
        }
        initializer {
            ThemeViewModel(todoApplication().userPreferencesRepository)
        }
    }
}

fun CreationExtras.todoApplication(): TodoApplication =
    (this[APPLICATION_KEY] as TodoApplication)
