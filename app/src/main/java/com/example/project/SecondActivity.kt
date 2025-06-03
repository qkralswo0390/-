package com.example.project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ActivitySecondBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class SecondActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val southWest = LatLng(37.30, 126.70)  // 남서쪽 (시흥)
    private val northEast = LatLng(37.38, 126.87)  // 북동쪽

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 지도 프래그먼트 설정
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // 리스트 데이터 설정
        val recyclerView = findViewById<RecyclerView>(R.id.item_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = listOf(
            ListItem("파일 1"),
            ListItem("파일 2"),
            ListItem("파일 3"),
            ListItem("파일 4"),
            ListItem("파일 5")
        )

        val adapter = ItemAdapter(items) { item ->
            Toast.makeText(this, "${item.title} 클릭됨", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        }

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setMinZoomPreference(11f)

        val location = LatLng(37.3392, 126.7870)

        // 마커에 타이틀을 지정 (마커 구분용)
        val marker = mMap.addMarker(
            MarkerOptions().position(location).title("시흥 지역")
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 11f))

        val bounds = LatLngBounds(southWest, northEast)
        mMap.setLatLngBoundsForCameraTarget(bounds)

        // 마커 클릭 리스너 등록
        mMap.setOnMarkerClickListener { clickedMarker ->
            if (clickedMarker.title == "시흥 지역") {
                val intent = Intent(this, Siheung::class.java) // 다음 화면으로 이동
                intent.putExtra("marker_title", clickedMarker.title) // 필요 시 데이터 전달
                startActivity(intent)
            }
            true  // 클릭 이벤트 소비
        }
    }


    private fun requestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }
    }
}
