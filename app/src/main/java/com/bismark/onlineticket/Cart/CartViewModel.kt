package com.bismark.onlineticket.Cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bismark.onlineticket.data_layer.entities.Cart
import com.bismark.onlineticket.di.RoomTicketProvider
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import javax.inject.Provider

class CartViewModel @Inject constructor(
    val ticketProvider: RoomTicketProvider
) : ViewModel(){
    private var _cartMutableLiveData: MutableLiveData<List<Cart>> = MutableLiveData()
    val cartLiveData: LiveData<List<Cart>> = _cartMutableLiveData

    suspend fun fetchAllCartItems() {
        ticketProvider.getCardDao().getAllCarts()
            .collect {
                _cartMutableLiveData.value = it
            }
    }
}

class CartViewModelFactory @Inject constructor(
    private val viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<out ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = viewModelMap[modelClass] ?: viewModelMap.asIterable().firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}