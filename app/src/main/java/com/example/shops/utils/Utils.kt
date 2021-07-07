package com.example.shops.utils

import com.example.shops.ui.shoplist.Shop

object Utils {
    fun getShops(): List<Shop> {
        val list = mutableListOf<Shop>()

        list.add(Shop("Raja shop 1", status = false))
        list.add(Shop("Raja shop 2", status = true))
        list.add(Shop("Raja shop 3", status = false))
        list.add(Shop("Raja shop 4", status = true))
        list.add(Shop("Raja shop 5", status = false))
        list.add(Shop("Raja shop 6", status = true))
        list.add(Shop("Raja shop 7", status = false))
        list.add(Shop("Raja shop 8", status = true))
        list.add(Shop("Raja shop 9", status = true))
        list.add(Shop("Raja shop 10", status = true))
        list.add(Shop("Raja shop 11", status = true))
        list.add(Shop("Raja shop 12", status = true))
        list.add(Shop("Raja shop 13", status = true))

        return list
    }
}