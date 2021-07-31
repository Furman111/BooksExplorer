package ru.furman.booksexplorer.viewmodel

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.furman.booksexplorer.model.ui.UiEffect
import ru.furman.booksexplorer.model.ui.UiEvent
import ru.furman.booksexplorer.model.ui.UiState
import ru.furman.booksexplorer.utils.SingleLiveEvent

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> : ViewModel() {

    val state: LiveData<State>
        get() = _state
    private val _state = MutableLiveData<State>()

    val effects: LiveData<Effect>
        get() = _effect
    private val _effect = SingleLiveEvent<Effect>()

    abstract fun handleEvent(event: Event)

    protected abstract fun createInitialState(): State

    @MainThread
    @CallSuper
    protected fun setState(state: State) {
        _state.value = state
    }

    @MainThread
    @CallSuper
    protected fun setEffect(effect: Effect) {
        _effect.setValue(effect)
    }

}