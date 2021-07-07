package com.example.shops.di

import com.example.hotel.data.service.HotelService
import com.example.shops.ui.shoplist.ShopListRepository
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance("https://hotel-85823-default-rtdb.firebaseio.com/")
    }

    @Singleton
    @Provides
    fun provideHotelService(
        firebaseDatabase: FirebaseDatabase
    ):HotelService{
        return ShopListRepository(firebaseDatabase)
    }

}