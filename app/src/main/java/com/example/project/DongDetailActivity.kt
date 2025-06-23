package com.example.project

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project.databinding.ActivityDongDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DongDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var dongName: String
    private lateinit var mapContainer: FrameLayout

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
        mapContainer = findViewById(R.id.mapContainer)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet).apply {
            peekHeight = 0
            isHideable = true
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        findViewById<TextView>(R.id.toolbarTitle).text = dongName
        findViewById<TextView>(R.id.dongdetailname).text = dongName
        findViewById<TextView>(R.id.toolbarSubtitle).text = "공원 · ${parkList.size}개 결과"

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        findViewById<ImageButton>(R.id.closeBottomSheetButton).setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            val layoutParams = mapContainer.layoutParams
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            mapContainer.layoutParams = layoutParams
        }
    }

    private fun getParkList(dong: String): List<ListItem> {
        return parksByDong[dong]?.map { park ->
            ListItem(park.name, park.imageResId)
        } ?: emptyList()
    }

    private fun drawPolylinesFromTxt(dong: String) {
        val fileResId = when (dong) {
            "거북섬동" -> R.raw.geobukseom_polyline
            "군자동" -> R.raw.gunjadong_polyline
            "능곡동" -> R.raw.neunggokdong_polyline
            "대야동" -> R.raw.daeya_polyline
            "목감동" -> R.raw.mokgamdong_polyline
            "배곧1동" -> R.raw.baegot1_polyline
            "배곧2동" -> R.raw.baegot2_polyline
            "신천동" -> R.raw.sincheon_polyline
            "연성동" -> R.raw.yeonsung_polyline
            "은행동" -> R.raw.eunhaung_polyline
            "장곡동" -> R.raw.janggok_polyline
            "정왕1동" -> R.raw.jeongwang1dong_polyline
            "정왕2동" -> R.raw.jeongwang2_polyline
            "정왕3동" -> R.raw.jeongwang3_polyline
            "정왕4동" -> R.raw.jeongwang4_polyline
            "정왕본동" -> R.raw.jeongwangbon_polyline
            else -> return
        }
        try {
            val inputStream = resources.openRawResource(fileResId)
            val lines = inputStream.bufferedReader().readLines()

            var currentLine = mutableListOf<LatLng>()
            for (line in lines) {
                if (line.trim() == "---") {
                    if (currentLine.isNotEmpty()) {
                        mMap.addPolyline(
                            PolylineOptions()
                                .addAll(currentLine)
                                .color(Color.RED)
                                .width(8f)
                        )
                        currentLine.clear()
                    }
                } else {
                    val latLngString = line.removePrefix("LatLng(").removeSuffix(")")
                    val parts = latLngString.split(",")
                    if (parts.size == 2) {
                        val lat = parts[0].trim().toDouble()
                        val lng = parts[1].trim().toDouble()
                        currentLine.add(LatLng(lat, lng))
                    }
                }
            }

            if (currentLine.isNotEmpty()) {
                mMap.addPolyline(
                    PolylineOptions()
                        .addAll(currentLine)
                        .color(Color.RED)
                        .width(8f)
                )
            }

        } catch (e: Exception) {
            Log.e("TXT", "경계선 폴리라인 그리기 실패", e)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setMinZoomPreference(14.7f)

        val centerLatLng = when (dongName) {
            "거북섬동" -> LatLng(37.324748, 126.684966)
            "군자동" -> LatLng(37.356642, 126.778763)
            "능곡동" -> LatLng(37.365299, 126.809174)
            "대야동" -> LatLng(37.453077, 126.799569)
            "목감동" -> LatLng(37.382090, 126.851719)
            "배곧1동" -> LatLng(37.376067, 126.730287)
            "배곧2동" -> LatLng(37.362662, 126.717535)
            "신천동" -> LatLng(37.438679, 126.780113)
            "연성동" -> LatLng(37.388891, 126.805796)
            "은행동" -> LatLng(37.432588, 126.807991)
            "장곡동" -> LatLng(37.382528, 126.783634)
            "정왕1동" -> LatLng(37.331675, 126.735278)
            "정왕2동" -> LatLng(37.341702, 126.721225)
            "정왕3동" -> LatLng(37.343833, 126.706295)
            "정왕4동" -> LatLng(37.362508, 126.731116)
            "정왕본동" -> LatLng(37.358199, 126.751292)
            else -> LatLng(37.33, 126.73)
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLatLng, 14.7f))

        val restrictionBounds = when (dongName) {
            "거북섬동" -> LatLngBounds(LatLng(37.318, 126.66), LatLng(37.33, 126.71))
            "군자동"   -> LatLngBounds(LatLng(37.348, 126.765), LatLng(37.362, 126.794))
            "능곡동"   -> LatLngBounds(LatLng(37.364, 126.802), LatLng(37.368, 126.815))
            "대야동"   -> LatLngBounds(LatLng(37.44, 126.79), LatLng(37.47, 126.81))
            "목감동"   -> LatLngBounds(LatLng(37.37, 126.84), LatLng(37.39, 126.87))
            "배곧1동" -> LatLngBounds(LatLng(37.36, 126.72), LatLng(37.39, 126.74))
            "배곧2동" -> LatLngBounds(LatLng(37.35, 126.71), LatLng(37.38, 126.73))
            "신천동"   -> LatLngBounds(LatLng(37.43, 126.77), LatLng(37.45, 126.80))
            "연성동"   -> LatLngBounds(LatLng(37.38, 126.80), LatLng(37.40, 126.82))
            "은행동"   -> LatLngBounds(LatLng(37.42, 126.80), LatLng(37.44, 126.82))
            "장곡동"   -> LatLngBounds(LatLng(37.38, 126.78), LatLng(37.40, 126.79))
            "정왕1동" -> LatLngBounds(LatLng(37.33, 126.73), LatLng(37.35, 126.75))
            "정왕2동" -> LatLngBounds(LatLng(37.34, 126.71), LatLng(37.36, 126.73))
            "정왕3동" -> LatLngBounds(LatLng(37.34, 126.70), LatLng(37.36, 126.72))
            "정왕4동" -> LatLngBounds(LatLng(37.36, 126.73), LatLng(37.38, 126.75))
            "정왕본동" -> LatLngBounds(LatLng(37.35, 126.74), LatLng(37.37, 126.76))
            else       -> LatLngBounds(LatLng(37.32, 126.68), LatLng(37.48, 126.88))
        }
        mMap.setLatLngBoundsForCameraTarget(restrictionBounds)

        drawPolylinesFromTxt(dongName)

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

                findViewById<TextView>(R.id.dongdetailname).text = park.name

                val screenHeight = resources.displayMetrics.heightPixels
                val offset = screenHeight / 6
                val projection = mMap.projection
                val markerPoint = projection.toScreenLocation(marker.position)
                markerPoint.y += offset.toInt()

                val newLatLng = projection.fromScreenLocation(markerPoint)
                mMap.animateCamera(CameraUpdateFactory.newLatLng(newLatLng))
                marker.showInfoWindow()

                val halfHeight = resources.displayMetrics.heightPixels / 2
                bottomSheetBehavior.peekHeight = halfHeight
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

                Log.d("MAP", "✅ 바텀시트 열림: ${park.name}")
            } else {
                Log.d("MAP", "❌ 해당 마커에 대한 공원 정보 없음")
            }
            true
        }

        mMap.setOnInfoWindowClickListener {
            Log.d("MAP", "ℹ️ InfoWindow 클릭됨: ${it.title}")
        }
    }
}
