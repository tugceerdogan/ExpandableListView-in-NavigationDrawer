package com.example.expandablelistviewnavigationdrawer

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.expandablelistviewnavigationdrawer.databinding.ActivityMainBinding
import com.example.expandablelistviewnavigationdrawer.ui.expanded.ExpandedMenuAdapter
import com.example.expandablelistviewnavigationdrawer.ui.expanded.ExpandedMenuModel
import com.google.android.material.navigation.NavigationView
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var viewGroup: View? = null

    private val headerList: ArrayList<ExpandedMenuModel> = ArrayList<ExpandedMenuModel>()
    private val childList: HashMap<ExpandedMenuModel, ArrayList<String>> =
        HashMap<ExpandedMenuModel, ArrayList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_firstChild, R.id.nav_secondChild,
                R.id.nav_thirdChild, R.id.nav_fourthChild, R.id.nav_fifthChild
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        prepareListData()

        //Initialize and Assign ExpandableListView
        val expandableListView: ExpandableListView = binding.expandedListView


        //Set Adapter in ExpandableListView :
        val mMenuAdapter =
            ExpandedMenuAdapter(this, headerList, childList, expandableListView)
        expandableListView.setAdapter(mMenuAdapter)

        expandableListView.choiceMode = ExpandableListView.CHOICE_MODE_SINGLE

        expandableListView.setOnGroupClickListener { parent, _, groupPosition, _ ->
            when (groupPosition) {
                0 -> {
                    if (parent.isGroupExpanded(groupPosition)) {
                        parent.collapseGroup(groupPosition)
                    } else {
                        parent.expandGroup(groupPosition)

                        parent.setOnChildClickListener { parent, view, groupPosition, childPosition, _ ->
                            view.isSelected = true
                            viewGroup?.setBackgroundColor(Color.parseColor("#FFFFFF"))
                            viewGroup = view
                            viewGroup?.setBackgroundColor(Color.parseColor("#2ba89c"))

                            drawerLayout.closeDrawer(GravityCompat.START)
                            when (childPosition) {
                                0 -> navController.navigate(R.id.nav_firstChild)
                                1 -> navController.navigate(R.id.nav_secondChild)
                            }
                            parent.expandGroup(groupPosition)
                        }
                    }
                }

                1 -> {
                    if (parent.isGroupExpanded(groupPosition)) {
                        parent.collapseGroup(groupPosition)
                    } else {
                        parent.expandGroup(groupPosition)
                        parent.setOnChildClickListener { parent, view, groupPosition, childPosition, _ ->
                            view.isSelected = true;
                            viewGroup?.setBackgroundColor(Color.parseColor("#FFFFFF"))
                            viewGroup = view;
                            viewGroup?.setBackgroundColor(Color.parseColor("#2ba89c"))
                            drawerLayout.closeDrawer(GravityCompat.START)
                            when (childPosition) {
                                0 -> navController.navigate(R.id.nav_thirdChild)
                                1 -> navController.navigate(R.id.nav_fourthChild)
                                2 -> navController.navigate(R.id.nav_fifthChild)
                            }
                            parent.expandGroup(groupPosition)
                        }
                    }
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun prepareListData() {

        val headerFirst = ExpandedMenuModel()
        headerFirst.itemName = ("Header1")
        headerFirst.itemIcon = R.drawable.ic_menu_camera

        headerList.add(headerFirst)

        val headerSecond = ExpandedMenuModel()
        headerSecond.itemName = ("Header2")
        headerSecond.itemIcon = R.drawable.ic_menu_camera

        headerList.add(headerSecond)

        val childFirst = ArrayList<String>()
        childFirst.add("Child1")
        childFirst.add("Child2")

        val childSecond = ArrayList<String>()
        childSecond.add("Child3")
        childSecond.add("Child4")
        childSecond.add("Child5")

        childList[headerList[0]] = childFirst
        childList[headerList[1]] = childSecond
    }
}