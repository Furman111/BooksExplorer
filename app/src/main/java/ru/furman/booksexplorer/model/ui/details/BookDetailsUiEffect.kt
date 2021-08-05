package ru.furman.booksexplorer.model.ui.details

import ru.furman.booksexplorer.model.ui.UiEffect

sealed class BookDetailsUiEffect : UiEffect {

    object NavigateBack : BookDetailsUiEffect()

}