package com.example.myapplication.presentation.wpfragment

interface DelegateItem {
    fun content(): Any

    fun id(): Long

    fun compareToOther(other: DelegateItem): Boolean
}