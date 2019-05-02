package com.example.habbit

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel (
    val dataSourceHabit: HabitDAO,
    val dataSourceDay: DayDAO,
    val dataBase: HabitDatabase,
    application: Application): AndroidViewModel(application){

    var habitList : LiveData<List<HabitBase>> = dataSourceHabit.selectAllHabit()

    private val _currentDay = MutableLiveData<String>()
    val currentDay : LiveData<String>
        get() = _currentDay

    init {
        _currentDay.value = getDateVal()
    }

    private val viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun deleteAll()
    {
        scope.launch{
            delete()
        }
    }

    private suspend fun delete(){
        withContext(Dispatchers.IO){
            dataBase.clearAllTables();
        }
    }

    fun onBoxChecking(id: Long) {
        scope.launch{
            onBoxCheckingUpdate(id)
        }
    }

    private suspend fun onBoxCheckingUpdate(id: Long) {
        var listOfHabits : List<HabitBase> = dataSourceHabit.selectAll()
        withContext(Dispatchers.IO){
            dataSourceHabit.updateChecked(id)
            listOfHabits = dataSourceHabit.selectAll()
            println(listOfHabits)
        }
    }

    fun onBoxUnchecking(id: Long) {
        scope.launch{
            onBoxUncheckingUpdate(id)
        }
    }

    private suspend fun onBoxUncheckingUpdate(id: Long) {
        withContext(Dispatchers.IO){
            dataSourceHabit.updateUnchecked(id)
        }
    }

    fun dayChanged() {
        scope.launch{
            _dayChanged()
        }
    }

    private suspend fun _dayChanged() {
        withContext(Dispatchers.IO) {
            dataSourceHabit.updateStreakNewDay()
            dataSourceHabit.updateMaskNewDay()
        }
    }

    private fun getDateVal(): String{
        val currentDate: Date = Calendar.getInstance().time
        val locale: Locale = Locale.getDefault()
        val format: String = "EEE, dd MMM yyyy"
        return SimpleDateFormat(format,locale).format(currentDate)
    }
}