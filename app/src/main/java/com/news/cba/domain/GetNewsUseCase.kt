package com.news.cba.domain

import com.news.cba.data.NewsRepository

class GetNewsUseCase(private val newsRepository: NewsRepository) {

    fun getNews() = newsRepository.getNews()
}