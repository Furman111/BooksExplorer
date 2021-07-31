package ru.furman.booksexplorer.repository

import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.network.BooksApi
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val booksApi: BooksApi
) : BooksRepository {

    override suspend fun getBooks(): List<Book> {
        return booksApi.getBooks(DEFAULT_BOOKS_COUNT).data
    }

    companion object {

        private const val DEFAULT_BOOKS_COUNT = 15

    }

}