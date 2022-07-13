package alireza.nezami.sabaideaandroidassignment.presentation.ui

import alireza.nezami.sabaideaandroidassignment.data.remote.model.toDomainModel
import alireza.nezami.sabaideaandroidassignment.data.remote.repo.SearchRepository
import alireza.nezami.sabaideaandroidassignment.domain.Movie
import alireza.nezami.sabaideaandroidassignment.utils.Result
import alireza.nezami.sabaideaandroidassignment.utils.debounce
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _movieList =
        MutableStateFlow<SearchScreenState<List<Movie>>>(SearchScreenState.EMPTY)
    val movieList = _movieList


    val onQueryTextChange: (String) -> Unit = debounce(
        300L,
        viewModelScope,
    ) { searchQuery ->
        searchRepository.searchMoviesWithQuery(searchQuery).collect { result ->
            when (result) {
                is Result.Error -> {
                    _movieList.value = SearchScreenState.ERROR(result.error)
                }
                is Result.Success -> {
                    _movieList.value = SearchScreenState.SUCCESS(result.value.toDomainModel())
                }
                is Result.NetworkError -> {
                    _movieList.value = SearchScreenState.NETWORK
                }
                is Result.Loading -> {
                    _movieList.value = SearchScreenState.LOADING
                }
            }
        }
    }

    fun searchMovies(query: String) = viewModelScope.launch {
        onQueryTextChange(query)
    }
}

sealed class SearchScreenState<out T> {
    object LOADING : SearchScreenState<Nothing>()
    object EMPTY : SearchScreenState<Nothing>()
    object NETWORK : SearchScreenState<Nothing>()
    data class SUCCESS<T>(val value: T) : SearchScreenState<T>()
    data class ERROR(val message: String? = null) : SearchScreenState<Nothing>()
}