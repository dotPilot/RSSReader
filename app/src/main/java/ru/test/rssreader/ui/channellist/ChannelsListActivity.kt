package ru.test.rssreader.ui.channellist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_channels_list.*
import ru.test.rssreader.R
import ru.test.rssreader.RssReaderApp
import ru.test.rssreader.ui.BaseActivity
import ru.test.rssreader.ui.callbacks.RecyclerItemClickListener
import ru.test.rssreader.ui.callbacks.SwipeToDeleteCallback
import ru.test.rssreader.viewmodels.ChannelsListViewModel
import timber.log.Timber
import javax.inject.Inject

class ChannelsListActivity : BaseActivity() {

    lateinit var viewModel: ChannelsListViewModel
    @Inject
    lateinit var mAdapter: ChannelsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels_list)
        RssReaderApp.appComponent.inject(this)

        viewModel = ViewModelProvider(this).get(ChannelsListViewModel::class.java)

        viewModel.channelsLiveData.observe(this){
            list -> mAdapter.swap(list)
            Timber.d("ChannelsListAdapter swap ${list.size}")
        }
        initRecycleView();

        viewModel.errors.observe(this, Observer{
            it.getContentIfNotHandled()?.let {showErrors(it)}})

        fab.setOnClickListener({ view ->
            AddChannelDialog(this).show(
                ru.test.rssreader.R.string.add_channel,
                ru.test.rssreader.R.string.add_channel_text,
                {viewModel.addChannel(it) })
        })

    }

    private fun initRecycleView() {
        val itemTouchHelper = ItemTouchHelper(
            SwipeToDeleteCallback(
                mAdapter,
                viewModel,
                this
            )
        )
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        listView.addItemDecoration(dividerItemDecoration)

        itemTouchHelper.attachToRecyclerView(listView)
        listView.addOnItemTouchListener(
            RecyclerItemClickListener(this, listView, object:
                RecyclerItemClickListener.OnItemClickListener {
                override fun onLongItemClick(view: View?, position: Int) {
                }

                override fun onItemClick(view: View, position:Int) {
                    viewModel.showChannelItems(this@ChannelsListActivity, position)
                }
            })
        )
        listView.adapter = mAdapter
    }

}
