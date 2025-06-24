package com.example.project

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.google.gson.GsonBuilder
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class DongDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var dongName: String
    private var summaryText: String = ""
    private var detailText: String = ""
    private var isExpanded = false


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

    private val stationMap = mapOf(
        "정왕1동" to "정왕동",
        "정왕2동" to "정왕동",
        "정왕3동" to "정왕동",
        "정왕4동" to "정왕동",
        "정왕본동" to "정왕동",
        "군자동" to "군자동",
        "신천동" to "신천동",
        "대야동" to "대야동",
        "은행동" to "대야동",
        "과림동" to "대야동",
        "배곧1동" to "정왕동",
        "배곧2동" to "정왕동",
        "배곧3동" to "정왕동"
    )

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

        val center = dongCenters[dongName] ?: LatLng(37.336, 126.729)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 14f))
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
            val marker = mMap.addMarker(MarkerOptions().position(park.latLng).title(park.name))
            marker?.tag = park.name
        }

        val parkImage = findViewById<ImageView>(R.id.parkImage)
        val parkInfo = findViewById<TextView>(R.id.parkInfo)
        val weatherInfo = findViewById<TextView>(R.id.weatherInfo)

        weatherInfo.text = ""
        fetchWeatherData(center.latitude, center.longitude)

        mMap.setOnMarkerClickListener { marker ->
            val park = parkMarkers.find { it.name == marker.tag }
            if (park != null) {
                parkImage.setImageResource(park.imageResId)

                val infoText = """
                    📍 이름: ${park.name}
                    🏞️ 주소: ${park.address}
                    ℹ️ 설명: ${park.description}
                """.trimIndent()
                parkInfo.text = infoText

                val mapView = (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).view
                mapView?.post {
                    val screenHeight = resources.displayMetrics.heightPixels
                    val offset = screenHeight / 6
                    val projection = mMap.projection
                    val markerPoint = projection.toScreenLocation(marker.position)
                    markerPoint.y += offset
                    val newLatLng = projection.fromScreenLocation(markerPoint)
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(newLatLng))
                }
                bottomSheetBehavior.peekHeight = resources.displayMetrics.heightPixels / 2
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            false
        }
        findViewById<TextView>(R.id.weatherInfo).setOnClickListener {
            isExpanded = !isExpanded
            it as TextView
            it.text = if (isExpanded) detailText else summaryText
        }

    }


    private fun fetchWeatherData(lat: Double, lon: Double) {
        val weatherInfo = findViewById<TextView>(R.id.weatherInfo)
        weatherInfo.text = "날씨 정보를 불러오는 중..."

        val (nx, ny) = convertToGrid(lat, lon)

        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val api = retrofit.create(WeatherApiService::class.java)

        val cal = Calendar.getInstance()
        if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) < 40) {
            cal.add(Calendar.DATE, -1)
        }
        val baseDate = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(cal.time)
        val baseTime = getBaseTime()

        val call = api.getNowcast(
            serviceKey = "B9+4J6Hkv4PRG2uoZqM6RE/35a6Cwt2n6/u2Szy2+mk+PlBwF807+yHwXlemkjW4iVx2E8W7XVEuKZoZAQeD6Q==",
            baseDate = baseDate,
            baseTime = baseTime,
            nx = nx,
            ny = ny
        )

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val items = response.body()?.response?.body?.items?.item
                if (items.isNullOrEmpty()) {
                    weatherInfo.text = "날씨 정보 없음"
                    return
                }

                var temp = ""
                var humid = ""
                var rainType = ""
                var wind = ""

                for (item in items) {
                    when (item.category) {
                        "T1H" -> temp = "${item.obsrValue}℃"
                        "REH" -> humid = "${item.obsrValue}%"
                        "PTY" -> {
                            rainType = when (item.obsrValue) {
                                "0" -> "없음"
                                "1" -> "비"
                                "2" -> "비/눈"
                                "3" -> "눈"
                                "4" -> "소나기"
                                else -> "알 수 없음"
                            }
                        }
                        "WSD" -> wind = "${item.obsrValue}m/s"
                    }
                }

                val summary = "🌡️ 기온: $temp"
                val detail = "🌡️ 기온: $temp\n💧 습도: $humid\n☔ 강수: $rainType\n🍃 풍속: $wind"

                summaryText = summary
                detailText = detail
                weatherInfo.text = summary

                val stationName = stationMap[dongName] ?: "정왕동"
                fetchAirQualityData(stationName, weatherInfo)
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                weatherInfo.text = "날씨 요청 실패"
            }
        })
    }

    private fun fetchAirQualityData(stationName: String, weatherInfo: TextView) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(AirQualityApiService::class.java)

        val call = api.getAirQuality(
            serviceKey = "B9+4J6Hkv4PRG2uoZqM6RE/35a6Cwt2n6/u2Szy2+mk+PlBwF807+yHwXlemkjW4iVx2E8W7XVEuKZoZAQeD6Q==",
            stationName = stationName,
            dataTerm = "DAILY",
            returnType = "json",
            numOfRows = 1,
            pageNo = 1
        )

        call.enqueue(object : Callback<AirQualityResponse> {
            override fun onResponse(call: Call<AirQualityResponse>, response: Response<AirQualityResponse>) {
                val item = response.body()?.response?.body?.items?.firstOrNull()
                if (item != null) {
                    val pm10 = item.pm10Value?.toIntOrNull()
                    val pm25 = item.pm25Value?.toIntOrNull()

                    val pm10Status = when {
                        pm10 == null -> "정보 없음"
                        pm10 <= 30 -> "좋음"
                        pm10 <= 80 -> "보통"
                        pm10 <= 150 -> "나쁨"
                        else -> "매우 나쁨"
                    }

                    val pm25Status = when {
                        pm25 == null -> "정보 없음"
                        pm25 <= 15 -> "좋음"
                        pm25 <= 35 -> "보통"
                        pm25 <= 75 -> "나쁨"
                        else -> "매우 나쁨"
                    }

                    detailText += """
                        
- 미세먼지: ${pm10 ?: "정보 없음"}㎍/㎥ ($pm10Status)
""".trimIndent()

// 현재 화면에 표시 중이라면 update
                    if (isExpanded) {
                        weatherInfo.text = detailText
                    }
                } else {
                    weatherInfo.append("\n\n🌫️ 미세먼지 정보 없음")
                }
            }

            override fun onFailure(call: Call<AirQualityResponse>, t: Throwable) {
                weatherInfo.append("\n\n🌫️ 미세먼지 요청 실패")
            }
        })
    }

    private fun getBaseTime(): String {
        val cal = Calendar.getInstance()
        if (cal.get(Calendar.MINUTE) < 40) cal.add(Calendar.HOUR_OF_DAY, -1)
        return SimpleDateFormat("HHmm", Locale.KOREA).format(cal.time)
    }

    private fun convertToGrid(lat: Double, lon: Double): Pair<Int, Int> {
        val RE = 6371.00877
        val GRID = 5.0
        val SLAT1 = 30.0
        val SLAT2 = 60.0
        val OLON = 126.0
        val OLAT = 38.0
        val XO = 43
        val YO = 136

        val DEGRAD = Math.PI / 180.0
        val re = RE / GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olon = OLON * DEGRAD
        val olat = OLAT * DEGRAD

        val sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        val sn2 = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn)
        val sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        val sf2 = Math.pow(sf, sn2) * Math.cos(slat1) / sn2
        val ro = Math.tan(Math.PI * 0.25 + olat * 0.5)
        val ro2 = re * sf2 / Math.pow(ro, sn2)

        val ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5)
        val ra2 = re * sf2 / Math.pow(ra, sn2)
        var theta = lon * DEGRAD - olon
        if (theta > Math.PI) theta -= 2.0 * Math.PI
        if (theta < -Math.PI) theta += 2.0 * Math.PI
        theta *= sn2

        val x = ra2 * Math.sin(theta) + XO + 0.5
        val y = ro2 - ra2 * Math.cos(theta) + YO + 0.5
        return Pair(x.toInt(), y.toInt())
    }

    companion object {
        val dongCenters: Map<String, LatLng> by lazy {
            parksByDong.mapValues { entry ->
                val parks = entry.value
                if (parks.isNotEmpty()) {
                    val avgLat = parks.map { it.latLng.latitude }.average()
                    val avgLng = parks.map { it.latLng.longitude }.average()
                    LatLng(avgLat, avgLng)
                } else {
                    LatLng(37.336, 126.729)
                }
            }
        }
    }
}
