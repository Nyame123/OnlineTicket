package com.bismark.onlineticket.data_layer

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bismark.onlineticket.data_layer.dao.CartDao
import com.bismark.onlineticket.data_layer.dao.TicketDao
import com.bismark.onlineticket.data_layer.entities.Cart
import com.bismark.onlineticket.data_layer.entities.Ticket

const val DATABASE_NAME = "onlineTicket"

@Database(
    entities = [
        Cart::class,
        Ticket::class
    ], version = 1, exportSchema = false
)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun ticketDao(): TicketDao
}
