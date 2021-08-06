package ru.furman.booksexplorer.data.repository

import ru.furman.booksexplorer.model.domain.Book

interface BooksRepository {

    suspend fun loadCarouselBooks(): List<Book>

    suspend fun loadBooks(page: Int = 0, pageSize: Int = DEFAULT_PAGE_SIZE): List<Book>

    suspend fun searchBooks(request: String): List<Book>

    companion object {

        const val DEFAULT_PAGE_SIZE = 10

    }

}