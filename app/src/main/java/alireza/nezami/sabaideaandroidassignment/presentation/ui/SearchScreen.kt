package alireza.nezami.sabaideaandroidassignment.presentation.ui


import alireza.nezami.sabaideaandroidassignment.R
import alireza.nezami.sabaideaandroidassignment.domain.Movie
import alireza.nezami.sabaideaandroidassignment.presentation.components.InputWrapper
import alireza.nezami.sabaideaandroidassignment.utils.Constants.SEARCH_MAX_CHAR
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.SubcomposeAsyncImage
import kotlin.random.Random


@Composable
fun SearchScreen(
    viewModel: SearchViewModel
) {
    val movieListState by viewModel.movieList.collectAsState()

    val inputWrapper by remember {
        mutableStateOf(InputWrapper())
    }

    var fieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                inputWrapper.value,
                TextRange(inputWrapper.value.length)
            )
        )
    }

    var showClearButtonState by rememberSaveable {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBox(
            modifier = Modifier
                .fillMaxWidth(),
            fieldValue = fieldValue,
            showClearButton = showClearButtonState,
            onValueChange = {
                fieldValue = it
                if (fieldValue.text.isNotBlank()) {
                    showClearButtonState = true
                    viewModel.searchMovies(fieldValue.text)
                }
            },
            onSearch = {

            },
            onClear = {
                showClearButtonState = false
            }
        )

        MovieList(
            modifier = Modifier
                .fillMaxSize(),
            state = movieListState
        )
    }
}

@Composable
fun MovieList(modifier: Modifier, state: SearchScreenState<List<Movie>>) {
    Box(modifier = modifier) {
        if (state is SearchScreenState.LOADING) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (state is SearchScreenState.ERROR) {
            Text(
                text = state.message ?: stringResource(R.string.something_happend),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (state is SearchScreenState.SUCCESS) {
            LazyColumn {
                items(state.value) { item ->
                    MovieItem(
                        item = item,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    modifier: Modifier,
    item: Movie
) {
    Card(modifier = modifier) {
        ConstraintLayout(modifier = Modifier) {
            val (image, quality, title, description, productYear, imdbRate, categories) = createRefs()

            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                    },
                contentScale = ContentScale.Crop,
                model = item.pic,
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = null
            )

            if (item.hD) {
                Text(
                    text = stringResource(R.string.HD),
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .constrainAs(quality) {
                            end.linkTo(image.end, margin = 8.dp)
                            top.linkTo(image.top, margin = 8.dp)
                        }
                )
            }

            Text(
                text = item.movieTitle,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(image.end, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    top.linkTo(parent.top, margin = 16.dp)
                }
            )

            Text(
                text = item.description,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.constrainAs(description) {
                    start.linkTo(image.end, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    top.linkTo(title.bottom, margin = 8.dp)
                }
            )

            Text(
                text = "سال تولید: " + item.proYear,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.constrainAs(productYear) {
                    end.linkTo(parent.end, margin = 16.dp)
                    top.linkTo(description.bottom, margin = 16.dp)
                }
            )

            Text(
                text = "imdb Rate: ${item.imdbRate}",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.constrainAs(imdbRate) {
                    start.linkTo(image.end, margin = 16.dp)
                    top.linkTo(description.bottom, margin = 16.dp)
                }
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(categories) {
                        top.linkTo(imdbRate.bottom, margin = 16.dp)
                    }
            ) {
                items(item.categories) { category ->
                    Box(
                        modifier = Modifier
                            .background(
                                color = randomColor()
                            )
                            .clip(
                                RoundedCornerShape(50.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }
            }

        }
    }
}

/**
 * Contain search field
 * Completely stateless
 */
@Composable
fun SearchBox(
    modifier: Modifier,
    fieldValue: TextFieldValue,
    onValueChange: (query: TextFieldValue) -> Unit,
    showClearButton: Boolean,
    onSearch: () -> Unit,
    onClear: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(0)
    ) {
        TextField(
            modifier = Modifier
                .padding(vertical = 8.dp),
            value = fieldValue,
            placeholder = {
                Text(text = stringResource(R.string.search_movies))
            },
            onValueChange = {
                if (it.text.length <= SEARCH_MAX_CHAR) {
                    onValueChange(it)
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                placeholderColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearch() }),
            trailingIcon = {
                if (showClearButton) {
                    IconButton(
                        onClick = onClear
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "search bar clear button",
                            tint = Color.Gray
                        )
                    }
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "search bar back button",
                    tint = Color.Gray
                )
            }
        )
    }
}

private fun randomColor(): Color {
    return Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), 256).apply {
        Log.i("TAG", "randomColor: ${this.colorSpace}")
    }
}