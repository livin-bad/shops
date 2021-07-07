package com.example.hotel.data.service

import com.example.shops.utils.Resource
import com.example.shops.ui.shoplist.Shop
import kotlinx.coroutines.flow.Flow

interface HotelService {
    fun getShops(url: String): Flow<Resource<List<Shop>>>
    fun getShop(url: String): Flow<Resource<Shop>>
}