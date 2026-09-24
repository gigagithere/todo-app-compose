package com.example.myapplication

import com.example.myapplication.data.Task
import com.example.myapplication.data.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

/**
 * In-memory [TaskRepository] for ViewModel tests. Mirrors what Room does for the
 * real one: newest task first, and inserting a task whose id already exists
 * replaces it (OnConflictStrategy.REPLACE).
 */
class FakeTaskRepository(initialTasks: List<Task> = emptyList()) : TaskRepository {
    private val tasks = MutableStateFlow(initialTasks)
    private var nextId = (initialTasks.maxOfOrNull { it.id } ?: 0L) + 1

    val currentTasks: List<Task> get() = tasks.value

    override fun getAllTasksStream(): Flow<List<Task>> =
        tasks.map { list -> list.sortedByDescending { it.createdAt } }

    override suspend fun getTask(id: Long): Task? = tasks.value.firstOrNull { it.id == id }

    override suspend fun insertTask(task: Task) {
        val saved = if (task.id == 0L) task.copy(id = nextId++) else task
        tasks.update { list -> list.filterNot { it.id == saved.id } + saved }
    }

    override suspend fun updateTask(task: Task) {
        tasks.update { list -> list.map { if (it.id == task.id) task else it } }
    }

    override suspend fun deleteTask(task: Task) {
        tasks.update { list -> list.filterNot { it.id == task.id } }
    }
}
