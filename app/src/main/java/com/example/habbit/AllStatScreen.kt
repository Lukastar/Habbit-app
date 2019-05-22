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
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.components.Legend
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
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
        val lineChart: LineChart = binding.allBarchart
        pieChart.setUsePercentValues(true)

        val datSet: PieDataSet = PieDataSet(allStatViewModel.chartArray, "Habits")
        datSet.setColors(allStatViewModel.colorArray)
        val pieDat: PieData = PieData(datSet)

        val monthDataSet: LineDataSet = LineDataSet(allStatViewModel.lineArray, "Monthly")
        monthDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        monthDataSet.fillAlpha = 500
        monthDataSet.setDrawFilled(true)
        monthDataSet.fillColor = context!!.getColor(R.color.app_bar_color)
        monthDataSet.color = context!!.getColor(R.color.app_bar_color)
        monthDataSet.setDrawCircles(false)
        monthDataSet.setDrawValues(false)
        val monthsSets : ArrayList<ILineDataSet> = ArrayList<ILineDataSet>()
        monthsSets.add(monthDataSet)
        val lineDat: LineData = LineData(monthDataSet)

        pieDat.setValueTextSize(25f)
        pieChart.setEntryLabelTextSize(15f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.legend.isEnabled = false
        pieChart.centerText = "All time\nstats"
        pieChart.setCenterTextSize(25f)
        pieChart.data = pieDat
        pieChart.description.isEnabled = false
        pieChart.invalidate()

        lineChart.data = lineDat
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.setDrawGridBackground(false)
        lineChart.legend.isEnabled = false
        lineChart.description.isEnabled = false
        lineChart.invalidate()

        return binding.root
    }
}