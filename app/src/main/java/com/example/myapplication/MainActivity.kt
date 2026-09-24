package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.AppNavHost
import com.example.myapplication.ui.AppViewModelProvider
import com.example.myapplication.ui.ThemeViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()

            // enableEdgeToEdge() picks the status/navigation bar icon colors from the
            // system theme; the app has its own dark mode toggle, so follow that instead.
            DisposableEffect(isDarkMode) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { isDarkMode },
                    navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { isDarkMode }
                )
                onDispose {}
            }

            MyApplicationTheme(darkTheme = isDarkMode) {
                AppNavHost(
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = themeViewModel::toggleDarkMode
                )
            }
        }
    }
}
