package com.example.PositionalDataSourceSample.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
//import androidx.paging.PagingSource


@Dao
interface BeerListDao {
    @Query("SELECT * FROM beerlist")
    fun getAll(): List<BeerList>

    //DataSource.Factory & LiveData Sample
    // 반환 타입을 DataSource로 하면 자동으로 PositionalDataSource를 생성 (RoomDB).
    @Query("SELECT * FROM beerlist ORDER BY name COLLATE NOCASE ASC")
    fun allBeerListByName() : DataSource.Factory<Int, BeerList>

    //PagingSource Sample
    /*
    @Query("SELECT * FROM beerlist ORDER BY name COLLATE NOCASE ASC")
    fun allBeerListByName() : PagingSource<Int, BeerList>
     */

    @Insert
    fun insert(beerList : List<BeerList>)

    @Insert
    fun insert(beerList : BeerList)

    @Delete
    fun delete(beerList: BeerList)
}