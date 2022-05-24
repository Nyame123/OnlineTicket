package com.bismark.onlineticket.data_layer.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Cart(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val ticket: Ticket,
    val noOfTicket: Int
)

@Entity
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val itemName: String,
    val createdDate: Date,
    val description: String,
    val itemImageUrl: String,
    val price: Float
)