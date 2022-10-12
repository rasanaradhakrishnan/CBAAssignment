package com.news.cba.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException


class NewsDAOImpl(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : NewsDAO {

    var okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(provideLoggingInterceptor())
        .certificatePinner(CertificatePinner.Builder().build()).build()

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    var retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var service: NewsClient = retrofit.create(NewsClient::class.java)


    override suspend fun getAllNews(): News {
        val result = safeApiCall(dispatcher) { service.getAllNews() }
        when (result) {
            is ApiResponse.Success<News> -> return result.value
            is ApiResponse.GenericError -> throw Exception(result.error)
            else -> throw Exception()
        }
    }

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): ApiResponse<T> {
        return withContext(dispatcher) {
            try {
                ApiResponse.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ApiResponse.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        ApiResponse.GenericError(code, throwable.message())
                    }
                    else -> {
                        ApiResponse.GenericError(null, null)
                    }
                }
            }
        }
    }

    interface NewsClient {
        @GET("v2/everything?q=tesla&from=2022-09-21&sortBy=publishedAt&apiKey=410388b2f7a340e38008c27cd0a6728c")
        suspend fun getAllNews(): News
    }
}