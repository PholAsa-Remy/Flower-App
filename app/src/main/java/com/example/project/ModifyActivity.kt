package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.databinding.ActivityModifyBinding
import com.example.project.databinding.ActivityRechercheBinding

class ModifyActivity : AppCompatActivity() {

    lateinit var binding : ActivityModifyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyBinding.inflate( layoutInflater )
        setContentView( binding.root)

        val receivedIntent = intent
        binding.flowername.setText(receivedIntent.getStringExtra("name"))
    }
}