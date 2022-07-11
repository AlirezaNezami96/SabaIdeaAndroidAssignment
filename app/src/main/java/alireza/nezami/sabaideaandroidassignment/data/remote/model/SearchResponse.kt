package alireza.nezami.sabaideaandroidassignment.data.remote.model


import alireza.nezami.sabaideaandroidassignment.domain.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "data")
    val `data`: List<Data>
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "link_type")
        val linkType: String,
        @Json(name = "link_key")
        val linkKey: String,
        @Json(name = "theme")
        val theme: String,
        @Json(name = "output_type")
        val outputType: String,
        @Json(name = "movie_id")
        val movieId: String,
        @Json(name = "uid")
        val uid: String,
        @Json(name = "movie_title")
        val movieTitle: String,
        @Json(name = "movie_title_en")
        val movieTitleEn: String,
        @Json(name = "tag_id")
        val tagId: Any,
        @Json(name = "serial")
        val serial: Serial,
        @Json(name = "watermark")
        val watermark: Boolean,
        @Json(name = "HD")
        val hD: Boolean,
        @Json(name = "watch_list_action")
        val watchListAction: String,
        @Json(name = "commingsoon_txt")
        val commingsoonTxt: String,
        @Json(name = "rel_data")
        val relData: RelData,
        @Json(name = "badge")
        val badge: Badge,
        @Json(name = "rate_enable")
        val rateEnable: Boolean,
        @Json(name = "descr")
        val descr: String,
        @Json(name = "cover")
        val cover: String,
        @Json(name = "publish_date")
        val publishDate: String,
        @Json(name = "age_range")
        val ageRange: String,
        @Json(name = "pic")
        val pic: Pic,
        @Json(name = "rate_avrage")
        val rateAvrage: String,
        @Json(name = "avg_rate_label")
        val avgRateLabel: String,
        @Json(name = "pro_year")
        val proYear: String,
        @Json(name = "imdb_rate")
        val imdbRate: String,
        @Json(name = "categories")
        val categories: List<Category>,
        @Json(name = "duration")
        val duration: Duration,
        @Json(name = "countries")
        val countries: List<Country>,
        @Json(name = "dubbed")
        val dubbed: Dubbed,
        @Json(name = "subtitle")
        val subtitle: Subtitle,
        @Json(name = "audio")
        val audio: Audio,
        @Json(name = "language_info")
        val languageInfo: LanguageInfo,
        @Json(name = "director")
        val director: String,
        @Json(name = "last_watch")
        val lastWatch: List<Any>,
        @Json(name = "freemium")
        val freemium: Boolean,
        @Json(name = "position")
        val position: Int,
        @Json(name = "sid")
        val sid: Int,
        @Json(name = "uuid")
        val uuid: String
    ) {
        @JsonClass(generateAdapter = true)
        data class Serial(
            @Json(name = "enable")
            val enable: Boolean,
            @Json(name = "parent_title")
            val parentTitle: String,
            @Json(name = "season_id")
            val seasonId: Int,
            @Json(name = "serial_part")
            val serialPart: String,
            @Json(name = "part_text")
            val partText: String,
            @Json(name = "season_text")
            val seasonText: String,
            @Json(name = "last_part")
            val lastPart: String
        )

        @JsonClass(generateAdapter = true)
        data class RelData(
            @Json(name = "rel_type")
            val relType: String,
            @Json(name = "rel_id")
            val relId: String,
            @Json(name = "rel_uid")
            val relUid: Any,
            @Json(name = "rel_title")
            val relTitle: Any,
            @Json(name = "rel_type_txt")
            val relTypeTxt: String
        )

        @JsonClass(generateAdapter = true)
        data class Badge(
            @Json(name = "backstage")
            val backstage: Boolean,
            @Json(name = "vision")
            val vision: Boolean,
            @Json(name = "hear")
            val hear: Boolean,
            @Json(name = "online_release")
            val onlineRelease: Boolean,
            @Json(name = "free")
            val free: Boolean,
            @Json(name = "exclusive")
            val exclusive: Boolean,
            @Json(name = "commingsoon")
            val commingsoon: Boolean,
            @Json(name = "info")
            val info: List<Any>
        )

        @JsonClass(generateAdapter = true)
        data class Pic(
            @Json(name = "movie_img_s")
            val movieImgS: String,
            @Json(name = "movie_img_m")
            val movieImgM: String,
            @Json(name = "movie_img_b")
            val movieImgB: String
        )

        @JsonClass(generateAdapter = true)
        data class Category(
            @Json(name = "title_en")
            val titleEn: String,
            @Json(name = "title")
            val title: String,
            @Json(name = "link_key")
            val linkKey: String,
            @Json(name = "link_type")
            val linkType: String
        )

        @JsonClass(generateAdapter = true)
        data class Duration(
            @Json(name = "value")
            val value: Int,
            @Json(name = "text")
            val text: String
        )

        @JsonClass(generateAdapter = true)
        data class Country(
            @Json(name = "country")
            val country: String,
            @Json(name = "country_en")
            val countryEn: String
        )

        @JsonClass(generateAdapter = true)
        data class Dubbed(
            @Json(name = "enable")
            val enable: Boolean,
            @Json(name = "text")
            val text: String
        )

        @JsonClass(generateAdapter = true)
        data class Subtitle(
            @Json(name = "enable")
            val enable: Boolean,
            @Json(name = "text")
            val text: String
        )

        @JsonClass(generateAdapter = true)
        data class Audio(
            @Json(name = "languages")
            val languages: List<String>,
            @Json(name = "isMultiLanguage")
            val isMultiLanguage: Boolean
        )

        @JsonClass(generateAdapter = true)
        data class LanguageInfo(
            @Json(name = "flag")
            val flag: String,
            @Json(name = "text")
            val text: String
        )
    }
}

suspend fun SearchResponse.toDomainModel(): List<Movie> {
    return this.data.map { data ->
        Movie(
            movieTitle = data.movieTitle,
            hD = data.hD,
            description = data.descr,
            pic = data.pic.movieImgM,
            rateAverage = data.rateAvrage,
            proYear = data.proYear,
            imdbRate = data.imdbRate,
            categories = data.categories.map { it.title },
        )
    }
}