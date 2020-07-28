package com.example.PositionalDataSourceSample.beerlist

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
//import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.PositionalDataSourceSample.R
import com.example.PositionalDataSourceSample.data.BeerList

//DataSource.Factory & LiveData Sample
class BeerListAdapter(val context: Context) : PagedListAdapter<BeerList, BeerListViewHolder>(DIFF_CALLBACK){
    override fun onBindViewHolder(holder: BeerListViewHolder, position: Int) {
        val beerList = getItem(position)

        if(beerList == null)
            holder.clear()
        else
            holder.bindTo(beerList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerListViewHolder {
        return BeerListViewHolder(parent)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BeerList>(){
            override fun areItemsTheSame(oldItem: BeerList, newItem: BeerList): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BeerList, newItem: BeerList): Boolean =
                oldItem == newItem
        }
    }
}

//PagingSource Sample
/*
class BeerListAdapter : PagingDataAdapter<BeerList, BeerListViewHolder>(DIFF_CALLBACK){
    override fun onBindViewHolder(holder: BeerListViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerListViewHolder = BeerListViewHolder(parent)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BeerList>(){
            override fun areItemsTheSame(oldItem: BeerList, newItem: BeerList): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BeerList, newItem: BeerList): Boolean =
                oldItem == newItem
        }
    }
}
*/