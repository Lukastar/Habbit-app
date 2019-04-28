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

    //for test
    @Query("select * from habitData")
    fun selectAll(): List<HabitBase>

    @Query("update habitData set streak = streak+1, reset_mask = 1 where id = :id")
    fun updateChecked(id: Long)

    @Query("update habitData set streak = streak-1, reset_mask = 0 where id = :id")
    fun updateUnchecked(id: Long)

    @Query("select * from habitData where id = :id")
    fun printLineFromBase(id: Long) : List<HabitBase>

    @Query("delete from habitData where id = :id")
    fun deleteFromBase(id: Long)
}