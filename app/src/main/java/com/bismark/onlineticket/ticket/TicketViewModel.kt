package com.bismark.onlineticket.ticket

import androidx.lifecycle.*
import com.bismark.onlineticket.data_layer.entities.Cart
import com.bismark.onlineticket.data_layer.entities.Ticket
import com.bismark.onlineticket.di.RoomTicketProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TicketViewModel constructor(
    private val ticketProvider: RoomTicketProvider
) : ViewModel() {

    private var _ticketMutableLiveData: MutableLiveData<List<Ticket>> = MutableLiveData()
    val ticketLiveData: LiveData<List<Ticket>> = _ticketMutableLiveData

    fun fetchAllTicketItems() {
        viewModelScope.launch {
            ticketProvider.getTicketDao().getAllTicket()
                .flowOn(Dispatchers.Default)
                .collect {
                    _ticketMutableLiveData.value = it
                }
        }
    }

    fun addToCart(ticket: Ticket, noTicket: Int) {
        viewModelScope.launch{
            withContext(Dispatchers.Default){
                val cart = Cart(
                    noOfTicket = noTicket,
                    ticketId = ticket.ticketId
                )

                ticketProvider.getCardDao().insertCart(cart)
            }
        }
    }
}

class TicketViewModelFactory constructor(
    private val cartProvider: RoomTicketProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TicketViewModel(cartProvider) as T
    }

}
