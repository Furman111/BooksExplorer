package ru.furman.booksexplorer.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class BooksDataModule {

    @Binds
    abstract fun bindBooksRepository(booksRepositoryImpl: BooksRepositoryImpl): BooksRepository

    @Binds
    @Named("fakeBooks")
    abstract fun bindBooksFakeRepository(booksFakeRepository: BooksFakeRepository): BooksRepository

}