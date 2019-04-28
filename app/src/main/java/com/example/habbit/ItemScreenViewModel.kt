package com.example.habbit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*

class ItemScreenViewModel (
    val dataSource: HabitDAO,
    application: Application): AndroidViewModel(application){

    private val viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun deleteItem(id: Long) {
        scope.launch {
            delete(id)
        }
    }

    private suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            dataSource.deleteFromBase(id)
        }
    }
}