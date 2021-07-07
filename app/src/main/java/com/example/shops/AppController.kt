package com.example.shops

import android.app.Application
import com.example.shops.ui.shoplist.Shop
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

@HiltAndroidApp
class AppController : Application() {

    @Inject
    lateinit var database: FirebaseDatabase

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        Timber.d(database.toString())

       /* val data = database.getReference("hotels/hotels/0")

        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                Timber.d("DATA: ${p0.toString()}")
                val data = p0.getValue(Shop::class.java)
                Timber.d(data.toString())
            }

            override fun onCancelled(p0: DatabaseError) {
                Timber.e(p0.toString())
            }

        })*/

    }
}