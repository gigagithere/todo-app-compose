package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasksStream(): Flow<List<Task>>
    suspend fun getTask(id: Long): Task?
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}

class OfflineTaskRepository(private val taskDao: TaskDao) : TaskRepository {
    override fun getAllTasksStream(): Flow<List<Task>> = taskDao.getAllTasks()
    override suspend fun getTask(id: Long): Task? = taskDao.getTaskById(id)
    override suspend fun insertTask(task: Task) { taskDao.insert(task) }
    override suspend fun updateTask(task: Task) { taskDao.update(task) }
    override suspend fun deleteTask(task: Task) { taskDao.delete(task) }
}
