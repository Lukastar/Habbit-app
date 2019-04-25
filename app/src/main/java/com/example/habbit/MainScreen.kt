package com.example.habbit

import android.animation.Animator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.habbit.databinding.MainScreenBinding
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.clear_screen.view.*
import android.widget.CompoundButton.OnCheckedChangeListener

class MainScreen: Fragment(){

    private lateinit var binding: MainScreenBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    var boxChecked : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //var mainColor = ColorDrawable(Color.BLUE)
        //(activity as AppCompatActivity).setSupportActionBar(custom_toolbar)
        //(activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(mainColor)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_screen, container, false)

        val application = requireNotNull(this.activity).application
        val base = HabitDatabase.getInstance(application)
        val dataSourceHabit = base!!.habitDAO
        val dataSourceDay = base!!.dayDAO
        linearLayoutManager = LinearLayoutManager(context)

        recyclerView = binding.mainRecycler.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }

        val itemDecor = DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation)
        itemDecor.setOrientation(1)
        recyclerView.addItemDecoration(itemDecor)

        val mainViewModelFactory = MainViewModelFactory(dataSourceHabit, dataSourceDay, base, application)
        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
        binding.mainViewModel = mainViewModel

        mainViewModel.habitList.observe(this, Observer { newList ->
            recyclerView.adapter = MainAdapter(newList, this)
        })

        /**
        mainViewModel.habitList.observe(this, Observer { newList ->
            val mainAdapter = MainAdapter(newList) {position ->
                mainViewModel.onBoxChecking(position.toLong())
            }
            recyclerView.adapter = mainAdapter
        })
        **/

        mainViewModel.currentDay.observe(this, Observer { newDay ->
            binding.dateText.text = newDay
        })

        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
        inflater.inflate(R.menu.actionbar_menu, menu)
        activity?.custom_toolbar?.setBackgroundColor(ContextCompat.getColor(context!!,R.color.app_bar_color))
        //(activity as AppCompatActivity).setSupportActionBar(custom_toolbar)
        //(activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(mainColor)
        //activity?.actionBar?.setBackgroundDrawable(mainColor)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.title.equals("Clear Data")){
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.clear_screen, null)
            val mBuilder = AlertDialog.Builder(context!!).setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            val application = requireNotNull(this.activity).application
            mDialogView.button.setOnClickListener {
                mainViewModel.deleteAll()
                mAlertDialog.dismiss()
            }
            mDialogView.button2.setOnClickListener {
                mAlertDialog.dismiss()
            }
            return false
        }
        else{
            return NavigationUI.onNavDestinationSelected(item, view!!.findNavController())
                || super.onOptionsItemSelected(item)}
    }

    fun checkingBox(id : Long) {
        if (boxChecked) {
            mainViewModel.onBoxChecking(id)
        }
        else {
            //mainViewModel.onBoxUnchecking(id)
        }
    }

    override fun onResume() {
        super.onResume()
    }
}


