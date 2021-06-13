package com.example.shualeduri.fragments

import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shualeduri.R
import com.example.shualeduri.adapters.RecyclerViewAdapter
import com.example.shualeduri.databinding.FragmentHistoryBinding
import com.example.shualeduri.model.IpModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HistoryFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {

        getFromDB()
        recyclerInit()

        binding.clearButton.setOnClickListener {
            adapter.clearItems()
            db.child(mAuth.currentUser?.uid!!).removeValue()
        }

    }

    private fun recyclerInit() {
        adapter = RecyclerViewAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun getFromDB() {
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("IpInfo")


        if (mAuth.currentUser?.uid != null) {
            db.child(mAuth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val ipContainer = mutableListOf<IpModel>()
                    for (data in snapshot.children) {
                        ipContainer.add(data.getValue(IpModel::class.java) as IpModel)
                    }
                    adapter.addItems(ipContainer.toMutableSet().toMutableList())
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}