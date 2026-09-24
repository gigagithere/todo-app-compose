package com.example.myapplication.ui.tasklist

import com.example.myapplication.FakeTaskRepository
import com.example.myapplication.MainDispatcherRule
import com.example.myapplication.data.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val older = Task(id = 1, title = "Older", createdAt = 1_000)
    private val newer = Task(id = 2, title = "Newer", createdAt = 2_000)

    private fun TestScope.startCollecting(viewModel: TaskListViewModel) {
        // uiState is a stateIn(WhileSubscribed) flow: it only runs while someone collects it.
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }
    }

    @Test
    fun `uiState lists the repository tasks, newest first`() = runTest {
        val viewModel = TaskListViewModel(FakeTaskRepository(listOf(older, newer)))
        startCollecting(viewModel)

        assertEquals(listOf(newer, older), viewModel.uiState.value.tasks)
    }

    @Test
    fun `uiState is empty when there are no tasks`() = runTest {
        val viewModel = TaskListViewModel(FakeTaskRepository())
        startCollecting(viewModel)

        assertTrue(viewModel.uiState.value.tasks.isEmpty())
    }

    @Test
    fun `toggleCompleted flips the completed flag`() = runTest {
        val repository = FakeTaskRepository(listOf(older))
        val viewModel = TaskListViewModel(repository)
        startCollecting(viewModel)

        viewModel.toggleCompleted(older)
        assertTrue(viewModel.uiState.value.tasks.single().isCompleted)

        viewModel.toggleCompleted(viewModel.uiState.value.tasks.single())
        assertFalse(viewModel.uiState.value.tasks.single().isCompleted)
    }

    @Test
    fun `deleteTask removes the task and asks the UI to show an undo snackbar`() = runTest {
        val repository = FakeTaskRepository(listOf(older, newer))
        val viewModel = TaskListViewModel(repository)
        startCollecting(viewModel)
        val events = mutableListOf<TaskListEvent>()
        // events is backed by a rendezvous Channel, so deleteTask suspends until this collects.
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.events.toList(events) }

        viewModel.deleteTask(newer)

        assertEquals(listOf(older), viewModel.uiState.value.tasks)
        assertEquals(listOf<TaskListEvent>(TaskListEvent.ShowUndoDeleteSnackbar(newer)), events)
    }

    @Test
    fun `undoDelete puts the deleted task back`() = runTest {
        val repository = FakeTaskRepository(listOf(older, newer))
        val viewModel = TaskListViewModel(repository)
        startCollecting(viewModel)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.events.toList(mutableListOf()) }

        viewModel.deleteTask(newer)
        viewModel.undoDelete(newer)

        assertEquals(listOf(newer, older), viewModel.uiState.value.tasks)
    }
}
