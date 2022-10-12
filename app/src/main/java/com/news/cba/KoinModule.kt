package com.news.cba

import com.news.cba.data.NewsDAO
import com.news.cba.data.NewsDAOImpl
import com.news.cba.data.NewsRepository
import com.news.cba.domain.GetNewsUseCase
import com.news.cba.presentation.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<NewsDAO> { NewsDAOImpl() }
    single { NewsRepository(get()) }
    single { GetNewsUseCase(get()) }

    viewModel { NewsViewModel(get()) }

}

