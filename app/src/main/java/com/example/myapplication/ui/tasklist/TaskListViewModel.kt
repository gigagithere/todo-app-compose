package com.example.myapplication.ui.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Task
import com.example.myapplication.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class TaskListUiState(val tasks: List<Task> = emptyList())

class TaskListViewModel(private val repository: TaskRepository) : ViewModel() {
    val uiState: StateFlow<TaskListUiState> =
        repository.getAllTasksStream()
            .map { TaskListUiState(tasks = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = TaskListUiState()
            )
}
