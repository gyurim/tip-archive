package com.gyurim.airbnb

import android.app.Application
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.NaverMapSdk.NaverCloudPlatformClient


class AirbnbApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client = NaverCloudPlatformClient(BuildConfig.NAVER_MAP_CLIENT_ID)
    }
}