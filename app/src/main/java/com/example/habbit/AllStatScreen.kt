package com.example.habbit

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.habbit.databinding.AllstatScreenBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.components.Legend
import kotlinx.android.synthetic.main.activity_main.*

class AllStatScreen: Fragment(){

    private lateinit var binding: AllstatScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //val binding: MainScreenBinding = DataBindingUtil.inflate(inflater, R.layout.main_screen, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.allstat_screen, container, false)
        val pieChart: PieChart = binding.placeholderChart
        pieChart.setUsePercentValues(true)
        var chartArray = ArrayList <PieEntry>()
        chartArray.add(PieEntry(60f,"Running"))
        chartArray.add(PieEntry(44f, "Learning Guitar"))
        chartArray.add(PieEntry(20f, "Farming Iron Ore"))
        val datSet: PieDataSet = PieDataSet(chartArray, "Habits")
        datSet.setColors(ColorTemplate.JOYFUL_COLORS,250)
        val pieDat: PieData = PieData(datSet)
        pieDat.setValueTextSize(20f)
        pieChart.setEntryLabelTextSize(15f)
        val legend: Legend = pieChart.legend
        legend.textSize = 15f
        pieChart.data = pieDat
        pieChart.description.isEnabled = false
        pieChart.invalidate()
        return binding.root
    }
}