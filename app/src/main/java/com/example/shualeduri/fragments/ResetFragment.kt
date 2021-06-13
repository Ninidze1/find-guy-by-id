package com.example.shualeduri.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shualeduri.databinding.DialogItemBinding
import com.example.shualeduri.databinding.FragmentResetBinding
import com.example.shualeduri.extensions.showDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class ResetFragment : Fragment() {

    private lateinit var binding: FragmentResetBinding
    private lateinit var mAuth: FirebaseAuth

    private lateinit var dialogBinding: DialogItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        mAuth = FirebaseAuth.getInstance()

        binding.resetButton.setOnClickListener {

            val email = binding.editTextTextEmailAddress.text.toString()
            if (email.isNotEmpty()) {
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener { process ->

                    if (process.isSuccessful) {
                        showDialog()
                        findNavController().navigate(ResetFragmentDirections.actionResetFragmentToLoginFragment())
                    } else {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "empty field", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDialog() {
        dialogBinding = DialogItemBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.showDialog(dialogBinding)
    }

}