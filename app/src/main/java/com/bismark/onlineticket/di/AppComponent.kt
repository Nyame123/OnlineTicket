package com.bismark.onlineticket.di

import com.bismark.onlineticket.Cart.CartScreen
import com.bismark.onlineticket.app.OnlineTicketApp
import com.bismark.onlineticket.ticket.TicketCenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class
    ]
)
interface AppComponent {

    fun inject(ticketCenter: TicketCenter)
    fun inject(cartScreen: CartScreen)
    fun inject(app: OnlineTicketApp)
}
