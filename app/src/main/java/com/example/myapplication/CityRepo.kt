package com.example.myapplication

object CityRepo {
    val names = arrayListOf(
        "Toronto","Moscow","Chicago","London","Sidney"
        ,"Beijing","Kazan","Paris","Madrid","Berlin",
        "Liverpool","Munich","Dubai","Oslo","Marsel"
    )
    val citys = arrayListOf<City>().apply {
        for (id in 1..15) {
           add(City(id = id , name = names[id - 1]))
        }
    }
    var sortedByNameAscending = true
    var sortedByIdAscending = true

}