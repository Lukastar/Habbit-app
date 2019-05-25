package com.example.habbit

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.graphics.toColor
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.habbit.databinding.AllstatScreenBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.components.Legend
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
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
        val barChart: BarChart = binding.allBarchart
        pieChart.setUsePercentValues(true)

        val datSet: PieDataSet = PieDataSet(allStatViewModel.chartArray, "Habits")
        datSet.setColors(allStatViewModel.colorArray)
        val pieDat: PieData = PieData(datSet)

        val monthDataSet: BarDataSet = BarDataSet(allStatViewModel.lineArray, "Monthly")
        monthDataSet.color = context!!.getColor(R.color.app_bar_color)
        monthDataSet.setDrawValues(true)
        monthDataSet.valueTextSize = 15f
        val barDat: BarData = BarData(monthDataSet)

        pieDat.setValueTextSize(25f)
        pieChart.setEntryLabelTextSize(15f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.legend.isEnabled = false
        pieChart.setCenterTextSize(25f)
        pieChart.data = pieDat
        pieChart.description.isEnabled = false
        pieChart.invalidate()

        barChart.data = barDat
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisRight.setDrawGridLines(false)
        barChart.xAxis.setDrawGridLines(false)
        barChart.legend.isEnabled = false
        barChart.description.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.axisLeft.isEnabled = false
        barChart.xAxis.labelCount = allStatViewModel.counter+1
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(allStatViewModel.labelArray)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.textSize = 12f
        barChart.invalidate()

        return binding.root
    }
}