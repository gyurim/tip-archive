package com.example.PositionalDataSourceSample.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BeerList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "price") var price : Int
){
    override fun toString() = name
}