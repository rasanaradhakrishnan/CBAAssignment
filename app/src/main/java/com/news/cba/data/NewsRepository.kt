package com.news.cba.data

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class NewsRepository(private val newsDAO: NewsDAO) {

    fun getNews() =
        liveData(Dispatchers.IO) {
            try {
                val response = newsDAO.getAllNews()
                emit(response.articles)
            } catch (e: Exception) {
                emit(null)
            }
        }
}