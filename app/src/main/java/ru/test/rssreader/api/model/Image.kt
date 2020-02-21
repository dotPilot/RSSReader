package ru.test.rssreader.api.model

import android.media.Image
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "image", strict = false)
class Image (
    @field:Element(name = "url")
    var url: String? = null,

    @field:Element(name = "title")
    var title: String? = null,

    @field:Element(name = "link")
    var link: String? = null
)