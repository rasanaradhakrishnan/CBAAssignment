package com.news.cba.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.news.cba.R
import com.news.cba.data.Articles
import com.news.cba.domain.GetNewsUseCase
import com.news.cba.presentation.view.NewsListFragment

class NewsViewModel(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {

    private val _newsList = MutableLiveData<List<Articles>>()
    val newsList: LiveData<List<Articles>>
        get() = _newsList
    var selectedNews = MutableLiveData<Articles>()


    fun getAllNews() =
        getNewsUseCase.getNews().observeForever {
            _newsList.postValue(it)
        }

    fun selectedArticle(articles: Articles, fragment: NewsListFragment) {
        findNavController(fragment).navigate(R.id.action_FirstFragment_to_SecondFragment)
        selectedNews.postValue(articles)
    }

    override fun onCleared() {
        _newsList.value = null
        super.onCleared()
    }

}