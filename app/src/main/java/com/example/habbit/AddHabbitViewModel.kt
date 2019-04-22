package com.example.habbit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class AddHabbitViewModel (
    val dataSource: HabitDAO,
    application: Application): AndroidViewModel(application){

    var color: Int = 0

    private val viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun addToDatabase(name: String, track: Boolean) {
        scope.launch {
            insert(name, track)
        }
    }
    private suspend fun insert(name: String, track: Boolean) {
        withContext(Dispatchers.IO) {
            dataSource.insertHabit(HabitBase(null,name,track,color.toString(),0,false))
            println(dataSource.selectAllHabit())
        }
    }
}