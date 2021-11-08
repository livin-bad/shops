package com.example.shops.ui.shoplist

import com.example.shops.utils.Resource
import com.example.hotel.data.service.HotelService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class ShopListRepository
@Inject
constructor(
    private val database: FirebaseDatabase,
) : HotelService {

    @ExperimentalCoroutinesApi
    override fun getShops(url: String): Flow<Resource<List<Shop>>> = callbackFlow {

        trySendBlocking(Resource.loading())

        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.sendBlocking(Resource.error(error.toString(), null))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list = mutableListOf<Shop>()
                for (dataSnapshot in dataSnapshot.children) {
                    Timber.d("KEY: ${dataSnapshot.key}")
                    val items = dataSnapshot.getValue(Shop::class.java)
                    items?.let {
                        it.id=dataSnapshot.key
                        list.add(it)

                    }
                }
                this@callbackFlow.trySendBlocking(Resource.success(list))
            }
        }

        database.getReference(url)
            .addValueEventListener(postListener)


        awaitClose {
            database.getReference(url)
                .removeEventListener(postListener)
        }
    }

    override fun getShop(url: String): Flow<Resource<Shop>> = callbackFlow {
        trySendBlocking(Resource.loading())

        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.sendBlocking(Resource.error(error.toString(), null))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue(Shop::class.java) ?: Shop()
                this@callbackFlow.trySendBlocking(Resource.success(data))
            }
        }

        database.getReference(url)
            .addValueEventListener(postListener)


        awaitClose {
            database.getReference(url)
                .removeEventListener(postListener)
        }
    }
}