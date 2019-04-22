package com.example.habbit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habbit.databinding.HabitItemBinding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.habit_item.view.*
import java.util.ArrayList

class MainAdapter(val habitList: List<HabitBase>): RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    private lateinit var binding: HabitItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
        val layout = R.layout.habit_item
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        //val layoutInflater = LayoutInflater.from(parent.context)
        //binding = DataBindingUtil.inflate(layoutInflater, layout, parent, false)
        return MainViewHolder(view)
    }


    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        holder.name.text = habitList[position].name
        holder.streak.text = habitList[position].streak.toString()
    }

    class MainViewHolder(val view: View): RecyclerView.ViewHolder(view){
            val name = view.itemName
            val streak = view.itemStreak

    }

    override fun getItemCount(): Int {
        return if(habitList!!.isEmpty()) 0 else habitList!!.size
    }
}