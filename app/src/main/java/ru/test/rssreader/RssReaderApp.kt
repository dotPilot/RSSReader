package ru.test.rssreader

import android.app.Application
import ru.test.rssreader.injection.AppModule
import ru.test.rssreader.injection.ApplicationComponent
import ru.test.rssreader.injection.DaggerApplicationComponent
import timber.log.Timber


class RssReaderApp : Application() {

    companion object{
        lateinit var appComponent : ApplicationComponent
    }
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}