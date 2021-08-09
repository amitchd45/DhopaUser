package com.dhopa.dhopaservice.sidemenu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dhopa.dhopaservice.databinding.FragmentWalletBinding
import com.dhopa.dhopaservice.sidemenu.adapters.AdapterTransaction

class WalletFragment : Fragment() {

    private lateinit var binding: FragmentWalletBinding
    private lateinit var view1:View
    private lateinit var adapter: AdapterTransaction

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWalletBinding.inflate(inflater,container,false)
        view1=binding.root

        setAdapter()
        init()

        return view1
    }

    private fun init() {
        binding.ivTransfer.setOnClickListener {

        }
    }

    private fun setAdapter() {
        adapter= AdapterTransaction()
        binding.rvTransaction.adapter=adapter
    }
}