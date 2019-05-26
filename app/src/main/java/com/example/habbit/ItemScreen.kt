package com.example.habbit

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.habbit.databinding.ItemScreenBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.clear_screen.view.*
import java.lang.NullPointerException

class ItemScreen: Fragment(){

    private lateinit var binding: ItemScreenBinding
    private lateinit var itemScreenViewModel: ItemScreenViewModel
    private var id: Long? = 0
    private var name: String? = ""
    private var color: Int? = 0
    private var tracking: Boolean? = false
    private lateinit var bundle : Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.item_screen, container, false)

        val application = requireNotNull(this.activity).application
        val base = HabitDatabase.getInstance(application)
        val dataSourceHabit = base!!.habitDAO

        val editFragment = EditHabbitScreen()
        bundle = Bundle()
        bundle.putAll(arguments)
        editFragment.arguments = bundle

        id = arguments?.getLong("id")
        name = arguments?.getString("name")
        color = arguments?.getInt("color")

        val itemScreenViewModelFactory = ItemScreenViewModelFactory(dataSourceHabit, application)
        itemScreenViewModel = ViewModelProviders.of(this, itemScreenViewModelFactory).get(ItemScreenViewModel::class.java)
        binding.itemScreenViewModel = itemScreenViewModel

        /* for future endeavors
        val application = requireNotNull(this.activity).application
        val base = HabitDatabase.getInstance(application)
        val dataSourceHabit = base!!.habitDAO
        dataSourceHabit.deleteFromBase(id!!)
        */

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.actionbaritem_menu, menu)
        activity?.custom_toolbar?.setBackgroundColor(color!!)
        activity?.custom_toolbar?.setTitle(name)
        activity?.window!!.statusBarColor = color!!
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.title.isNullOrEmpty()){
            return NavigationUI.onNavDestinationSelected(item, view!!.findNavController())
                    || super.onOptionsItemSelected(item)
        }

        else if (item.title.equals("Edit")){
            view?.findNavController()?.navigate(R.id.action_itemScreen_to_editHabbitScreen2, bundle)
            return false
        }

        else if(item.title.equals("Delete")){
            itemScreenViewModel.deleteItem(id!!)
            view?.findNavController()?.navigate(R.id.action_itemScreen_to_mainScreen)
            return false
        }
        return false


    }
}