package com.bismark.onlineticket.ticket

import androidx.lifecycle.*
import com.bismark.onlineticket.data_layer.entities.Ticket
import com.bismark.onlineticket.di.RoomTicketProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TicketViewModel constructor(
    private val ticketRepository: TicketRepository,
) : ViewModel() {

    private var _ticketMutableLiveData: MutableLiveData<List<Ticket>> = MutableLiveData()
    val ticketLiveData: LiveData<List<Ticket>> = _ticketMutableLiveData

    fun fetchAllTicketItems() {
        viewModelScope.launch {
            ticketRepository.fetchAllTickets()
                .collect {
                    _ticketMutableLiveData.value = it
                }
        }
    }

    fun addToCart(ticket: Ticket, noTicket: Int) {
        viewModelScope.launch{
            ticketRepository.addTicketToCart(noTicket, ticket)
        }
    }
}

class TicketViewModelFactory constructor(
    private val ticketProvider: RoomTicketProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val ticketRepository = TicketRepository(ticketProvider, Dispatchers.Default)
        return TicketViewModel(ticketRepository) as T
    }
}
