package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController

import androidx.navigation.ui.setupWithNavController

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)
        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.regFragment -> {
                    // Скрываем bottomNavigationView
                    bottomNavigationView.visibility = View.GONE
                }
                R.id.loginFragment2 -> {
                    bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    // Показываем bottomNavigationView
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }


    }
}