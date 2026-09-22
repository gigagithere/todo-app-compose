package com.example.myapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavBar(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    data object Search : BottomNavBar(
        route = "search",
        title = "Search",
        icon = Icons.Default.Search
    )

    data object Profile : BottomNavBar(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

    companion object {
        val all:List<BottomNavBar> by lazy { listOf(Home, Search, Profile) }
    }
}