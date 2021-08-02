package ru.furman.booksexplorer.model.ui.details

import ru.furman.booksexplorer.model.ui.UiEvent

sealed class BookDetailsUiEvent : UiEvent {

    object OnBackPressed : BookDetailsUiEvent()

    data class PageSelected(val page: Int) : BookDetailsUiEvent()

}