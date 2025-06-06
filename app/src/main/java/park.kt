package com.example.project

import com.google.android.gms.maps.model.LatLng

data class Park(
    val name: String,
    val latLng: LatLng,
    val imageResId: Int,
    val address: String? = null,
    val description: String? = null
)

// 각 동별 공원 리스트를 담은 Map
val parksByDong: Map<String, List<Park>> = mapOf(
    "정왕1동" to listOf(
        Park("소망공원", LatLng(37.333493, 126.725643), R.drawable.intersect, address = "정왕동 1368-37", description = "놀이터, 벤치"),
        Park("새싹공원", LatLng(37.340062, 126.7512), R.drawable.intersect, address = "정왕동 1412-9", description = "놀이터, 벤치"),
        Park("꿈동산공원", LatLng(37.342427, 126.748022), R.drawable.intersect, address = "정왕동 1569-10", description = "놀이터, 벤치"),
        Park("군서공원", LatLng(37.344543, 126.74559), R.drawable.intersect, address = "정왕동 1581", description = "놀이터, 벤치"),
        Park("젊음과패기공원", LatLng(37.348403, 126.74004), R.drawable.intersect, address = "정왕동 1800-5", description = "놀이터, 벤치"),
        Park("평안공원", LatLng(37.339471, 126.741545), R.drawable.intersect, address = "정왕동 1787-4", description = "놀이터, 벤치")
    ),

    "정왕2동" to listOf(
        Park("희망공원", LatLng(37.337075, 126.714717), R.drawable.intersect, address = "정왕동 1358", description = "놀이터, 벤치"),
        Park("서촌공원", LatLng(37.348583, 126.726767), R.drawable.intersect, address = "정왕동 1848-2", description = "놀이터, 벤치"),
        Park("중앙공원", LatLng(37.349188, 126.735365), R.drawable.intersect, address = "정왕동 1845-1", description = "놀이터, 벤치"),
        Park("사랑공원", LatLng(37.355439, 126.733894), R.drawable.intersect, address = "정왕동 1872-7", description = "놀이터, 벤치"),
        Park("시화2공원", LatLng(37.357594, 126.733953), R.drawable.intersect, address = "정왕동 1872-1", description = "놀이터, 벤치"),
        Park("송운공원", LatLng(37.358441, 126.732652), R.drawable.intersect, address = "정왕동 1873-1", description = "놀이터, 벤치")
    ),

    "정왕3동" to listOf(
        Park("오이도공원", LatLng(37.341666, 126.689871), R.drawable.intersect, address = "정왕동 2040-1", description = "놀이터, 벤치"),
        Park("옥터공원", LatLng(37.346452, 126.689584), R.drawable.intersect, address = "정왕동 1998-5", description = "놀이터, 벤치"),
        Park("옥구공원", LatLng(37.355604, 126.712434), R.drawable.intersect, address = "정왕동 2138", description = "놀이터, 벤치"),
        Park("동보근린공원", LatLng(37.355131, 126.716451), R.drawable.intersect, address = "정왕동 1942-3", description = "놀이터, 벤치"),
        Park("서해공원", LatLng(37.354779, 126.724714), R.drawable.intersect, address = "정왕동 1867-4", description = "놀이터, 벤치")
    ),

    "정왕4동" to listOf(
        Park("풀잎사랑공원", LatLng(37.358792, 126.729368), R.drawable.intersect, address = "정왕동 1875-6", description = "놀이터, 벤치"),
        Park("온들녘공원", LatLng(37.3611, 126.73153), R.drawable.intersect, address = "정왕동 1876-5", description = "놀이터, 벤치"),
        Park("함송공원", LatLng(37.363484, 126.734222), R.drawable.intersect, address = "정왕동 1877-6", description = "놀이터, 벤치"),
        Park("함현공원", LatLng(37.365477, 126.732959), R.drawable.intersect, address = "정왕동 1878-3", description = "놀이터, 벤치"),
        Park("함현공원", LatLng(37.365859, 126.73588), R.drawable.intersect, address = "정왕동 1878-6", description = "놀이터, 벤치")
    ),
    "대야동" to listOf(
        Park("은계숲생태공원", LatLng(37.449142, 126.800716), R.drawable.intersect, address = "경기 시흥시 대야동 642-190", description = "놀이터, 벤치"),
        Park("은행근린공원", LatLng(37.444325, 126.795564), R.drawable.intersect, address = "경기 시흥시 대야동 565", description = "놀이터, 벤치"),
        Park("생매공원", LatLng(37.446912, 126.794256), R.drawable.intersect, address = "경기 시흥시 대야동 570-3", description = "놀이터, 벤치"),
        Park("한마음건강공원", LatLng(37.454337, 126.797469), R.drawable.intersect, address = "대야동 263-16", description = "놀이터, 벤치")
    ),
    "군자동" to listOf(
        Park("산들공원", LatLng(37.347416, 126.780254), R.drawable.intersect, address = "경기 시흥시 거모동 1356", description = "놀이터, 벤치"),
        Park("태봉공원", LatLng(37.342271, 126.786762), R.drawable.intersect, address = "경기 시흥시 거모동 1768-5", description = "놀이터, 벤치"),
        Park("거모공원", LatLng(37.343027, 126.784263), R.drawable.intersect, address = "경기 시흥시 거모동 1754-1", description = "놀이터, 벤치"),
        Park("한우물공원", LatLng(37.345121, 126.783356), R.drawable.intersect, address = "경기 시흥시 거모동 1735-3", description = "놀이터, 벤치"),
        Park("내곡공원", LatLng(37.348274, 126.783457), R.drawable.intersect, address = "경기 시흥시 거모동 1722-5", description = "놀이터, 벤치"),
        Park("석곡공원", LatLng(37.344169, 126.780996), R.drawable.intersect, address = "경기 시흥시 거모동 1739-1", description = "놀이터, 벤치"),
        Park("안골공원", LatLng(37.349135, 126.785764), R.drawable.intersect, address = "경기 시흥시 거모동 1715-1", description = "놀이터, 벤치"),
        Park("도일공원", LatLng(37.340189, 126.78568), R.drawable.intersect, address = "거모동 1775-2", description = "놀이터, 벤치"),
        Park("망고개소공원", LatLng(37.342688, 126.789098), R.drawable.intersect, address = "거모동 1616", description = "놀이터, 벤치")
    ),
    "능곡동" to listOf(
        Park("능곡선사유적공원", LatLng(37.375075, 126.811564), R.drawable.intersect, address = "경기 시흥시 능곡동 479", description = "놀이터, 벤치"),
        Park("영모재공원", LatLng(37.369542, 126.816061), R.drawable.intersect, address = "경기 시흥시 능곡동 617", description = "놀이터, 벤치"),
        Park("중앙공원", LatLng(37.367999, 126.813835), R.drawable.intersect, address = "경기 시흥시 능곡동 766", description = "놀이터, 벤치"),
        Park("능골공원", LatLng(37.365745, 126.808291), R.drawable.intersect, address = "경기 시흥시 능곡동 781", description = "놀이터, 벤치"),
        Park("숲새재공원", LatLng(37.372359, 126.806165), R.drawable.intersect, address = "경기 시흥시 능곡동 865", description = "놀이터, 벤치"),
        Park("승지공원", LatLng(37.371806, 126.812811), R.drawable.intersect, address = "경기 시흥시 능곡동 552", description = "놀이터, 벤치"),
        Park("실개울공원", LatLng(37.365706, 126.810481), R.drawable.intersect, address = "경기 시흥시 능곡동 777", description = "놀이터, 벤치"),
        Park("가래울공원", LatLng(37.365115, 126.813811), R.drawable.intersect, address = "경기 시흥시 능곡동 793", description = "놀이터, 벤치"),
        Park("목실공원", LatLng(37.373747, 126.813164), R.drawable.intersect, address = "경기 시흥시 능곡동 495", description = "놀이터, 벤치")
    ),
    "신천동" to listOf(
        Park("신천근린공원", LatLng(37.435809, 126.785492), R.drawable.intersect, address = "경기 시흥시 신천동 57", description = "놀이터, 벤치"),
        Park("백년정원", LatLng(37.432806, 126.786706), R.drawable.intersect, address = "경기 시흥시 신천동 178-2", description = "놀이터, 벤치"),
        Park("백제공원", LatLng(37.432215, 126.792258), R.drawable.intersect, address = "경기 시흥시 신천동 884-5", description = "놀이터, 벤치"),
        Park("신라공원", LatLng(37.440903, 126.781711), R.drawable.intersect, address = "경기 시흥시 신천동 742", description = "놀이터, 벤치"),
        Park("신일공원", LatLng(37.431115, 126.790706), R.drawable.intersect, address = "경기 시흥시 신천동 890-1", description = "놀이터, 벤치"),
        Park("현진공원", LatLng(37.430953, 126.790652), R.drawable.intersect, address = "경기 시흥시 신천동 775", description = "놀이터, 벤치"),
        Park("둥지공원", LatLng(37.436691, 126.788848), R.drawable.intersect, address = "경기 시흥시 신천동 857-1", description = "놀이터, 벤치"),
        Park("산호어린이공원", LatLng(37.433212, 126.789336), R.drawable.intersect, address = "경기 시흥시 신천동 870-1", description = "놀이터, 벤치"),
        Park("포도원어린이공원", LatLng(37.435484, 126.786291), R.drawable.intersect, address = "경기 시흥시 신천동 851-7", description = "놀이터, 벤치"),
        Park("도원어린이공원", LatLng(37.438512, 126.777387), R.drawable.intersect, address = "경기 시흥시 신천동 818-1", description = "놀이터, 벤치"),
        Park("복은자리어린이공원", LatLng(37.435676, 126.790816), R.drawable.intersect, address = "경기 시흥시 신천동 947", description = "놀이터, 벤치"),
        Park("상아어린이공원", LatLng(37.436338, 126.777555), R.drawable.intersect, address = "경기 시흥시 신천동 826-1", description = "놀이터, 벤치")
    ),
    "은행동" to listOf(
        Park("비둘기공원", LatLng(37.441647, 126.794117), R.drawable.intersect, address = "경기 시흥시 은행동 551", description = "놀이터, 벤치"),
        Park("은계중앙공원", LatLng(37.441445, 126.800305), R.drawable.intersect, address = "경기 시흥시 은행동 55", description = "놀이터, 벤치"),
        Park("웃터골공원", LatLng(37.44457, 126.801125), R.drawable.intersect, address = "경기 시흥시 은행동 600", description = "놀이터, 벤치"),
        Park("은행공원", LatLng(37.442524, 126.798573), R.drawable.intersect, address = "경기 시흥시 은행동 538-4", description = "놀이터, 벤치"),
        Park("은행근린공원", LatLng(37.441638, 126.793978), R.drawable.intersect, address = "경기 시흥시 은행동 551", description = "놀이터, 벤치"),
        Park("오난산전망공원", LatLng(37.442107, 126.806472), R.drawable.intersect, address = "경기 시흥시 은행동 622", description = "놀이터, 벤치"),
        Park("은계호수공원", LatLng(37.444502, 126.807189), R.drawable.intersect, address = "경기 시흥시 은행동 601-150", description = "놀이터, 벤치"),
        Park("검바위하늘공원", LatLng(37.434054, 126.800038), R.drawable.intersect, address = "경기 시흥시 은행동 660", description = "놀이터, 벤치")
    ),
    "배곧1동" to listOf(
        Park("배곧생명공원", LatLng(37.371905, 126.722186), R.drawable.intersect, address = "경기 시흥시 배곧동 187", description = "놀이터, 벤치"),
        Park("물빛공원", LatLng(37.375285, 126.734579), R.drawable.intersect, address = "경기 시흥시 배곧동 62", description = "놀이터, 벤치"),
        Park("숲속향기공원", LatLng(37.382848, 126.733052), R.drawable.intersect, address = "경기 시흥시 배곧동 11", description = "놀이터, 벤치")
    ),
    "배곧2동" to listOf(
        Park("배곧한울공원", LatLng(37.356174, 126.703721), R.drawable.intersect, address = "경기 시흥시 배곧동 307", description = "놀이터, 벤치")
    ),
    "거북섬동" to listOf(
        Park("나래솔근린공원", LatLng(37.323435, 126.704199), R.drawable.intersect, address = "정왕동 2606-3", description = "놀이터, 벤치"),
        Park("해미공원", LatLng(37.324815, 126.697120), R.drawable.intersect, address = "정왕동 2647", description = "놀이터, 벤치"),
        Park("오이도기념공원", LatLng(37.335034, 126.690486), R.drawable.intersect, address = "정왕동 2202-6", description = "놀이터, 벤치")
    ),
    "장곡동" to listOf(
        Park("가온근린공원", LatLng(37.378251, 126.793102), R.drawable.intersect, address = "장곡동 938-1", description = "놀이터, 벤치"),
        Park("갯골생태공원", LatLng(37.391035, 126.780835), R.drawable.intersect, address = "경기 시흥시 장곡동 724-32", description = "놀이터, 벤치"),
        Park("매꼴공원", LatLng(37.381291, 126.780672), R.drawable.intersect, address = "경기 시흥시 장곡동 798-2", description = "놀이터, 벤치"),
        Park("진말공원", LatLng(37.378575, 126.787602), R.drawable.intersect, address = "경기 시흥시 장곡동 825-2", description = "놀이터, 벤치"),
        Park("장곡공원", LatLng(37.378757, 126.783949), R.drawable.intersect, address = "경기 시흥시 장곡동 815", description = "놀이터, 벤치")
    ),
    "연성동" to listOf(
        Park("하상공원", LatLng(37.391960, 126.814300), R.drawable.intersect, address = "경기 시흥시 하상동 372", description = "놀이터, 벤치"),
        Park("연꽃공원", LatLng(37.395886, 126.810959), R.drawable.intersect, address = "경기 시흥시 하상동 367", description = "놀이터, 벤치"),
        Park("샛말공원", LatLng(37.396032, 126.804400), R.drawable.intersect, address = "경기 시흥시 하중동 848", description = "놀이터, 벤치"),
        Park("성마루공원", LatLng(37.395930, 126.804379), R.drawable.intersect, address = "경기 시흥시 하중동 880-2", description = "놀이터, 벤치"),
        Park("관곡공원", LatLng(37.399719, 126.800639), R.drawable.intersect, address = "경기 시흥시 하중동 826-2", description = "놀이터, 벤치"),
        Park("연꽃테마파크", LatLng(37.400841, 126.808355), R.drawable.intersect, address = "경기 시흥시 하중동 271", description = "놀이터, 벤치")
    ),
    "목감동" to listOf(
        Park("목감공원", LatLng(37.385607, 126.861246), R.drawable.intersect, address = "경기 시흥시 솔고개길 43", description = "놀이터, 벤치"),
        Park("꿈자람공원", LatLng(37.419125, 126.791786), R.drawable.intersect, address = "경기 시흥시 미산동 산12-6", description = "놀이터, 벤치")
    ),
    "정왕본동" to listOf(
        Park("정왕1공원", LatLng(37.357191, 126.752204), R.drawable.intersect, address = "정왕동 2246-1", description = "놀이터, 벤치"),
        Park("정왕2공원", LatLng(37.358785, 126.754957), R.drawable.intersect, address = "정왕동 2253-4", description = "놀이터, 벤치"),
        Park("정왕5공원", LatLng(37.352073, 126.743826), R.drawable.intersect, address = "정왕동 2322-3", description = "놀이터, 벤치"),
        Park("정왕6공원", LatLng(37.352791, 126.745694), R.drawable.intersect, address = "정왕동 2312-4", description = "놀이터, 벤치"),
        Park("정왕4공원", LatLng(37.353742, 126.743008), R.drawable.intersect, address = "정왕동 2307-3", description = "놀이터, 벤치"),
        Park("정왕3공원", LatLng(37.35316, 126.74028), R.drawable.intersect, address = "정왕동 2299-4", description = "놀이터, 벤치"),
        Park("연못공원", LatLng(37.348531, 126.744752), R.drawable.intersect, address = "정왕동 1825-2", description = "놀이터, 벤치"),
        Park("시화1공원", LatLng(37.348111, 126.748372), R.drawable.intersect, address = "정왕동 876-131", description = "놀이터, 벤치"),
        Park("무지개공원", LatLng(37.346835, 126.746951), R.drawable.intersect, address = "정왕동 1207-1", description = "놀이터, 벤치"),
        Park("달공원", LatLng(37.34534, 126.754851), R.drawable.intersect, address = "정왕동 1504-2", description = "놀이터, 벤치"),
        Park("해공원", LatLng(37.341774, 126.755996), R.drawable.intersect, address = "정왕동 1542-5", description = "놀이터, 벤치"),
        Park("별공원", LatLng(37.34574, 126.751036), R.drawable.intersect, address = "정왕동 1192-1", description = "놀이터, 벤치"),
        Park("큰솔공원", LatLng(37.343981, 126.75395), R.drawable.intersect, address = "정왕동 1512", description = "놀이터, 벤치"),
        Park("죽율체육공원", LatLng(37.349045, 126.76007), R.drawable.intersect, address = "죽율동 610", description = "놀이터, 벤치")
    ),
)
