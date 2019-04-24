package com.example.habbit

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habbit.databinding.HabitItemBinding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.habit_item.view.*
import java.util.ArrayList

class MainAdapter(val habitList: List<HabitBase>): RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    //move to ViewModel and change to LiveData
    private lateinit var itemStateArray : ArrayList<HabitBase>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
        val layout = R.layout.habit_item
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        itemStateArray = arrayListOf<HabitBase>()
        //val layoutInflater = LayoutInflater.from(parent.context)
        //binding = DataBindingUtil.inflate(layoutInflater, layout, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        holder.name.text = habitList[position].name
        if (habitList[position].tracking) {
            holder.streak.text = habitList[position].streak.toString()
        }
        else {
            holder.streak.text = "-"
        }

        holder.box.setChecked(itemStateArray.contains(habitList[position]))
    }

    inner class MainViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val name = view.itemName
        val streak = view.itemStreak
        val box = view.itemCheck

        init {
            //view.setOnClickListener ({ boxClick(position) })
            view.setOnClickListener {box.isChecked = !box.isChecked}
            box.setOnCheckedChangeListener { buttonView, isChecked ->
                val adapterItem = habitList[position]
                if (isChecked){
                    itemStateArray.add(adapterItem)
                }
                else
                {
                    itemStateArray.remove(adapterItem)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if(habitList!!.isEmpty()) 0 else habitList!!.size
    }
}