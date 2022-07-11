package alireza.nezami.sabaideaandroidassignment.presentation.ui


import alireza.nezami.sabaideaandroidassignment.R
import alireza.nezami.sabaideaandroidassignment.presentation.components.InputWrapper
import alireza.nezami.sabaideaandroidassignment.utils.Constants.SEARCH_MAX_CHAR
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


@Composable
fun SearchScreen(
    viewModel: SearchViewModel
) {
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

    Box(modifier = Modifier.fillMaxSize()) {
        SearchBox(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
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