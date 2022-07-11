package alireza.nezami.sabaideaandroidassignment.presentation.ui

import alireza.nezami.sabaideaandroidassignment.data.remote.model.toDomainModel
import alireza.nezami.sabaideaandroidassignment.data.remote.repo.SearchRepository
import alireza.nezami.sabaideaandroidassignment.domain.Movie
import alireza.nezami.sabaideaandroidassignment.utils.Result
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

    private val _movieList = MutableStateFlow<List<Movie>>(listOf())
    val movieList = _movieList

    fun searchMovies(query: String) = viewModelScope.launch {
        searchRepository.searchMoviesWithQuery(query).collect { result ->
            when (result) {
                is Result.Error -> {}
                is Result.Success -> {
                    _movieList.value = result.value.toDomainModel()
                }
                is Result.NetworkError -> {}
                is Result.Loading -> {}
            }
        }
    }
}