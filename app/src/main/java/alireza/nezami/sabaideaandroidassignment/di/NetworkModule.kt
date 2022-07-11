package alireza.nezami.sabaideaandroidassignment.di

import alireza.nezami.sabaideaandroidassignment.BuildConfig
import alireza.nezami.sabaideaandroidassignment.data.remote.service.SearchService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


private const val HTTP_READ_TIMEOUT_IN_SECONDS = 60
private const val HTTP_CALL_TIMEOUT_IN_SECONDS = 60

@Module(includes = [AppModule::class])
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.HEADERS)
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .readTimeout(HTTP_READ_TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .callTimeout(HTTP_CALL_TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URI)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Singleton
    @Provides
    fun provideSearchServiceService(
        retrofit: Retrofit
    ): SearchService = retrofit.create(SearchService::class.java)

}