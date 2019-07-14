package com.example.myapplication.dao

import androidx.room.*
import com.example.myapplication.models.Ticket

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicket(ticket: Ticket)
    @Update
    fun updateTicket(ticket: Ticket)
    @Delete
    fun deleteTicket(ticket: Ticket)
}