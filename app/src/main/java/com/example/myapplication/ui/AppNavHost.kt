package com.example.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.addedit.AddEditTaskScreen
import com.example.myapplication.ui.tasklist.TaskListScreen

object Routes {
    const val TASK_LIST = "taskList"
    const val ADD_EDIT_TASK = "addEditTask?taskId={taskId}"
    const val ADD_EDIT_TASK_ADD = "addEditTask"
    fun editTask(taskId: Long) = "addEditTask?taskId=$taskId"
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.TASK_LIST) {
        composable(Routes.TASK_LIST) {
            TaskListScreen(
                onAddTask = { navController.navigate(Routes.ADD_EDIT_TASK_ADD) },
                onEditTask = { task -> navController.navigate(Routes.editTask(task.id)) }
            )
        }
        composable(
            route = Routes.ADD_EDIT_TASK,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
            AddEditTaskScreen(
                onDone = { navController.popBackStack() }
            )
        }
    }
}
