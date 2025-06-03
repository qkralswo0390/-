package com.example.project

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.databinding.ActivityJeongwangBinding
import com.example.project.databinding.ActivitySinhyeonBinding

class sinhyeon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySinhyeonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화
        supportActionBar?.setHomeButtonEnabled(true)


    }

    // 툴바 뒤로가기 버튼 눌렀을 때 SecondActivity로 돌아가기
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // 또는 finish()
        return true
    }
}