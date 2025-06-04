package com.example.project

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ActivityJeongwangBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions

class jeongwang : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityJeongwangBinding

    private val southWest = LatLng(37.32, 126.68)  // 남서쪽 (시흥)
    private val northEast = LatLng(37.48, 126.88)
    private val siheungBounds = LatLngBounds(southWest, northEast)
    private val siheungCenter = LatLng(37.40, 126.78)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJeongwangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // 기본 타이틀 제거

        val customView = layoutInflater.inflate(R.layout.toolbar_layout, null)
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
            ListItem("정왕역", null),
            ListItem("아파트 단지", null),
            ListItem("공원 산책로", null),
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

        val jeongwangPolygonPoints = listOf(
            LatLng(37.3458, 126.7372),  // 서쪽
            LatLng(37.3578, 126.7525),  // 북서
            LatLng(37.3619, 126.7754),  // 북
            LatLng(37.3571, 126.7982),  // 북동
            LatLng(37.3432, 126.8059),  // 동
            LatLng(37.3296, 126.7878),  // 남동
            LatLng(37.3280, 126.7583),  // 남
            LatLng(37.3360, 126.7394)   // 남서
        )

        val jeongwangPolygon = mMap.addPolygon(
            PolygonOptions()
                .addAll(jeongwangPolygonPoints)
                .strokeColor(Color.RED)
                .strokeWidth(5f)
                .fillColor(0x22FF0000) // 반투명 빨간색
        ) // 빨간색 투명 배경

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setMinZoomPreference(14f)

        val jeongwangBounds = LatLngBounds(
            LatLng(37.3280, 126.7372),  // 남서
            LatLng(37.3619, 126.8059)   // 북동
        )
        mMap.setLatLngBoundsForCameraTarget(jeongwangBounds)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.345, 126.77), 14f))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}