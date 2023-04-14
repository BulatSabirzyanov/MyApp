package com.example.myapplication.presentation.wpfragment

import com.example.myapplication.presentation.wpfragment.date.DateDelegateItem
import com.example.myapplication.presentation.wpfragment.date.DateModel
import com.example.myapplication.presentation.wpfragment.weather.WeatherDelegateItem
import com.example.myapplication.presentation.wpfragment.weather.WeatherModel

fun List<WeatherModel>.concatenateWithDate(dates: List<DateModel>): List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    dates.forEach { dateModel ->
        delegateItemList.add(
            DateDelegateItem(id = dateModel.id, value = dateModel)
        )
        val date = dateModel.date
        val allDayWeathers = this.filter { weather ->
            weather.date == date
        }
        allDayWeathers.forEach { model ->
            delegateItemList.add(
                WeatherDelegateItem(
                    id = model.id,
                    value = model,
                )
            )
        }
    }
    return delegateItemList

}