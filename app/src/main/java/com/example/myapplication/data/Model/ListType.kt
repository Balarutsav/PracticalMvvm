package com.example.myapplication.data.Model

abstract class ListType {
    abstract val viewType: Int

    companion object {
        const val TYPE_DATE = 2
        const val TYPE_NORMAL = 3
    }
}