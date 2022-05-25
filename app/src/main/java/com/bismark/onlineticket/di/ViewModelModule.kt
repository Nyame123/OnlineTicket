package com.bismark.onlineticket.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bismark.onlineticket.Cart.CartViewModel
import com.bismark.onlineticket.ViewModelFactories
import com.bismark.onlineticket.ticket.TicketViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKeys(CartViewModel::class)
    abstract fun bindsCartViewModel(cartViewModel: CartViewModel): ViewModel

    @[Binds IntoMap ViewModelKeys(TicketViewModel::class)]
    abstract fun bindsTicketViewModel(ticketViewModel: TicketViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactories): ViewModelProvider.Factory
}