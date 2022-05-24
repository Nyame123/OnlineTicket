package com.bismark.onlineticket.di

import com.bismark.onlineticket.data_layer.TicketDatabase
import com.bismark.onlineticket.data_layer.dao.CartDao
import com.bismark.onlineticket.data_layer.dao.TicketDao
import javax.inject.Inject

interface RoomTicketProvider {
    fun getCardDao(): CartDao
    fun getTicketDao(): TicketDao
}

class RoomTicketProviderImpl @Inject constructor(
    private val ticketDatabase: TicketDatabase
) : RoomTicketProvider{
    override fun getCardDao(): CartDao {
       return ticketDatabase.cartDao()
    }

    override fun getTicketDao(): TicketDao {
        return ticketDatabase.ticketDao()
    }

}