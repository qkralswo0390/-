package com.example.project

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.databinding.ActivityDongDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonPoint
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle

class DongDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var dongName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDongDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        dongName = intent.getStringExtra("dongName") ?: "정왕1동"
        val parkList = getParkList(dongName)

        val bottomSheet = findViewById<LinearLayout>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet).apply {
            peekHeight = 0
            isHideable = true
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        findViewById<TextView>(R.id.toolbarTitle).text = dongName
        findViewById<TextView>(R.id.dongdetailname).text = dongName
        findViewById<TextView>(R.id.toolbarSubtitle).text = "공원 · ${parkList.size}개 결과"

        //val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.item_list)
        //recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = ItemAdapter(parkList) {
        //    val intent = Intent(this, YouthParkActivity::class.java)
        //    startActivity(intent)
        //}

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        findViewById<ImageButton>(R.id.closeBottomSheetButton).setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun getParkList(dong: String): List<ListItem> {
        return parksByDong[dong]?.map { park ->
            ListItem(park.name, park.imageResId)
        } ?: emptyList()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))

        val bounds = LatLngBounds(LatLng(37.32, 126.71), LatLng(37.35, 126.75))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bounds.center, 14f))
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setMinZoomPreference(14f)

        val parkMarkers = parksByDong[dongName] ?: emptyList()
        for (park in parkMarkers) {
            val marker = mMap.addMarker(
                MarkerOptions().position(park.latLng).title(park.name)
            )
            marker?.tag = park.name
            Log.d("MAP", "일반 마커 추가됨: ${park.name}")
        }

        val parkImage = findViewById<ImageView>(R.id.parkImage)
        val parkInfo = findViewById<TextView>(R.id.parkInfo)

        mMap.setOnMarkerClickListener { marker ->
            Log.d("MAP", "✅ 마커 클릭됨: ${marker.title}, tag=${marker.tag}")
            val park = (parksByDong[dongName] ?: emptyList()).find { it.name == marker.tag }

            if (park != null) {
                parkImage.setImageResource(park.imageResId)

                val infoText = buildString {
                    append("📍 이름: ${park.name}\n")
                    append("🗺️ 주소: ${park.address ?: "주소 정보 없음"}\n")
                    append("ℹ️ 설명: ${park.description ?: "설명 없음"}")
                }
                parkInfo.text = infoText

                val halfHeight = resources.displayMetrics.heightPixels / 2
                bottomSheetBehavior.peekHeight = halfHeight
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                Log.d("MAP", "✅ 바텀시트 열림: ${park.name}")
            } else {
                Log.d("MAP", "❌ 해당 마커에 대한 공원 정보 없음")
            }

            false
        }

        mMap.setOnInfoWindowClickListener {
            Log.d("MAP", "ℹ️ InfoWindow 클릭됨: ${it.title}")
        }

        val geoJsonFileId = when (dongName) {
            "정왕1동" -> R.raw.jeongwang1dong_only
            else -> null
        }
    }
}