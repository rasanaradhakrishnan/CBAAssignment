package com.news.cba.data

interface NewsDAO {

    suspend fun getAllNews(): News
}