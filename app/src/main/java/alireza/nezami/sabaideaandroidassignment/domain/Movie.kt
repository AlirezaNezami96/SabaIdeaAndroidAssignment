package alireza.nezami.sabaideaandroidassignment.domain

data class Movie(
    val movieTitle: String,
    val hD: Boolean,
    val description: String,
    val pic: String,
    val proYear: String,
    val imdbRate: String,
    val categories: List<String>,
)
