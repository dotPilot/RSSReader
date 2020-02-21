package ru.test.rssreader.injection

import android.content.Context
import android.content.pm.PackageManager
import dagger.Module
import dagger.Provides
import ru.test.rssreader.RssReaderApp
import javax.inject.Singleton

@Module
class AppModule(private val application: RssReaderApp) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return this.application
    }

    @Provides
    @Singleton
    internal fun providePackageManager(): PackageManager {
        return application.packageManager
    }
}