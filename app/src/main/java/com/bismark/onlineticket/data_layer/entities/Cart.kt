package com.bismark.onlineticket.data_layer.entities

import androidx.annotation.DrawableRes
import androidx.room.*
import java.util.*

@Entity
data class Cart(
    @PrimaryKey(autoGenerate = true) val cartId: Int = 0,
    val noOfTicket: Int,
    val ticketId: Int
)

@Entity
data class Ticket(
    @PrimaryKey(autoGenerate = true) val ticketId: Int = 0,
    val itemName: String,
    val createdDate: Date,
    val description: String,
    @DrawableRes val imageResource: Int,
    val price: Float
)

data class CartWithTicket(
    val cart: Cart,
    val ticket: Ticket
)
