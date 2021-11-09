package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.ActivityRecallBinding

class RecallActivity : AppCompatActivity() {

    lateinit var binding : ActivityRecallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecallBinding.inflate( layoutInflater )
        setContentView( binding.root)
    }
}