package com.example.myapplication.ui.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Task
import com.example.myapplication.data.TaskRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TaskListUiState(val tasks: List<Task> = emptyList())

sealed interface TaskListEvent {
    data class ShowUndoDeleteSnackbar(val task: Task) : TaskListEvent
}

class TaskListViewModel(private val repository: TaskRepository) : ViewModel() {
    val uiState: StateFlow<TaskListUiState> =
        repository.getAllTasksStream()
            .map { TaskListUiState(tasks = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = TaskListUiState()
            )

    private val _events = Channel<TaskListEvent>()
    val events: Flow<TaskListEvent> = _events.receiveAsFlow()

    fun toggleCompleted(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            _events.send(TaskListEvent.ShowUndoDeleteSnackbar(task))
        }
    }

    fun undoDelete(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }
}
