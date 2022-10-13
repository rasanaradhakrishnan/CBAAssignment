package com.news.cba

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.news.cba.data.Articles
import com.news.cba.domain.GetNewsUseCase
import com.news.cba.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class NewsViewModelTest : KoinTest {

    private lateinit var newsViewModel: NewsViewModel

    private lateinit var useCase: GetNewsUseCase

    val list = mutableListOf<Articles>()
    val result = MutableLiveData<List<Articles>>()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var listObserver: Observer<List<Articles>>

    @Mock
    lateinit var selectedListObserver: Observer<Articles>

    @Before
    fun setup() {
        useCase = mock(GetNewsUseCase::class.java)
        MockitoAnnotations.initMocks(this)
        startKoin {
            modules(appModule)
        }
        list.add(Articles("abc", "http://urlToImage", "2/10/2022", "Rasana", "Assignment"))
        result.value = list
        runBlocking {
            `when`(useCase.getNews()).thenReturn(result)
        }
        newsViewModel = NewsViewModel(useCase)
    }

    @Test
    fun testFetchArticles() {
        runBlocking {
            newsViewModel.newsList.observeForever(listObserver)
            newsViewModel.getAllNews()
            delay(1000)
            verify(useCase).getNews()
            verify(listObserver, timeout(50)).onChanged(list)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}