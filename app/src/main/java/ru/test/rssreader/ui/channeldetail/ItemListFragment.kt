package ru.test.rssreader.ui.channeldetail

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.item_list_fragment.*
import ru.test.rssreader.RssReaderApp
import ru.test.rssreader.ui.callbacks.RecyclerItemClickListener
import ru.test.rssreader.viewmodels.ItemsDetailViewModel
import timber.log.Timber
import javax.inject.Inject

class ItemListFragment : Fragment() {

    companion object {
        val BUNDLE_RECYCLER_LAYOUT = "recycler_view_save_instance"
    }

    @Inject
    lateinit var mAdapter: ItemListAdapter
    lateinit var viewModel: ItemsDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RssReaderApp.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(ru.test.rssreader.R.layout.item_list_fragment, container, false)
    }

    private fun setupSwipeRefresh(root: View) {
        val swiperefresh = root.findViewById<SwipeRefreshLayout>(ru.test.rssreader.R.id.swiperefresh)

        swiperefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            viewModel.refreshChannel()
        })
        swiperefresh.setColorSchemeResources(
            ru.test.rssreader.R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity?.let { ViewModelProvider(it).get(ItemsDetailViewModel::class.java) }!!

        setupRecyclerView(recycler_view)
        setupSwipeRefresh(swiperefresh)

        viewModel.loadItems()
        viewModel.itemListLiveData.observe(viewLifecycleOwner){
            list ->
                mAdapter.swap(list)
                Timber.d("ItemListFragment swaped with ${list.size} items")
                recycler_view.getLayoutManager()?.onRestoreInstanceState(savedInstanceState?.getParcelable<Parcelable>(BUNDLE_RECYCLER_LAYOUT))
        }

        viewModel.errors.observe(viewLifecycleOwner, {swiperefresh.isRefreshing = false})
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter

        val dividerItemDecoration = DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(activity!!, recyclerView, object:
                RecyclerItemClickListener.OnItemClickListener {
                override fun onLongItemClick(view: View?, position: Int) {
                }

                override fun onItemClick(view: View, position:Int) {
                    viewModel.selectItem(mAdapter.getItem(position))
                }
            })
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recycler_view.getLayoutManager()?.onSaveInstanceState())
    }
}
