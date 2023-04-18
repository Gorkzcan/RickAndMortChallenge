package com.gorkem.rmainviousg.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gorkem.rmainviousg.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var _binding : FragmentSplashBinding
    private val binding: FragmentSplashBinding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        //check if it's the first time the app is opened
        val isFirstOpen = isFirstTimeOpen(requireContext())

        //Set the welcome text based on whether it's the first time or not
        val greetingText = if(isFirstOpen) "Welcome!" else "Hello!"
        binding.twGreeting.text = greetingText

        //Save that the app has been opened at least once
        saveAppOpenedStatus(requireContext())

        navigateHome()

        return binding.root
    }

    //Check if the app has been opened before
    private fun isFirstTimeOpen(context: Context): Boolean{
        val sharedPrefs = context.getSharedPreferences("APP_OPENED", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean("IS_FIRST_TIME", true)
    }

    //Save that the app has been opened at least once
    private fun saveAppOpenedStatus(context: Context){
        val sharedPrefs = context.getSharedPreferences("APP_OPENED", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()){
            putBoolean("IS_FIRST_TIME", false)
            apply()
        }
    }

    private fun navigateHome(){
        binding.btnNavigateHome.setOnClickListener {
            val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }



}