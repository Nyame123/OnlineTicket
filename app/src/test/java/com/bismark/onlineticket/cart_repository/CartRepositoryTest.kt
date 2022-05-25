package com.bismark.onlineticket.cart_repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bismark.onlineticket.Cart.CartRepository
import com.bismark.onlineticket.R
import com.bismark.onlineticket.data_layer.entities.Cart
import com.bismark.onlineticket.data_layer.entities.CartWithTicket
import com.bismark.onlineticket.data_layer.entities.Ticket
import com.bismark.onlineticket.di.RoomTicketProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CartRepositoryTest {

    lateinit var cartRepository: CartRepository

    @Mock
    lateinit var cartProvider: RoomTicketProvider

    lateinit var coroutineDispatcher: TestCoroutineDispatcher

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        coroutineDispatcher = TestCoroutineDispatcher()
        cartRepository = CartRepository(
            cartProvider,
            coroutineDispatcher
        )
    }

    @Test
    fun testSubtotalCalculation() = runBlocking {
        val cartWithTicket = provideCartWithTicketList()

        val result = async {
            cartRepository.reduceCartListToTotal(cartWithTicket)
        }

        assertEquals(result.await(), 12.0f)
    }

    private fun provideCartWithTicketList(): List<CartWithTicket> {
        //first
        val ticket1 = Ticket(
            ticketId = 1,
            itemName = "Football Ticket",
            createdDate = Date(),
            description = "This is a football Laliga ticket sold",
            imageResource = R.drawable.football,
            price = 3f
        )

        val cartWithTicket1 = CartWithTicket(
            Cart(
                cartId = 1,
                noOfTicket = 2,
                ticketId = 1
            ),
            ticket = ticket1
        )

        //second
        val ticket2 = Ticket(
            ticketId = 2,
            itemName = "Volley Ball Ticket",
            createdDate = Date(),
            description = "This is a volley ball ticket sold",
            imageResource = R.drawable.volley,
            price = 2.0f
        )

        val cartWithTicket2 = CartWithTicket(
            Cart(
                cartId = 2,
                noOfTicket = 3,
                ticketId = 2
            ),
            ticket = ticket2
        )

        val cartsWithTicketList = mutableListOf<CartWithTicket>()
        cartsWithTicketList.add(cartWithTicket1)
        cartsWithTicketList.add(cartWithTicket2)

        return cartsWithTicketList
    }
}
