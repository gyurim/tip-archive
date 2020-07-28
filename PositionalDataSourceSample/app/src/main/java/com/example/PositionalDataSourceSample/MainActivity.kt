package com.example.PositionalDataSourceSample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.PositionalDataSourceSample.beerlist.BeerListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button -> go()
        }
    }

    private fun go(){
        val intent = Intent(this, BeerListActivity::class.java)
        startActivity(intent)
    }
}
