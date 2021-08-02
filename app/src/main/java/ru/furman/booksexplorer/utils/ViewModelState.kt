package ru.furman.booksexplorer.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import ru.furman.booksexplorer.model.ui.UiEffect
import ru.furman.booksexplorer.model.ui.UiEvent
import ru.furman.booksexplorer.model.ui.UiState
import ru.furman.booksexplorer.viewmodel.BaseViewModel

@Composable
fun <State : UiState, Event : UiEvent, Effect : UiEffect> StatesOf(
    viewModel: BaseViewModel<State, Event, Effect>,
    block: @Composable (state: State, effect: Effect?) -> Unit
) {
    val state by viewModel.state.observeAsState(viewModel.currentState)
    val effect by viewModel.effects.observeAsState()
    block(state, effect)
}