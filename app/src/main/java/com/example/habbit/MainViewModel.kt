package com.example.habbit

import android.app.Application
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
    private fun getDateVal(): String{
        val currentDate: Date = Calendar.getInstance().time
        val locale: Locale = Locale.getDefault()
        val format: String = "EEE, dd MMM yyyy"
        return SimpleDateFormat(format,locale).format(currentDate)
    }

}