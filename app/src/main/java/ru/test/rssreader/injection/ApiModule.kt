package ru.test.rssreader.injection

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import ru.test.rssreader.BuildConfig
import ru.test.rssreader.api.RssApi
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit


@Module
class ApiModule() {

    val cacheSize: Long = 10 * 1024 * 1024
    val cacheTimeSec = 30

    val cacheInterceptor: Interceptor
        get() = Interceptor {
            val response = it.proceed(it.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(cacheTimeSec, TimeUnit.SECONDS)
                .build()

            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }

    @Provides
    fun provideOkHttpClient(
        context: Context,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val cache = Cache(File(context.cacheDir, "http-cache"), cacheSize)
        return OkHttpClient.Builder()
            .addInterceptor(cacheInterceptor)
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Timber.d("Retrofit log: $message") })
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    fun provideRssApi(client: OkHttpClient) = retrofit2.Retrofit.Builder()
        .client(client)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("http://ya.ru")
        .build()
        .create(RssApi::class.java)
}