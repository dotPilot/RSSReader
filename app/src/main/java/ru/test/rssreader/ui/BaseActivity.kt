package ru.test.rssreader.ui

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import ru.test.rssreader.R
import ru.test.rssreader.api.RequestResult

open class BaseActivity : AppCompatActivity(){

    open fun showErrors(error: RequestResult?) {
        if (error == null) return
        when (error) {
            RequestResult.SUCCESS -> Unit
            RequestResult.DISCONNECTED -> showToast(R.string.net_error)
            RequestResult.BAD_URL -> showToast(R.string.url_error)
            RequestResult.NOT_A_FEED -> showToast(R.string.feed_error)
            RequestResult.UNKNOWN -> showToast(R.string.unknown_error)
        }
    }

    private fun showToast(@StringRes text: Int) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}