package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root)

        binding.recallbutton.setOnClickListener(){
            var goToRecall : Intent = Intent (this, RecallActivity:: class.java)
            startActivity(goToRecall)
        }

        binding.recherchebutton.setOnClickListener(){
            var goToResearch : Intent = Intent (this, ResearchActivity:: class.java)
            startActivity(goToResearch)
        }

    }

}