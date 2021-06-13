package com.example.shualeduri.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shualeduri.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        mAuth = FirebaseAuth.getInstance()

        binding.registrationRegButton.setOnClickListener {
            register()
        }

        binding.toLogin.setOnClickListener {
            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment())
        }

    }


    private fun register() {
        val email = binding.emailRegInput.text.toString()
        val password = binding.passRegInput.text.toString()
        val passwordConfirmed = binding.passRegConfInput.text.toString()

        if (email.isEmpty() || password.isEmpty() || passwordConfirmed.isEmpty() || password != passwordConfirmed) {
            Toast.makeText(requireContext(), "fill fields", Toast.LENGTH_SHORT).show()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { process ->
                if (process.isSuccessful) {
                    Toast.makeText(requireContext(), "You are now registered !", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error, Check Internet connection", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}