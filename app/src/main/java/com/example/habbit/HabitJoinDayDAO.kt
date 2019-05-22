package com.example.habbit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao

interface HabitJoinDayDAO {

    @Query("select habitData.habitId, dayData.dayId, name, color, streak, day from habitData inner join dayData on habitData.habitId=dayData.habit_id")
    fun getJoinedTables(): MutableList<HabitJoinDay>
}