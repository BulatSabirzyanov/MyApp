package com.example.myapplication.presentation.wpfragment.weather

import com.example.myapplication.presentation.wpfragment.DelegateItem


class WeatherDelegateItem(
    val id: Long,
    private val value: WeatherModel
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Long = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as WeatherDelegateItem).value == content()
    }
}