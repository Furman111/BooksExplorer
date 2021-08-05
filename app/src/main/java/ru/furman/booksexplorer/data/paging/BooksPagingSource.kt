package ru.furman.booksexplorer.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.furman.booksexplorer.data.repository.BooksRepository
import ru.furman.booksexplorer.model.domain.Book
import javax.inject.Inject

class BooksPagingSource @Inject constructor(
    private val booksRepository: BooksRepository
) : PagingSource<Int, Book>() {

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 0
            val books =
                booksRepository.loadBooks(pageSize = params.loadSize, page = nextPageNumber)
            LoadResult.Page(
                data = books,
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber + 1
            )
        } catch (throwable: Exception) {
            return LoadResult.Error(throwable)
        }
    }

}