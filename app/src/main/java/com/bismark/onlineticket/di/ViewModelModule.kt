package com.bismark.onlineticket.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bismark.onlineticket.Cart.CartViewModel
import com.bismark.onlineticket.Cart.CartViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindViewModel(cartViewModel: CartViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: CartViewModelFactory): ViewModelProvider.Factory
}