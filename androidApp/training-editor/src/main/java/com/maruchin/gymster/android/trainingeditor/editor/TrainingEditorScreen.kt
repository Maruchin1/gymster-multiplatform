package com.maruchin.gymster.android.trainingeditor.editor

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.maruchin.gymster.android.ui.AppTheme
import com.maruchin.gymster.android.ui.LoadingContent
import com.maruchin.gymster.data.trainings.model.sampleTrainingBlocks
import com.maruchin.gymster.feature.trainingeditor.editor.TrainingEditorUiState
import kotlinx.coroutines.launch

@Composable
internal fun TrainingEditorScreen(state: TrainingEditorUiState, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopBar(loadedState = state as? TrainingEditorUiState.Loaded, onBack = onBack)
        }
    ) { contentPadding ->
        AnimatedContent(
            targetState = state,
            contentKey = { it::class },
            label = "TrainingEditorContent",
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            when (it) {
                TrainingEditorUiState.Loading -> LoadingContent()
                is TrainingEditorUiState.Loaded -> LoadedContent(state = it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(loadedState: TrainingEditorUiState.Loaded?, onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = loadedState?.training?.name.orEmpty())
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun LoadedContent(state: TrainingEditorUiState.Loaded) {
    val pagerState = rememberPagerState { state.training.exercises.size }
    val scope = rememberCoroutineScope()

    Column {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            state.training.exercises.forEachIndexed { index, exercise ->
                Tab(
                    selected = index == pagerState.currentPage,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(index) }
                    },
                    text = { Text(text = "${index + 1}") },
                    icon = {
                        if (exercise.isComplete) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                        } else {
                            Icon(imageVector = Icons.Outlined.Circle, contentDescription = null)
                        }
                    }
                )
            }
        }
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val exercise = state.training.exercises[page]
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(text = exercise.name, style = MaterialTheme.typography.titleLarge)
                        Text(
                            text = "Sets: ${exercise.sets}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Reps: ${exercise.reps}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                exercise.progress.forEach { setWithProgress ->
                    OutlinedCard(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Regular set",
                                style = MaterialTheme.typography.labelLarge
                            )
                            AnimatedVisibility(visible = setWithProgress.isComplete) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = setWithProgress.toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(text = "Previous")
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Next")
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TrainingEditorScreen_LoadedPreview() {
    AppTheme {
        TrainingEditorScreen(
            state = TrainingEditorUiState.Loaded(
                sampleTrainingBlocks.first().weeks.first().trainings.first()
            ),
            onBack = {}
        )
    }
}
