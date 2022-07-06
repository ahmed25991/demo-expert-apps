package com.expertapps.demo.utils

import java.util.*

object AppUtils {

    fun getDaysDifference(fromDate: Date?, toDate: Date?): Int {
        return if (fromDate == null || toDate == null) 0
        else ((toDate.time - fromDate.time) / (1000 * 60 * 60 * 24)).toInt()
    }

}