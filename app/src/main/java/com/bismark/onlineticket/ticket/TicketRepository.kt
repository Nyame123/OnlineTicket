package com.bismark.onlineticket.ticket

import com.bismark.onlineticket.data_layer.entities.Cart
import com.bismark.onlineticket.data_layer.entities.Ticket
import com.bismark.onlineticket.di.RoomTicketProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class TicketRepository constructor(
    private val ticketProvider: RoomTicketProvider,
    private val backgroundDispatcher: CoroutineDispatcher
) {

    fun fetchAllTickets() = ticketProvider.getTicketDao().getAllTicket()
        .flowOn(backgroundDispatcher)

    suspend fun addTicketToCart(
        noTicket: Int,
        ticket: Ticket
    ) {
        withContext(backgroundDispatcher) {
            val cart = Cart(
                noOfTicket = noTicket,
                ticketId = ticket.ticketId
            )

            ticketProvider.getCardDao().insertCart(cart)
        }
    }
}
