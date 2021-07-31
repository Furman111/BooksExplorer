package ru.furman.booksexplorer.repository

import ru.furman.booksexplorer.model.domain.Book

interface BooksRepository {

    suspend fun getBooks(): List<Book>

}