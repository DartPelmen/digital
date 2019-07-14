package com.example.myapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Ticket",foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["Id"],
    childColumns = ["user_id"])])
data class Ticket (
    @ColumnInfo(name="Id")
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name="activated")
    var activated: Boolean,
    @ColumnInfo(name="created_at")
    var created_at: Date,
    @ColumnInfo(name="used_at")
    var used_at: Date?,
    @ColumnInfo(name="user_id")
    var user_id:Int
)