package ru.test.rssreader.ui.channeldetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_list.*
import ru.test.rssreader.R
import ru.test.rssreader.RssReaderApp
import ru.test.rssreader.TextUtils
import ru.test.rssreader.ui.BaseActivity
import ru.test.rssreader.viewmodels.ItemsDetailViewModel


class ItemListActivity : BaseActivity() {

    private var detailFragment: Fragment? = null
    private var listFragment: Fragment? = null
    private var twoPane: Boolean = false
    lateinit var viewModel: ItemsDetailViewModel

    companion object{
        val FEED_URL_LINK_KEY = "url_link"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_list)

        RssReaderApp.appComponent.inject(this)

        if (item_detail_container != null) {
            twoPane = true
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val feedUrl = intent.getStringExtra(FEED_URL_LINK_KEY)

        viewModel = ViewModelProvider(this).get(ItemsDetailViewModel::class.java)
        viewModel.feedUrl = feedUrl

        addFragments()

        viewModel.errors.observe(this, Observer{
            it.getContentIfNotHandled()?.let {showErrors(it)}})

        viewModel.itemLiveData.observe(this, Observer{
            it.getContentIfNotHandled()?.let {
                if (!twoPane) supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, detailFragment!!, "detailFragment")
                    .addToBackStack("detail")
                    .commit()
            }}
        )

        Observable.just(viewModel)
            .subscribeOn(Schedulers.io())
            .subscribe{
                title = TextUtils.htmlToText(viewModel.getChannel().title)
            }
    }

    private fun addFragments() {
        listFragment = supportFragmentManager.findFragmentByTag("listFragment")
        detailFragment = supportFragmentManager.findFragmentByTag("detailFragment")

        val removeFragmentTransaction = supportFragmentManager.beginTransaction()
        if (listFragment == null)
            listFragment = ItemListFragment()
        else
            removeFragmentTransaction.remove(listFragment!!)

        var hasBackStack = false
        if (detailFragment == null)
            detailFragment = ItemDetailFragment()
        else {
            hasBackStack = supportFragmentManager.popBackStackImmediate()
            removeFragmentTransaction.remove(detailFragment!!)
        }

        removeFragmentTransaction.commit()
        supportFragmentManager.executePendingTransactions()

        var addFragmentTransaction = supportFragmentManager.beginTransaction()
        if(twoPane){
            addFragmentTransaction.add(R.id.item_list_container, listFragment!!, "listFragment")
            addFragmentTransaction.add(R.id.item_detail_container, detailFragment!!, "detailFragment")
        } else{
            addFragmentTransaction.add(R.id.fragment_container, listFragment!!, "listFragment")
            if(hasBackStack){
                addFragmentTransaction.commit()
                addFragmentTransaction = supportFragmentManager.beginTransaction()
                addFragmentTransaction.add(R.id.fragment_container, detailFragment!!, "detailFragment")
                    .addToBackStack("detail")
            }
        }
        addFragmentTransaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
