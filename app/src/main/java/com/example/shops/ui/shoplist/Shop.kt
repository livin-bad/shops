package com.example.shops.ui.shoplist

data class Shop(
    var id: String?=null,
    val name: String? = null,
    val image: String?=null,
    val time: List<TimeShop>? = null,
    val status: Boolean?=null
) {
    data class TimeShop(
        val from: String? = null,
        val to: String? = null
    )
}
