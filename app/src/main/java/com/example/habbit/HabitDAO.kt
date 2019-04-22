package com.example.habbit

import androidx.room.*

@Dao

interface HabitDAO {

    @Insert
    fun insertHabit(habitData: HabitBase)

    @Update
    fun updateHabit(habitData: HabitBase)

    @Delete
    fun deleteHabit(habitData: HabitBase)

    @Query("select * from habitData")
    fun selectAllHabit(): List<HabitBase>
}