package com.example.myapplication


sealed class DataModel{

    data class Liked(
        //var url_liked: String = "",
        var title_liked: String = "",
        var discription_liked: String = ""
    ):DataModel()

    data class Categories(
        //var url_categories: String = "",
        var title_categories: String = ""
    ):DataModel()

    data class Header(
        var title_header: String = ""
    ):DataModel()

    data class Albums(
        //var url_albums: String = "",
        var title_albums: String = ""
    ):DataModel()


}
