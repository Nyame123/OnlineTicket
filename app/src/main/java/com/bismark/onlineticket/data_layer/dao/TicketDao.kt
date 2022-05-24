package com.bismark.onlineticket.data_layer.dao

import androidx.room.*
import com.bismark.onlineticket.data_layer.entities.Ticket
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Insert
    suspend fun insertTicket(ticket: Ticket)

    @Insert
    suspend fun insertTicket(tickets: List<Ticket>)

    @Transaction
    @Query("Select * from ticket")
    suspend fun getAllTicket(): Flow<List<Ticket>>

    @Query("Select * from ticket where id = :id")
    suspend fun getTicket(id: Int): Ticket

    @Update
    suspend fun updateTicket(ticket: Ticket)

    @Delete
    suspend fun deleteTicket(ticket: Ticket)
}
