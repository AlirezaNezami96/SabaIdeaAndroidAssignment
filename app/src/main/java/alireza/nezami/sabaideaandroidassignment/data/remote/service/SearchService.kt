package alireza.nezami.sabaideaandroidassignment.data.remote.service

import alireza.nezami.sabaideaandroidassignment.data.remote.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SearchService {

    @GET("api/en/v1/movie/movie/list/tagid/1000300/text/{query}/sug/on")
    suspend fun getCoffeeMachine(
        @Path("query") query: String,
        @Header("jsonType") type: String = "simple"
    ): SearchResponse
}