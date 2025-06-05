package com.example.project

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityJeongwang1Binding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle

class jeongwang : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityJeongwang1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJeongwang1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // 기본 타이틀 제거

        val customView = layoutInflater.inflate(R.layout.jeongwang1_toolbar_layout, null)
        binding.toolbar.addView(customView)

// 뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // 1) 새로운 리스트 생성
        val newList = listOf(
            ListItem("토비공원", R.drawable.intersect),
            ListItem("정왕역", R.drawable.intersect),
            ListItem("아파트 단지", R.drawable.intersect),
            ListItem("공원 산책로", R.drawable.intersect),
        )

        // 2) 리사이클러뷰 레이아웃 매니저 설정
        binding.itemList.layoutManager = LinearLayoutManager(this)
        val adapter = ItemAdapter(newList) { item ->
            Toast.makeText(this, "${item.title} 클릭됨", Toast.LENGTH_SHORT).show()
        }
        binding.itemList.adapter = adapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
        )

        val layer = GeoJsonLayer(mMap, R.raw.jeongwang1dong_only, this)

        for (feature in layer.features) {
            val polygonStyle = GeoJsonPolygonStyle().apply {
                strokeColor = Color.RED        // 경계선 색
                strokeWidth = 7f               // 경계선 두께
            }
            feature.polygonStyle = polygonStyle
        }

        layer.addLayerToMap()

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setMinZoomPreference(14f)

        val jeongwang1dong = LatLng(37.3365, 126.7335)

        val bounds = LatLngBounds(
            LatLng(37.32, 126.71),  // 남서
            LatLng(37.35, 126.75)   // 북동
        )

        mMap.setLatLngBoundsForCameraTarget(bounds)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jeongwang1dong, 14f))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}