package com.bismark.onlineticket.Cart

import android.util.Log
import com.bismark.onlineticket.data_layer.entities.CartWithTicket
import com.bismark.onlineticket.di.RoomTicketProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class CartRepository constructor(
    private val cartProvider: RoomTicketProvider,
    private val backgroundDispatcher: CoroutineDispatcher
) {

    fun getAllCartItemsFromLocal() =
        cartProvider.getCardDao().getAllCarts()
            .map { cartList ->
                cartList.map {
                    val ticket = cartProvider.getTicketDao().getTicket(it.ticketId)
                    CartWithTicket(it, ticket)
                }
            }.flowOn(backgroundDispatcher)
            .catch {
                Log.d("Flow Error", it.message.toString())
            }

    suspend fun reduceCartListToTotal(cartWithTicket: List<CartWithTicket>) =
        cartWithTicket.asFlow()
            .map { cart ->
                cart.ticket.price * cart.cart.noOfTicket
            }
            .flowOn(backgroundDispatcher)
            .catch {
                Log.d("Flow Subtotal Error", it.message.toString())
            }
            .reduce { accumulator, value ->
                accumulator + value
            }.toFloat()
}
