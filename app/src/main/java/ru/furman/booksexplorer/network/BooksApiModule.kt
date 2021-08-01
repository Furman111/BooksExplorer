package ru.furman.booksexplorer.network

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
class BooksApiModule {

    @Provides
    fun provideBooksApi(okHttpClient: OkHttpClient): BooksApi {
        val responseGson = GsonBuilder().create()
        return Retrofit.Builder()
            .baseUrl(API_DOMAIN)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(responseGson))
            .build()
            .create(BooksApi::class.java)
    }

    companion object {

        private const val API_DOMAIN = "https://fakerapi.it/api/"

    }

}