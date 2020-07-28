package com.example.PositionalDataSourceSample.beerlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.PositionalDataSourceSample.R
import com.example.PositionalDataSourceSample.data.BeerList

class BeerListViewHolder (parent:ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_beerlist, parent, false)){
        private val nameView = itemView.findViewById<TextView>(R.id.beer_name)
        private var beerList : BeerList? = null

        fun bindTo(beerList: BeerList?){
            this.beerList = beerList
            nameView.text = beerList?.name
        }

        fun clear(){
            nameView.text = null
        }
}
