package com.example.project

import android.Manifest
import android.R.attr.fillColor
import android.R.attr.strokeColor
import android.R.attr.strokeWidth
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
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
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle

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

        // 툴바 설정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 지도 프래그먼트 설정
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // 리스트 데이터 설정
        val recyclerView = findViewById<RecyclerView>(R.id.item_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = listOf(
            ListItem("거북섬동"),
            ListItem("군자동"),
            ListItem("능곡동"),
            ListItem("대야동"),
            ListItem("목감동"),
            ListItem("배곧1동"),
            ListItem("배곧2동"),
            ListItem("신천동"),
            ListItem("연성동"),
            ListItem("은행동"),
            ListItem("장곡동"),
            ListItem("정왕1동"),
            ListItem("정왕2동"),
            ListItem("정왕3동"),
            ListItem("정왕4동"),
            ListItem("정왕본동")
        )

        val adapter = SimpleItemAdapter(items) { item ->
            val intent = Intent(this, DongDetailActivity::class.java)
            intent.putExtra("dongName", item.title)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val layer = GeoJsonLayer(mMap, R.raw.sihueng, this)

        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
        )

        for (feature in layer.features) {
            val polygonStyle = GeoJsonPolygonStyle().apply {
                strokeWidth = 8f
            }

            val fullName = feature.getProperty("행정동명") ?: ""
            val dongName = fullName.split(" ").lastOrNull() ?: ""

            polygonStyle.strokeColor = when (dongName) {
                "거북섬동"   -> Color.rgb(139, 0, 0)
                "과림동"     -> Color.rgb(0, 0, 139)
                "군자동"     -> Color.rgb(0, 100, 0)
                "능곡동"     -> Color.rgb(139, 69, 19)
                "대야동"     -> Color.rgb(85, 107, 47)
                "매화동"     -> Color.rgb(72, 61, 139)
                "목감동"     -> Color.rgb(128, 0, 128)
                "배곧1동"    -> Color.rgb(0, 139, 139)
                "배곧2동"    -> Color.rgb(255, 140, 0)
                "신천동"     -> Color.rgb(47, 79, 79)
                "신현동"     -> Color.rgb(184, 134, 11)
                "연성동"     -> Color.rgb(60, 179, 113)
                "월곶동"     -> Color.rgb(199, 21, 133)
                "은행동"     -> Color.rgb(25, 25, 112)
                "장곡동"     -> Color.rgb(255, 69, 0)
                "정왕1동"    -> Color.rgb(0, 0, 0)
                "정왕2동"    -> Color.rgb(255, 0, 255)
                "정왕3동"    -> Color.rgb(70, 130, 180)
                "정왕4동"    -> Color.rgb(128, 128, 0)
                "정왕본동"   -> Color.rgb(0, 255, 127)
                else         -> Color.DKGRAY
            }
            feature.polygonStyle = polygonStyle
        }

        layer.addLayerToMap()

        val siheungBounds = LatLngBounds(
            LatLng(37.32, 126.68),
            LatLng(37.48, 126.88)
        )
        mMap.setLatLngBoundsForCameraTarget(siheungBounds)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.40, 126.78), 12f))
        mMap.uiSettings.isZoomControlsEnabled = true

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
                val intent = Intent(this, DongDetailActivity::class.java)
                intent.putExtra("marker_title", clickedMarker.title)
                startActivity(intent)
            }
            true
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
