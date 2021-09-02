package ru.furman.booksexplorer.data.repository

import kotlinx.coroutines.delay
import ru.furman.booksexplorer.model.domain.Book
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

class BooksFakeRepository @Inject constructor() : BooksRepository {

    private val random = Random(592385)

    override suspend fun loadCarouselBooks(): List<Book> {
        delay(2000L)
        return List(7) {
            createBook("#$it")
        }
    }

    override suspend fun loadBooks(page: Int, pageSize: Int): List<Book> {
        delay(2000L)
        return List(pageSize) {
            createBook("page $page #$it")
        }
    }

    override suspend fun searchBooks(request: String): List<Book> {
        delay(2000L)
        return List(10) {
            createBook("#$it for $request")
        }
    }

    private fun createBook(titleSuffix: String): Book {
        val image = when (random.nextInt(0..2)) {
            0 -> "https://www.globusbooks.com/pictures/2075.jpg"
            1 -> "https://img4.labirint.ru/rc/745318c3dd818e54f20c922b8322a88a/220x340/books60/593024/cover.jpg?1564028758"
            else -> "https://m.media-amazon.com/images/M/MV5BZDM1YmQzODAtYjQ5ZC00M2IxLWEwYzktM2RjZDIxNWUzOGZjXkEyXkFqcGdeQXVyMjg3NTY4Nw@@._V1_.jpg"
        }
        return Book(
            title = "Evgeniy Onegin $titleSuffix",
            author = "Pushkin",
            genre = "Novel",
            description = "Read Pushkin's Eugene Onegin in Russian without the need for a dictionary with this edition. Bringing you Pushkin's entire masterpiece, this edition’s Russian and English word-by-word translation are displayed side by side on each page, the stress labeled in bold for each Russian word, thereby eliminating the need for a dictionary. This edition is a must for Russian language learners and Russian literature lovers wanting to read Pushkin’s original story.",
            isbn = "123412535",
            imageUrl = image,
            publishedDate = "213213",
            publisher = "Piter"
        )
    }


}