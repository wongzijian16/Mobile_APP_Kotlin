package com.example.naturemagnet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.naturemagnet.entity.PrefManager
import com.example.naturemagnet.database.NatureMagnetDB
import com.example.naturemagnet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    private lateinit var db: NatureMagnetDB
    private lateinit var prefManager: PrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
        navController = findNavController(R.id.hostFragment)

        db = NatureMagnetDB.getInstance(this)!!
        Log.i("MainActivity", db.customerDao().getCustAll().toString())
        checkLogin()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.awarenessMainFragment,
                R.id.eventMainFragment, R.id.fragment_user_main,
                R.id.fragment_shop, R.id.eventManageFragment
            ), binding.drawerLayout
        )
        binding.apply {
            //toolbar
            toolbar.setupWithNavController(navController, appBarConfiguration)

            //drawer
            navView.setupWithNavController(navController)

            //bottomNav
            bottomNavigation.setupWithNavController(navController)
        }

        //hide or show the bottom nav in specific fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                //this ID refer to the id that inside the bottom nav menu
                R.id.homeFragment -> showBottomNav()
                R.id.awarenessMainFragment -> showBottomNav()
                R.id.eventMainFragment -> showBottomNav()
                R.id.fragment_shop -> showBottomNav()
                R.id.fragment_user_main -> showBottomNav()
                R.id.eventManageFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }


    }

    private fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        binding.bottomNavigation.visibility = View.GONE
    }

    private fun init() {
        prefManager = PrefManager(this)
    }

    private fun checkLogin() {


        if (prefManager.isLogin() == false) {
            val intent = Intent(this, activity_user_firstpage::class.java)
            startActivity(intent)
        } else {


            val navGraph = navController.navInflater.inflate(R.navigation.navigation_graph)
            if (prefManager.getEmail() != "") {
                Log.e("Tag1",prefManager.getEmail().toString())
                Log.e("Tag","Log home")
                navGraph.setStartDestination(R.id.homeFragment)
            }

//            if (prefManager.getEmail1() !="") {
//                Log.e("Tag2",prefManager.getEmail1().toString())
//                Log.e("Tag","Log admin")
//                navGraph.setStartDestination(R.id.fragment_admin_management)
//            }

            navController.graph = navGraph

        }
    }


}