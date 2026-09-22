package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.OfflineTaskRepository
import com.example.myapplication.data.TaskDatabase
import com.example.myapplication.data.TaskRepository

class TodoApplication : Application() {
    private val database: TaskDatabase by lazy { TaskDatabase.getDatabase(this) }
    val taskRepository: TaskRepository by lazy { OfflineTaskRepository(database.taskDao()) }
}
