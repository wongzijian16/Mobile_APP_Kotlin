package com.example.naturemagnet.entity

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    //share pref mode
    val PRIVATE_MODE = 0

    //sharedPred FileName
    private val PREF_NAME = "SharedPreferences"
    private val IS_LOGIN = "is_login"

    val pref: SharedPreferences? = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    val editor :SharedPreferences.Editor? = pref?.edit()

    fun setLogin(isLogin : Boolean){
        editor?.putBoolean(IS_LOGIN, isLogin)
        editor?.commit()
    }

    fun setEmail(custEmail : String?){
        editor?.putString("custEmail",custEmail)
        editor?.commit()
    }

    fun setEmail1(adminEmail : String?){
        editor?.putString("adminEmail",adminEmail)
        editor?.commit()
    }

    fun isLogin() : Boolean?{
        return pref?.getBoolean(IS_LOGIN,false)
    }

    fun getEmail() : String?{
        return pref?.getString("custEmail","")
    }

    fun getEmail1() : String?{
        return pref?.getString("adminEmail","")
    }

    fun setId(id : Long){
        editor?.putLong("id",id)
        editor?.commit()
    }

    fun getId() : Long?{
        return pref?.getLong("id",0)
    }

    fun removeData(){
        editor?.clear()
        editor?.commit()
    }
}