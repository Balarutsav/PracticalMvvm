package com.example.myapplication.data

import com.example.myapplication.data.Model.ListType

data class DateModel(val date:String,
                     override val viewType: Int = TYPE_DATE
):ListType()
