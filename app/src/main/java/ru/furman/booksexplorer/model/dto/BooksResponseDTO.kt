package ru.furman.booksexplorer.model.dto

import ru.furman.booksexplorer.model.domain.Book

data class BooksResponseDTO(val data: List<Book>)