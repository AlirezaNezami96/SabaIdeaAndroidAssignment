package alireza.nezami.sabaideaandroidassignment.domain

import alireza.nezami.sabaideaandroidassignment.data.remote.model.SearchResponse
import alireza.nezami.sabaideaandroidassignment.data.remote.repo.SearchRepository
import alireza.nezami.sabaideaandroidassignment.data.remote.service.SearchService
import alireza.nezami.sabaideaandroidassignment.utils.Result
import alireza.nezami.sabaideaandroidassignment.utils.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

) : SearchRepository {
    override suspend fun searchMoviesWithQuery(query: String): Flow<Result<SearchResponse>> = flow {
        safeApiCall(dispatcher) {
            searchService.getCoffeeMachine(query)
        }
    }
}