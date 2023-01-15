package com.gyurim.airbnb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naver.maps.map.MapView

class MainActivity : AppCompatActivity() {
    private val mapView: MapView by lazy {
        findViewById(R.id.mapView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // fragment -> 수명 주기를 가지고 있기 때문에 activity의 수명주기를 그대로 가져와 사용 ㄱㄴ. activity에서 사용할 때는 fragment 방법을 사용하면 됨
        // mapview -> 커스텀이 더 가능하지만, 뷰에 activity의 수명주기를 그대로 넘겨줘야하는 단점이 있음. fragment 내에서 지도를 사용해야할 경우엔 mapview 사용해서 붙여줘야함 (제약). activity에서 mapview 써도 됨

        mapView.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}