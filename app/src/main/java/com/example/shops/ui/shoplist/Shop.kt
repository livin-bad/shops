package com.example.shops.ui.shoplist

data class Shop(
    val name: String? = null,
    val time: List<TimeShop>? = null,
    val status: Boolean?=null
) {
    data class TimeShop(
        val from: String? = null,
        val to: String? = null
    )
}
