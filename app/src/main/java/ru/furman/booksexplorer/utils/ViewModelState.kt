package ru.furman.booksexplorer.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import ru.furman.booksexplorer.model.ui.UiEffect
import ru.furman.booksexplorer.model.ui.UiEvent
import ru.furman.booksexplorer.model.ui.UiState
import ru.furman.booksexplorer.viewmodel.BaseViewModel

@Composable
fun <State : UiState, Event : UiEvent, Effect : UiEffect> StatesOf(
    viewModel: BaseViewModel<State, Event, Effect>,
    block: @Composable (state: State, effects: Flow<Effect?>) -> Unit
) {
    val state by viewModel.state.collectAsState(viewModel.currentState)
    block(state, viewModel.effects)
}

@Composable
fun <Effect : UiEffect> CollectEffects(
    effects: Flow<Effect?>,
    onEffect: (Effect) -> Unit
) {
    LaunchedEffect(effects) {
        effects.collect { effect ->
            effect?.let { onEffect(effect) }
        }
    }
}