package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.DataAdapter

import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private var dataAdapter: DataAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataAdapter = DataAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = dataAdapter


        }
        dataAdapter?.setItems(mockData())
    }
    private fun mockData(): List<DataModel> = listOf(
        DataModel.Liked(title_liked = "Мне нравится", discription_liked = "307 треков - 17 часов"),
        DataModel.Categories(title_categories = "Плейлисты"),
        DataModel.Categories(title_categories = "Треки"),
        DataModel.Categories(title_categories = "Альбомы"),
        DataModel.Categories(title_categories = "Исполнители"),
        DataModel.Categories(title_categories = "Подкасты и книги"),
        DataModel.Categories(title_categories = "Скачанные треки"),
        DataModel.Categories(title_categories = "Треки с устройства"),
        DataModel.Categories(title_categories = "Детям"),
        DataModel.Header(title_header = "Вы недавно слушали"),
        DataModel.Albums(title_albums = "Макс Корж"),
        DataModel.Albums(title_albums = "Logic"),
        DataModel.Albums(title_albums = "Bones"),
        DataModel.Albums(title_albums = "Макс Краш"),

    )




}