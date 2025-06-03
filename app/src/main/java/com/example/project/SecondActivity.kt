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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class SecondActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val southWest = LatLng(37.32, 126.68)  // 남서쪽 (시흥)
    private val northEast = LatLng(37.48, 126.88)  // 북동쪽

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // 툴바 설
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
            ListItem("정왕동"),
            ListItem("대야동"),
            ListItem("신현동"),
            ListItem("은행동"),
            ListItem("매화동")
        )

        val adapter = ItemAdapter(items) { item ->
            when (item.title) {
                "정왕동" -> {
                    val intent = Intent(this, jeongwang::class.java)
                    startActivity(intent)
                }
                "대야동" -> {
                    val intent = Intent(this, daeya::class.java)
                    startActivity(intent)
                }
                "신현동" -> {
                    val intent = Intent(this, sinhyeon::class.java)
                    startActivity(intent)
                }
                "은행동" -> {
                    val intent = Intent(this, eunhaeng::class.java)
                    startActivity(intent)
                }
                "매화동" -> {
                    val intent = Intent(this, maehwa::class.java)
                    startActivity(intent)
                }
            }
        }
        recyclerView.adapter = adapter

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermissions()
            return
        }

        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setMinZoomPreference(12f)

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentLatLng = LatLng(location.latitude, location.longitude)

                val bounds = LatLngBounds(southWest, northEast)
                mMap.setLatLngBoundsForCameraTarget(bounds)

                if (bounds.contains(currentLatLng)) {
                    mMap.addMarker(
                        MarkerOptions().position(currentLatLng).title("현재 내 위치")
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 11f))
                } else {
                    Toast.makeText(this, "현재 위치는 시흥 지역 범위를 벗어났습니다.", Toast.LENGTH_SHORT).show()

                    // 기본 중심 좌표를 시흥 중심으로 설정
                    val defaultLatLng = LatLng(37.3392, 126.7870)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 11f))
                }
            } else {
                Toast.makeText(this, "현재 위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()

                val defaultLatLng = LatLng(37.3392, 126.7870)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLatLng, 11f))
            }
        }


        // 마커 클릭 리스너 등록
        mMap.setOnMarkerClickListener { clickedMarker ->
            if (clickedMarker.title == "시흥 지역") {
                val intent = Intent(this, jeongwang::class.java) // 다음 화면으로 이동
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
