package com.example.shops.ui.shoplist

import androidx.lifecycle.*
import com.example.shops.utils.Resource
import com.example.hotel.data.service.HotelService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


/**
 * @author bsoft-61 on 15/2/21.
 * */
@HiltViewModel
class ShopListViewModel
@Inject
constructor(
    private val repository: HotelService,
) : ViewModel() {


    private val _dataStateShops: MutableLiveData<Resource<List<Shop>>> = MutableLiveData()

    val dataStateShops: LiveData<Resource<List<Shop>>>
        get() = _dataStateShops

    fun setStateShops(url: String) {
        viewModelScope.launch {
            repository.getShops(url)
                .onEach { dataState ->
                    Timber.d("DATA: $dataState")
                    _dataStateShops.value = dataState
                }
                .launchIn(viewModelScope)

            /* repository.getShops(url).collect {
                 Timber.d("DATA: $it")
             }*/
        }
    }

    private val _dataStateShop: MutableLiveData<Resource<Shop>> = MutableLiveData()

    val dataStateShop: LiveData<Resource<Shop>>
        get() = _dataStateShop

    fun setStateShop(url: String) {
        viewModelScope.launch {
            repository.getShop(url)
                .onEach { dataState ->
                    Timber.d("DATA: $dataState")
                    _dataStateShop.value = dataState
                }
                .launchIn(viewModelScope)

            /* repository.getShops(url).collect {
                 Timber.d("DATA: $it")
             }*/
        }
    }


}