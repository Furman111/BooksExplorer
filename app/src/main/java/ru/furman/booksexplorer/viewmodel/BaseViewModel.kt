package ru.furman.booksexplorer.viewmodel

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.furman.booksexplorer.model.ui.UiEffect
import ru.furman.booksexplorer.model.ui.UiEvent
import ru.furman.booksexplorer.model.ui.UiState

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> : ViewModel() {

    val state: Flow<State>
        get() = _state.asSharedFlow()
    private val _state = MutableSharedFlow<State>()

    val effects: Flow<Effect>
        get() = _effect.receiveAsFlow()
    private val _effect = Channel<Effect>()

    val currentState: State
        get() {
            if (!::_currentState.isInitialized) {
                _currentState = createInitialState()
            }
            return _currentState
        }
    private lateinit var _currentState: State

    abstract fun handleEvent(event: Event)

    protected abstract fun createInitialState(): State

    @MainThread
    @CallSuper
    protected fun setState(state: State) {
        _currentState = state
        viewModelScope.launch {
            _state.emit(state)
        }
    }

    @MainThread
    @CallSuper
    protected fun setEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

}