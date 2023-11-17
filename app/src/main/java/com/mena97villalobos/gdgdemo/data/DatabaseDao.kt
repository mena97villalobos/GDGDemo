package com.mena97villalobos.gdgdemo.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface DatabaseDao {

    @Update
    fun updateUser(user: User)

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): List<User>

}