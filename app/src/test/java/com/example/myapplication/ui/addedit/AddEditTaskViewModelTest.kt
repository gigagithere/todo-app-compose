package com.example.myapplication.ui.addedit

import androidx.lifecycle.SavedStateHandle
import com.example.myapplication.FakeTaskRepository
import com.example.myapplication.MainDispatcherRule
import com.example.myapplication.data.Task
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class AddEditTaskViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun viewModel(repository: FakeTaskRepository, taskId: Long? = null) =
        AddEditTaskViewModel(
            savedStateHandle = SavedStateHandle(if (taskId == null) emptyMap() else mapOf("taskId" to taskId)),
            repository = repository
        )

    @Test
    fun `without a task id it is in add mode with an empty title`() {
        val viewModel = viewModel(FakeTaskRepository())

        assertFalse(viewModel.isEditMode)
        assertEquals("", viewModel.title)
    }

    @Test
    fun `the default task id of -1 also means add mode`() {
        val viewModel = viewModel(FakeTaskRepository(), taskId = -1L)

        assertFalse(viewModel.isEditMode)
    }

    @Test
    fun `saving in add mode inserts a task with the trimmed title`() {
        val repository = FakeTaskRepository()
        val viewModel = viewModel(repository)
        var done = false

        viewModel.updateTitle("  Buy milk  ")
        viewModel.saveTask { done = true }

        assertEquals(listOf("Buy milk"), repository.currentTasks.map { it.title })
        assertTrue(done)
    }

    @Test
    fun `saving a blank title does nothing`() {
        val repository = FakeTaskRepository()
        val viewModel = viewModel(repository)
        var done = false

        viewModel.updateTitle("   ")
        viewModel.saveTask { done = true }

        assertTrue(repository.currentTasks.isEmpty())
        assertFalse(done)
    }

    @Test
    fun `edit mode loads the existing task's title`() {
        val repository = FakeTaskRepository(listOf(Task(id = 7, title = "Old title")))
        val viewModel = viewModel(repository, taskId = 7L)

        assertTrue(viewModel.isEditMode)
        assertEquals("Old title", viewModel.title)
    }

    @Test
    fun `saving in edit mode updates the task and keeps its other fields`() {
        val original = Task(id = 7, title = "Old title", isCompleted = true, createdAt = 1_000)
        val repository = FakeTaskRepository(listOf(original))
        val viewModel = viewModel(repository, taskId = 7L)

        viewModel.updateTitle("New title")
        viewModel.saveTask {}

        assertEquals(listOf(original.copy(title = "New title")), repository.currentTasks)
    }
}
