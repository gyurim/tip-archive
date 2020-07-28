package com.example.PositionalDataSourceSample.beerlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.PositionalDataSourceSample.R
import com.example.PositionalDataSourceSample.data.BeerListDao as BeerListDao

//DataSource.Factory & Livedata sample
class BeerListActivity : AppCompatActivity(){
    private lateinit var viewModel: BeerListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beerlist)

        val recyclerView : RecyclerView = findViewById(R.id.beerlist_recycler_view)
        val adapter =  BeerListAdapter(this)

        viewModel= ViewModelProvider(this).get(BeerListViewModel::class.java)
        //val viewModel : BeerListViewModel by viewModels()
        recyclerView.adapter = adapter
        subscribeUi(adapter)
        //ViewModel과 Adapter 연결
    }

    private fun subscribeUi(adapter: BeerListAdapter){
        viewModel.getBeerListLiveData().observe(this, Observer {
            names -> if(names != null) adapter.submitList(names)
        })
    }

    companion object {
        private const val TAG = "BeerListActivity"
    }
}

//PagingSource sample
/*
class BeerListActivity : AppCompatActivity(){
    private val viewModel by viewModels<BeerListViewModel>()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beerlist)

        val recyclerView = findViewById<RecyclerView>(R.id.beerlist_recycler_view)
        val adapter =  BeerListAdapter()
        recyclerView.adapter = adapter

        // Subscribe the adapter to the ViewModel, so the items in the adapter are refreshed
        // when the list changes

        lifecycleScope.launch{
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.allBeerList.collectLatest { adapter.submitData(it) }
        }
    }

    companion object {
        private const val TAG = "BeerListActivity"
    }
}
*/