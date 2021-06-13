package com.example.shualeduri.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavAction
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.shualeduri.R
import com.example.shualeduri.databinding.FragmentSplashScreenBinding
import com.example.shualeduri.extensions.fade

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        val action: NavDirections = SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment()
        binding.imageView.fade()
        binding.splashTitle.fade(action)
    }

}