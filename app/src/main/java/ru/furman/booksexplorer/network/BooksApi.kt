package ru.furman.booksexplorer.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.furman.booksexplorer.model.dto.BooksResponseDTO

interface BooksApi {

    @GET("v1/books")
    suspend fun getBooks(
        @Query("_seed") page: Int,
        @Query("_quantity") pageSize: Int
    ): BooksResponseDTO

}