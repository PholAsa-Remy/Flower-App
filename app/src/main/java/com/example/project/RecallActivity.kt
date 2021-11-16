package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.databinding.ActivityMainBinding
import com.example.project.databinding.ActivityRecallBinding

class RecallActivity : AppCompatActivity() {

    lateinit var binding : ActivityRecallBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecallBinding.inflate( layoutInflater )
        setContentView( binding.root)

        binding.recycler.hasFixedSize()
        //TODO : Changer ici avec la liste des plantes a arroser
        var list : MutableList<String> = mutableListOf<String>()
        list.add("Rose")
        list.add("Tullippe")
        list.add("Cabracan")
        //Fin Du changement
        var adapter = RecallRecycledAdapter(list)
        binding.recycler.hasFixedSize() /* pour améliorer les pérformances*/
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter
    }
}