package com.bismark.onlineticket.di

import com.bismark.onlineticket.Cart.CartScreen
import com.bismark.onlineticket.TicketCenter
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
}
