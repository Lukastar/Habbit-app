package com.example.habbit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habbit.databinding.HabitItemBinding
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.habit_item.view.*

class MainAdapter(var habitList:List<HabitBase>? = ArrayList<HabitBase>()): RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    private lateinit var binding: HabitItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
        val layout = R.layout.habit_item
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        //val layoutInflater = LayoutInflater.from(parent.context)
        //binding = DataBindingUtil.inflate(layoutInflater, layout, parent, false)
        return MainViewHolder(view, habitList!!)
    }


    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        holder.onBindViews(position)
    }

    inner class MainViewHolder(val view: View, val habitList:List<HabitBase>): RecyclerView.ViewHolder(view){
        fun onBindViews(position: Int){
            view.itemName.text = habitList.get(position).name
            view.itemStreak.text = habitList.get(position).streak.toString()
        }
    }

    override fun getItemCount(): Int {
        return if(habitList!!.isEmpty()) 0 else habitList!!.size
    }
}