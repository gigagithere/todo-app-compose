package com.example.myapplication.ui.addedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Task
import com.example.myapplication.data.TaskRepository
import kotlinx.coroutines.launch

class AddEditTaskViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: TaskRepository
) : ViewModel() {
    private val taskId: Long? = savedStateHandle.get<Long>("taskId")?.takeIf { it != -1L }
    val isEditMode: Boolean = taskId != null

    private var loadedTask: Task? = null

    var title by mutableStateOf("")
        private set

    init {
        taskId?.let { id ->
            viewModelScope.launch {
                repository.getTask(id)?.let { task ->
                    loadedTask = task
                    title = task.title
                }
            }
        }
    }

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun saveTask(onDone: () -> Unit) {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isBlank()) return
        viewModelScope.launch {
            val existing = loadedTask
            if (existing != null) {
                repository.updateTask(existing.copy(title = trimmedTitle))
            } else {
                repository.insertTask(Task(title = trimmedTitle))
            }
            onDone()
        }
    }
}
