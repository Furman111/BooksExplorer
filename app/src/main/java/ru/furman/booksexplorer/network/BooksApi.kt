package ru.furman.booksexplorer.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.furman.booksexplorer.model.dto.BooksResponseDTO

interface BooksApi {

    @GET("/v1/books?_quantity={count}")
    suspend fun getBooks(@Path("count") count: Int): BooksResponseDTO

}