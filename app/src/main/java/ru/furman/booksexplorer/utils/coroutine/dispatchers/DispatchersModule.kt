package ru.furman.booksexplorer.utils.coroutine.dispatchers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    @Singleton
    fun provideDispatchers(): Dispatchers {
        return Dispatchers(
            main = kotlinx.coroutines.Dispatchers.Main,
            io = kotlinx.coroutines.Dispatchers.IO,
            computation = kotlinx.coroutines.Dispatchers.Default,
        )
    }

}