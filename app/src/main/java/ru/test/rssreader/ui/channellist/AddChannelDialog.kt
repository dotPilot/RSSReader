package ru.test.rssreader.ui.channellist

import android.app.Activity
import android.app.AlertDialog
import android.text.InputType
import android.widget.EditText
import androidx.annotation.StringRes
import ru.test.rssreader.R
import javax.inject.Inject

class AddChannelDialog(private val activity: Activity) {
        fun show(@StringRes title: Int, @StringRes text: Int, callback: (String) -> Unit) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(title)
            builder.setMessage(text)

            val input = EditText(activity)
            input.inputType = InputType.TYPE_TEXT_VARIATION_URI
            builder.setView(input)

            builder.setPositiveButton(R.string.add) { _, _ -> callback(input.text.toString().trim()) }
            builder.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }

            builder.create().show()
        }
    }