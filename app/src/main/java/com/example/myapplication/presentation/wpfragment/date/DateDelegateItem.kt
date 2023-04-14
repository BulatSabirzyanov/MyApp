package com.example.myapplication.presentation.wpfragment.date

import com.example.myapplication.presentation.wpfragment.DelegateItem

class DateDelegateItem(
    val id: Long,
    private val value: DateModel
) : DelegateItem {
    override fun content(): Any = value

    override fun id(): Long = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as DateDelegateItem).value == content()
    }

}