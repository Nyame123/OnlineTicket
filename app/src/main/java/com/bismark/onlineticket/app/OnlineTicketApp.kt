package com.bismark.onlineticket.app

import android.app.Application
import android.content.Context
import com.bismark.onlineticket.R
import com.bismark.onlineticket.data_layer.TicketDatabase
import com.bismark.onlineticket.data_layer.entities.Ticket
import com.bismark.onlineticket.di.AppComponent
import com.bismark.onlineticket.di.DaggerAppComponent
import com.bismark.onlineticket.di.DatabaseModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class OnlineTicketApp : Application() {
    @Inject
    lateinit var database: TicketDatabase

    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent

    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(this))
            .build()

        appComponent.inject(this)

        doTransaction()
    }

    fun doTransaction() = runBlocking {

        val ticketDao = database.ticketDao()
        val cartDao = database.cartDao()
        //first
        val ticket1 = Ticket(
            itemName = "Football Ticket",
            createdDate = Date(),
            description = "This is a football Laliga ticket sold",
            imageResource = R.drawable.football,
            price = 322.00f
        )

        //second
        val ticket2 = Ticket(
            itemName = "Volley Ball Ticket",
            createdDate = Date(),
            description = "This is a volley ball ticket sold",
            imageResource = R.drawable.volley,
            price = 254.00f
        )

        //third
        val ticket3 = Ticket(
            itemName = "Sport Ticket",
            createdDate = Date(),
            description = "This is a football UPL ticket sold",
            imageResource = R.drawable.sports,
            price = 122.00f
        )

        //fourth
        val ticket4 = Ticket(
            itemName = "Yoga Ticket",
            createdDate = Date(),
            description = "This is a yoga ticket sold",
            imageResource = R.drawable.yoga,
            price = 222.00f
        )

        val tickets = mutableListOf<Ticket>()
        tickets.add(ticket1)
        tickets.add(ticket2)
        tickets.add(ticket3)
        tickets.add(ticket4)

        withContext(Dispatchers.IO) {
            ticketDao.deleteAll()
            cartDao.deleteAll()

            ticketDao.insertTicket(tickets)
        }
    }
}