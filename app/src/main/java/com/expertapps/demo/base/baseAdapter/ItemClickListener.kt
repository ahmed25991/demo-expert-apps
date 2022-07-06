package com.expertapps.demo.base.baseAdapter

interface ItemClickListener {
    fun <T> onItemClick(item: T,type:Int?=null)
     // fun <T> onItemClick(item: T,type:Int)
}