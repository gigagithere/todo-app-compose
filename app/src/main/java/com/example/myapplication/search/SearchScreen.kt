package com.example.myapplication.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.CardGray
import com.example.myapplication.ui.theme.CardGreen
import com.example.myapplication.ui.theme.CardOrange
import com.example.myapplication.ui.theme.CardPurple
import com.example.myapplication.ui.theme.CardYellow


private data class Exercise(val title: String, val color: Color)


private val exercise = listOf(
    Exercise("Jumping Jacks", CardPurple),
    Exercise("Wall Sit", CardYellow),
    Exercise("Push Up", CardGreen),
    Exercise("Abdominal Crunch", CardOrange),
    Exercise("Squat", CardGray),
    Exercise("Lunges", CardPurple),
    Exercise("Squat", CardGray),
    Exercise("Lunges", CardPurple),
    Exercise("Squat", CardGray),
    Exercise("Lunges", CardPurple),
    Exercise("Squat", CardGray),
    Exercise("Lunges", CardPurple),
    Exercise("Squat", CardGray),
    Exercise("Lunges", CardPurple),
    Exercise("Squat", CardGray),
    Exercise("Lunges", CardPurple),
)

@Composable
fun SearchScreen(modifier: Modifier = Modifier,
                 onExerciseClick: () -> Unit
) {
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .background(color = MaterialTheme.colorScheme.background)
        .statusBarsPadding()
        .padding(horizontal = 24.dp)
    ) {
        PracticesTopBar()

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Practices",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = buildAnnotatedString {
            append("Exercises based on ")
            withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                append("your needs")
            }
        },
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            exercise.chunked(2).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { exercise ->
                        ExerciseCard(exercise = exercise,
                                    onClick = onExerciseClick,
                                    modifier = Modifier.weight(1f)
                        )
                    }
                    if (row.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun ExerciseCard(exercise: Exercise, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(exercise.color)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(
            text = exercise.title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.align(Alignment.TopStart)
        )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "↗",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
    }
}

@Composable
private fun PracticesTopBar() {
    Row(
        modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Logo"
        )
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search"
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(onExerciseClick = {})
}