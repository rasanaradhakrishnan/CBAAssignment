package com.news.cba.data

data class News(val articles: List<Articles>) : ApiResponse<News>()