package com.bismark.onlineticket.data_layer.dao

import androidx.room.*
import com.bismark.onlineticket.data_layer.entities.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert
    suspend fun insertCart(cart: Cart)

    @Insert
    suspend fun insertCart(carts: List<Cart>)

    @Transaction
    @Query("Select * from Cart")
    fun getAllCarts(): Flow<List<Cart>>

    @Query("Select * from cart where cartId = :id")
    suspend fun getCart(id: Int): Cart

    @Update
    suspend fun updateCart(cart: Cart)

    @Delete
    suspend fun deleteCart(cart: Cart)

    @Query("Delete from Cart")
    suspend fun deleteAll()
}
