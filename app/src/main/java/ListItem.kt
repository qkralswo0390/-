package com.example.project

data class ListItem(
    val title: String,
    val imageResId: Int? = null  // 이미지 리소스 아이디, 없으면 null 허용
)