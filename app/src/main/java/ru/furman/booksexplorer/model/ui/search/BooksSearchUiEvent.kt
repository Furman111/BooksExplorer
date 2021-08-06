package ru.furman.booksexplorer.model.ui.search

import ru.furman.booksexplorer.model.ui.UiEvent

sealed class BooksSearchUiEvent : UiEvent {

    data class NewSearchRequest(val request: String) : BooksSearchUiEvent()

}