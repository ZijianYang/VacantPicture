package com.yzj.vacantpicture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yzj.vacantpicture.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detail)
    }
}