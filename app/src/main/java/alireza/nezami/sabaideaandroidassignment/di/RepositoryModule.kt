package alireza.nezami.sabaideaandroidassignment.di

import alireza.nezami.sabaideaandroidassignment.data.remote.repo.SearchRepository
import alireza.nezami.sabaideaandroidassignment.data.remote.service.SearchService
import alireza.nezami.sabaideaandroidassignment.domain.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, AppModule::class])
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideSearchRepository(
        coffeeMachineService: SearchService,
        dispatcher: CoroutineDispatcher
    ): SearchRepository =
        SearchRepositoryImpl(
            coffeeMachineService,
            dispatcher
        )


}