package ru.test.rssreader.ui.channeldetail

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.item_detail.*
import ru.test.rssreader.R
import ru.test.rssreader.viewmodels.ItemsDetailViewModel

class ItemDetailFragment : Fragment() {

    lateinit var viewModel: ItemsDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.let { ViewModelProvider(it).get(ItemsDetailViewModel::class.java)}!!

        web_view.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progress_bar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progress_bar?.visibility = View.INVISIBLE
            }
        })
        web_view.settings.javaScriptEnabled = true

        viewModel.itemLiveData.observe(viewLifecycleOwner, Observer {
            web_view.loadUrl(viewModel.itemLiveData.value?.pickContent()?.link)
        })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity?.let { ViewModelProvider(it).get(ItemsDetailViewModel::class.java)}!!
    }
}
