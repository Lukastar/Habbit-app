package com.example.habbit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao

interface DayDAO {

    @Insert
    fun insertDay(dayData: DayBase)

    @Update
    fun updateDay(dayData: DayBase)

    @Delete
    fun deleteDay(dayData: DayBase)
}

