package com.example.myapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "User")
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="Id")
    var id: Int,
    @ColumnInfo(name="date_of_birth")
    var date_of_birth: Date,
    @ColumnInfo(name="email")
    var email: String,
    @ColumnInfo(name="first_name")
    var first_name: String,
    @ColumnInfo(name="middle_name")
    var middle_name: String?,
    @ColumnInfo(name="last_name")
    var last_name: String,
    @ColumnInfo(name="passport")
    var passport: String,
    @ColumnInfo(name="phone")
    var phone: String,
    @ColumnInfo(name="username")
    var username: String,
    @ColumnInfo(name="password")
    var password: String
    )