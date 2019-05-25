package com.example.habbit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.util.*
import kotlin.collections.HashMap

class AllStatViewModel (
    val dataSourceJoined: HabitJoinDayDAO,
    val dataBase: HabitDatabase,
    application: Application): AndroidViewModel(application) {

    var joinedList: MutableList<HabitJoinDay> = dataSourceJoined.getJoinedTables()
    var joinedList2: MutableList<HabitJoinDay> = dataSourceJoined.getJoinedTables()
    var howManyDays = 0
    var habitName = ""
    private var whichIndex = 0L
    var chartArray = ArrayList <PieEntry>()
    var colorArray = ArrayList <Int>()
    var habitColor = ""
    var lineArray = ArrayList <BarEntry>()
    var labelArray = ArrayList <String>()
    private var whichMonth = ""
    private var monthCounter = 0
    //private val monthHash : HashMap<String,Float> = hashMapOf("Jan" to 1f, "Feb" to 2f, "Mar" to 3f, "Apr" to 4f
        //, "May" to 5f, "Jun" to 6f, "Jul" to 7f, "Aug" to 8f, "Sep" to 9f, "Oct" to 10f, "Nov" to 11f, "Dec" to 12f)
    private var temporaryMonth: BigDecimal = BigDecimal(0)
    private var temporaryYear : BigDecimal = BigDecimal(0)
    var counter = 0

    init {
        if (joinedList.size != 0) {
            dateJoinedList()
            sortJoinedList()
        }
    }

    fun dateJoinedList(){
        println(joinedList2)
        whichMonth = joinedList2[0].day.substring(3,5)
        labelArray.add(joinedList2[0].day.substring(3,5)+"."+joinedList2[0].day.substring(8,10))
        joinedList2.forEach {
            if (whichMonth.equals(it.day.substring(3,5))) {
                monthCounter++

            }
            else {
                println("Month" + it.day.substring(3))
                labelArray.add(it.day.substring(3,5)+"."+it.day.substring(8,10))
                lineArray.add(BarEntry(counter.toFloat(),monthCounter.toFloat()))
                println("Else Entry = "+ counter.toString() + " "+ monthCounter)
                whichMonth = it.day.substring(3,5)
                counter++
                monthCounter = 1
            }
        }
        lineArray.add(BarEntry(counter.toFloat(),monthCounter.toFloat()))
        lineArray.forEach { println(it) }
        labelArray.forEach { println(it) }


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
