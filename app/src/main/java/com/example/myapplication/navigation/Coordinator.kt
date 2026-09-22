package com.example.myapplication.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.home.HomeScreen
import com.example.myapplication.profile.ProfileScreen
import com.example.myapplication.search.ExerciseDetailScreen
import com.example.myapplication.search.SearchScreen

@Composable
fun Coordinator() {
var selectedTab by remember { mutableStateOf<BottomNavBar>(BottomNavBar.Home) }
var showDetail by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
                BottomNavBar.all.forEach{ tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab
                                    showDetail = false },
                        icon = { Icon(tab.icon, contentDescription = tab.title) },
                        label = { Text(tab.title)}
                    )
                }
            }
        }
    ) { innerPadding ->
        if (showDetail) {
            ExerciseDetailScreen(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()))
        } else {
            when (selectedTab) {
                BottomNavBar.Home -> HomeScreen(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()))
                BottomNavBar.Search -> SearchScreen(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                                                    onExerciseClick = { showDetail = true })
                BottomNavBar.Profile -> ProfileScreen(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()))
            }
        }
    }
}

@Preview
@Composable
fun CoordinatorPreview() {
    Coordinator()
}