package com.bismark.onlineticket.Cart

import androidx.lifecycle.*
import com.bismark.onlineticket.data_layer.entities.CartWithTicket
import com.bismark.onlineticket.di.RoomTicketProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    private var _cartMutableLiveData: MutableLiveData<List<CartWithTicket>> = MutableLiveData()
    val cartLiveData: LiveData<List<CartWithTicket>> = _cartMutableLiveData

    private var _noCartItemMutableLiveData: MutableLiveData<Unit> = MutableLiveData()
    val noCartItemiveData: LiveData<Unit> = _noCartItemMutableLiveData

    private var _subtotalCost: MutableLiveData<Float> = MutableLiveData()
    val subtotalCostLivaData: LiveData<Float> = _subtotalCost

    val cartWithTicketList = mutableListOf<CartWithTicket>()

    fun fetchAllCartItems() {
        viewModelScope.launch {
            cartRepository.getAllCartItemsFromLocal()
                .collect {
                    cartWithTicketList.addAll(it)
                    calculateSubtotal(cartWithTicketList)
                    _cartMutableLiveData.value = it
                }
        }
    }

    fun calculateSubtotal(cartWithTicket: List<CartWithTicket>) {
        viewModelScope.launch {
            calculate(cartWithTicket)
        }
    }

    private suspend fun calculate(cartWithTicket: List<CartWithTicket>) {
        if (cartWithTicket.isNotEmpty()) {
            val result = cartRepository.reduceCartListToTotal(cartWithTicket)
            _subtotalCost.value = result
        } else {
            _noCartItemMutableLiveData.value = Unit
        }
    }
}

class CartViewModelFactory constructor(
    private val cartProvider: RoomTicketProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val cartRepository = CartRepository(cartProvider, Dispatchers.Default)
        return CartViewModel(cartRepository) as T
    }
}
