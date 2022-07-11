package alireza.nezami.sabaideaandroidassignment.data.remote.repo

import alireza.nezami.sabaideaandroidassignment.data.remote.model.SearchResponse
import alireza.nezami.sabaideaandroidassignment.utils.Result
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchMoviesWithQuery(query: String): Flow<Result<SearchResponse>>
}