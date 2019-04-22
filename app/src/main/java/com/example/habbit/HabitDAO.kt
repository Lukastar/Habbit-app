package com.example.habbit

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.ArrayList

@Dao

interface HabitDAO {

    @Insert
    fun insertHabit(habitData: HabitBase)

    @Update
    fun updateHabit(habitData: HabitBase)

    @Delete
    fun deleteHabit(habitData: HabitBase)

    @Query("select * from habitData")
    fun selectAllHabit(): LiveData<List<HabitBase>>
}