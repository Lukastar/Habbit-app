package com.example.habbit

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.habbit.databinding.AllstatScreenBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.components.Legend
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import java.util.*

class AllStatScreen: Fragment(){

    private lateinit var binding: AllstatScreenBinding
    private lateinit var allStatViewModel : AllStatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //val binding: MainScreenBinding = DataBindingUtil.inflate(inflater, R.layout.main_screen, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.allstat_screen, container, false)

        val application = requireNotNull(this.activity).application
        val base = HabitDatabase.getInstance(application)
        val dataSourceJoined = base!!.joinedDAO
        val mainViewModelFactory = AllStatViewModelFactory(dataSourceJoined, base, application)
        allStatViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(AllStatViewModel::class.java)
        binding.allStatViewModel = allStatViewModel

        val pieChart: PieChart = binding.placeholderChart
        pieChart.setUsePercentValues(true)

        /**allStatViewModel.habitCounted.observe(this, Observer { _ ->

            var howManyDaysForChart = allStatViewModel.howManyDays.toFloat()
            var nameForChart = allStatViewModel.habitName
            println("GGGGGGGGGGGGGGG")
            println(howManyDaysForChart)


            chartArray.add(PieEntry(howManyDaysForChart, nameForChart))
            println(chartArray)
        })**/

        val datSet: PieDataSet = PieDataSet(allStatViewModel.chartArray, "Habits")
        datSet.setColors(allStatViewModel.colorArray)
        val pieDat: PieData = PieData(datSet)

        pieDat.setValueTextSize(25f)
        pieChart.setEntryLabelTextSize(15f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.legend.isEnabled = false
        pieChart.centerText = "All time\nstats"
        pieChart.setCenterTextSize(25f)
        pieChart.data = pieDat
        pieChart.description.isEnabled = false
        pieChart.invalidate()

        return binding.root
    }
}