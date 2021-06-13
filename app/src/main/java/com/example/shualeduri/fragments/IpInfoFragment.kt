package com.example.shualeduri.fragments

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shualeduri.databinding.FragmentIpInfoBinding
import com.example.shualeduri.extensions.hide
import com.example.shualeduri.extensions.show
import com.example.shualeduri.model.IpModel
import com.example.shualeduri.model.ResultHandle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class IpInfoFragment : Fragment() {

    private lateinit var binding: FragmentIpInfoBinding
    private lateinit var mAuth: FirebaseAuth

    private val viewModel: MainViewModel by viewModels()
    val args: IpInfoFragmentArgs by navArgs()

    private lateinit var db: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIpInfoBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {

        viewModel.init(args.ipaddress)
        observers()

        binding.location.setOnClickListener {
            toMap()
        }
    }

    private fun observers() {

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("IpInfo")

        binding.progressBar.show()
        viewModel.IpInfo.observe(viewLifecycleOwner, { response ->
            when (response.status) {
                ResultHandle.Companion.Status.SUCCESS -> {
                    successCase(response)
                    binding.progressBar.hide()
                }

                ResultHandle.Companion.Status.ERROR -> {
                    Toast.makeText(requireContext(), response.error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun successCase(response: ResultHandle<IpModel>) {

        val info = response.data
        binding.ipTextView.text = info?.ip
        binding.cityTextView.text = info?.city
        binding.regionTextView.text = info?.region
        binding.countryTextView.text = info?.country
        binding.locationTextView.text = info?.loc
        binding.orgTextView.text = info?.org
        binding.timezoneTextView.text = info?.timezone

        setToDB()

    }

    private fun setToDB() {

        val setModel = IpModel(
            binding.cityTextView.text.toString(),
            binding.countryTextView.text.toString(),
            binding.ipTextView.text.toString(),
            binding.locationTextView.text.toString(),
            binding.orgTextView.text.toString(),
            binding.regionTextView.text.toString(),
            binding.timezoneTextView.text.toString()
        )

        if (mAuth.currentUser?.uid != null) {

            db.child(mAuth.currentUser?.uid!!).child(db.push().key.toString()).setValue(setModel).addOnCompleteListener { process ->
                if (process.isSuccessful) {
                    Toast.makeText(requireContext(), "მივაგენით", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun toMap() {
        if (binding.locationTextView.text.isNotEmpty()) {
            val container = binding.locationTextView.text.split(",")
            val action = IpInfoFragmentDirections.actionIpInfoFragmentToMapsFragment2(container[0].toFloat(), container[1].toFloat())
            findNavController().navigate(action)
        }
    }
}