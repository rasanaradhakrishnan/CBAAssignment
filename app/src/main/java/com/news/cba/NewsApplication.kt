package com.news.cba

import android.app.Application
import org.koin.core.context.GlobalContext.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }

    }
}