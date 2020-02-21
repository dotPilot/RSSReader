package ru.test.rssreader.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url
import ru.test.rssreader.api.model.Feed

interface RssApi {
    @GET
    fun getFeed(@Url feed: String): Single<Feed>
}