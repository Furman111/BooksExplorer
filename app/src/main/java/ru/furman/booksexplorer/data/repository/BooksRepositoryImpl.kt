package ru.furman.booksexplorer.data.repository

import kotlinx.coroutines.withContext
import ru.furman.booksexplorer.model.domain.Book
import ru.furman.booksexplorer.network.BooksApi
import ru.furman.booksexplorer.utils.coroutine.dispatchers.Dispatchers
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val booksApi: BooksApi,
    private val dispatchers: Dispatchers
) : BooksRepository {

    override suspend fun loadCarouselBooks(): List<Book> {
        return withContext(dispatchers.io) {
            booksApi.getBooks(page = CAROUSEL_BOOKS_SEED, pageSize = CAROUSEL_BOOKS_COUNT)
                .data
                .map(::processImage)
        }
    }

    override suspend fun loadBooks(page: Int, pageSize: Int): List<Book> {
        return withContext(dispatchers.io) {
            booksApi.getBooks(page = page, pageSize = pageSize).data
                .map(::processImage)
        }
    }

    override suspend fun searchBooks(request: String): List<Book> {
        return withContext(dispatchers.io) {
            booksApi.getBooks(page = request.hashCode(), pageSize = SEARCH_BOOKS_COUNT).data
                .map(::processImage)
        }
    }

    private fun processImage(book: Book): Book {
        val newImageUrl = book.imageUrl.replace("http", "https")
        return book.copy(imageUrl = newImageUrl)
    }

    companion object {

        private const val CAROUSEL_BOOKS_SEED = 101

        private const val CAROUSEL_BOOKS_COUNT = 10

        private const val SEARCH_BOOKS_COUNT = 20

    }

}