package com.example.PositionalDataSourceSample.beerlist

import androidx.paging.PositionalDataSource
import com.example.PositionalDataSourceSample.data.BeerList

//RoomDB의 경우 자동으로 PositionlDataSource 생성
class BeerListDataSource : PositionalDataSource<BeerList>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<BeerList>) {
        TODO("Not yet implemented")
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<BeerList>) {
        TODO("Not yet implemented")
    }

}