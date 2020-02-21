package ru.test.rssreader

import android.os.Build
import android.text.Html


class TextUtils {
    companion object{
        fun htmlToText(html: String) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
        else
            Html.fromHtml(html)
    }
}