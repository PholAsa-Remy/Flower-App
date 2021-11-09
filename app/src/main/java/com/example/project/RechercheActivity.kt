package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.ActivityRechercheBinding

class RechercheActivity : AppCompatActivity() {

    lateinit var binding : ActivityRechercheBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRechercheBinding.inflate( layoutInflater )
        setContentView( binding.root)
    }
}