package com.example.shualeduri.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shualeduri.R
import com.example.shualeduri.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
        }
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.registerButton.setOnClickListener {
            registration()
        }

        binding.resetButton.setOnClickListener {
            reset()
        }

    }

    private fun login() {
        val email = binding.loginInput.text.toString()
        val password = binding.passInput.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "fill fields", Toast.LENGTH_SHORT).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { process ->
                if (process.isSuccessful) {
                    val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun registration() {
        findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    private fun reset() {
        findNavController().navigate(R.id.action_loginFragment_to_resetFragment)
    }
}