package com.bismark.onlineticket.Cart

import android.util.Log
import androidx.lifecycle.*
import com.bismark.onlineticket.data_layer.entities.CartWithTicket
import com.bismark.onlineticket.di.RoomTicketProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel constructor(
    private val cartProvider: RoomTicketProvider
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
            cartProvider.getCardDao().getAllCarts()
                .map { cartList ->
                    cartList.map {
                        val ticket = cartProvider.getTicketDao().getTicket(it.ticketId)
                        CartWithTicket(it, ticket)
                    }
                }.flowOn(Dispatchers.Default)
                .catch {
                    Log.d("Flow Error", it.message.toString())
                }
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
            val result = cartWithTicket.asFlow()
                .map { cart ->
                    cart.ticket.price * cart.cart.noOfTicket
                }
                .flowOn(Dispatchers.Default)
                .catch {
                    Log.d("Flow Subtotal Error", it.message.toString())
                }
                .reduce { accumulator, value ->
                    accumulator + value
                }.toFloat()

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
        return CartViewModel(cartProvider) as T
    }
}
