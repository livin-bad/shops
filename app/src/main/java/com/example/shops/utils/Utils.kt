package com.example.shops.utils

import com.example.shops.ui.shoplist.Shop
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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

    fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }

    fun getTime(hourOfDay: Int, minute: Int): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(0, 0, 0, hourOfDay, minute, 0)
        val timeInMillis: Long = calendar.timeInMillis
        val dateFormatter: DateFormat = SimpleDateFormat("hh:mm a")
        val date = Date()
        date.time = timeInMillis
        return dateFormatter.format(date).replace("am", "AM").replace("pm", "PM")
    }
}