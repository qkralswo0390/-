package com.example.project

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ActivityJeongwangBinding

class jeongwang : AppCompatActivity() {

    private lateinit var binding: ActivityJeongwangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJeongwangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // 1) 새로운 리스트 생성
        val newList = listOf(
            ListItem("토비공원"),
            ListItem("정왕역"),
            ListItem("아파트 단지"),
            ListItem("공원 산책로"),
        )

        // 2) 리사이클러뷰 레이아웃 매니저 설정
        binding.itemList.layoutManager = LinearLayoutManager(this)

        // 3) 어댑터 생성 & 아이템 클릭 이벤트 정의
        val adapter = ItemAdapter(newList) { item ->
            Toast.makeText(this, "${item.title} 클릭됨", Toast.LENGTH_SHORT).show()
        }

        // 4) 어댑터 연결
        binding.itemList.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}

