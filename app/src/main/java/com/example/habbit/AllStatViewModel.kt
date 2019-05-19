package com.example.habbit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.*
import java.util.ArrayList

class AllStatViewModel (
    val dataSourceJoined: HabitJoinDayDAO,
    val dataBase: HabitDatabase,
    application: Application): AndroidViewModel(application) {

    var joinedList: MutableList<HabitJoinDay> = dataSourceJoined.getJoinedTables()
    var howManyDays = 0
    var habitName = ""
    private var whichIndex = 0L
    var chartArray = ArrayList <PieEntry>()
    var colorArray = ArrayList <Int>()
    var habitColor = ""

    init {
        sortJoinedList()
    }

    fun sortJoinedList()
    {
        joinedList.sortBy{ it.habitId }
        whichIndex = joinedList[0].habitId
        habitName = joinedList[0].name
        habitColor = joinedList[0].color
        joinedList.forEach {
           if (it.habitId == whichIndex) {
               howManyDays++
           }
            else {
               colorArray.add(habitColor.toInt())
               chartArray.add(PieEntry(howManyDays.toFloat(), habitName))
               habitName = it.name
               habitColor = it.color
               whichIndex = it.habitId
               howManyDays = 1
           }
        }
        colorArray.add(habitColor.toInt())
        chartArray.add(PieEntry(howManyDays.toFloat(), habitName))
    }
}
