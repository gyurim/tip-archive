package com.example.PositionalDataSourceSample.beerlist

//import androidx.paging.Pager
//import androidx.paging.PagingConfig

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.example.PositionalDataSourceSample.data.BeerList
import com.example.PositionalDataSourceSample.data.BeerListDB
import com.example.PositionalDataSourceSample.data.BeerListDao
import com.example.PositionalDataSourceSample.data.ioThread


//DataSource.Factory & LiveData Sample
class BeerListViewModel(app : Application) : AndroidViewModel(app){
    private val beerListDao = BeerListDB.get(app).beerListDao()
    private lateinit var beerList : LiveData<PagedList<BeerList>>
    init{
        val pagedListBuilder : LivePagedListBuilder<Int, BeerList> = LivePagedListBuilder<Int, BeerList>(
            beerListDao.allBeerListByName(),
            50
        )
        beerList = pagedListBuilder.build()
    }

    fun getBeerListLiveData() = beerList
}

/*
class BeerListViewModel (beerListDao: BeerListDao) : ViewModel() {
    val beerList: LiveData<PagedList<BeerList>> = LivePagedListBuilder(beerListDao.allBeerListByName(), 20).build()
}
*/


//PagingSource Sample
//pagedList class : 앱의 데이터 chunk 또는 페이지를 로드
/*
class BeerListViewModel(app :Application) : AndroidViewModel(app){
    private val dao = BeerListDB.get(app).beerListDao()

    val allBeerList = Pager(
        PagingConfig(
            pageSize = 6,
            enablePlaceholders = true,
            maxSize = 20
        )
    ) {
        dao.allBeerListByName()
    }.flow


    fun insert(text: CharSequence) = ioThread {
        dao.insert(BeerList(id = 0, name = text.toString()))
    }

    fun remove(beerList: BeerList) = ioThread {
        dao.delete(beerList)
    }
}
 */