package com.example.myapplication.dao

import androidx.room.*
import com.example.myapplication.models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)
    @Update
    fun updateUser(user:User)
    @Delete
    fun deleteUser(user:User)
    @Query("SELECT * FROM User")
    fun getUsers():List<User>
    @Query("SELECT * FROM User WHERE username=:username AND password=:password")
    fun getUserByUsernameAndPassword(username:String, password:String):List<User>

}