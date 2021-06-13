package com.example.shualeduri.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shualeduri.R
import com.example.shualeduri.databinding.MainFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        init()
        return binding.root

    }

    private fun init() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        mAuth = FirebaseAuth.getInstance()

        initTitle()

        binding.exit.setOnClickListener {
            signOut()
        }

        binding.checkButton.setOnClickListener {
            toInfoFragment()
        }

        binding.historyButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_historyFragment)
        }
    }

    private fun signOut() {
        mAuth.signOut()
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
    }

    private fun toInfoFragment() {
        val inputText = binding.ipInput.text.trim().toString()

        if (binding.ipInput.text.isNotEmpty() && isValidIPAddress(inputText)) {
            val action = MainFragmentDirections.actionMainFragmentToIpInfoFragment(inputText)
            findNavController().navigate(action)
            binding.ipInput.text.clear()
        } else {
            Toast.makeText(requireContext(), "incorrect ip", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidIPAddress(ip:String):Boolean {
        val reg = ("(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])")
        val regex = ("$reg\\.$reg\\.$reg\\.$reg")
        val pattern = Pattern.compile(regex)
        val matches = pattern.matcher(ip)
        return matches.matches()
    }

    private fun initTitle() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            binding.emailText.text = it.email
        }
    }
}