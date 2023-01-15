package com.gyurim.airbnb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var naverMap: NaverMap
    private val mapView: MapView by lazy {
        findViewById(R.id.mapView)
    }
    private lateinit var locationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // fragment -> 수명 주기를 가지고 있기 때문에 activity의 수명주기를 그대로 가져와 사용 ㄱㄴ. activity에서 사용할 때는 fragment 방법을 사용하면 됨
        // mapview -> 커스텀이 더 가능하지만, 뷰에 activity의 수명주기를 그대로 넘겨줘야하는 단점이 있음. fragment 내에서 지도를 사용해야할 경우엔 mapview 사용해서 붙여줘야함 (제약). activity에서 mapview 써도 됨

        mapView.onCreate(savedInstanceState)

        // mapview에서 navermap 객체 가져옴
        mapView.getMapAsync(this)
    }

    // mainActivity 에서 onMapReady()를 구현해줌으로써 mainActivity 는 OnMapReadyCallback의 구현체가 되었음
    // 따라서 mainActivity 속 getMapAsync(this)에서 this를 쓸 수 있는 것
    override fun onMapReady(map: NaverMap) {
        naverMap = map

        // 줌 레벨 정보
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.497885, 127.027512))
        naverMap.moveCamera(cameraUpdate)

        // 현위치
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = true // 현재 위치 버튼

        locationSource = FusedLocationSource(this@MainActivity, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }

        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}