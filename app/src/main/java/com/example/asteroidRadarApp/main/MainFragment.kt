package com.example.asteroidRadarApp.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.asteroidRadarApp.adapter.AsteroidAdapter
import com.example.asteroidRadarApp.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        val viewModel: MainViewModel by activityViewModels()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = AsteroidAdapter {
            findNavController().navigate(
                MainFragmentDirections.actionShowDetail(it)
            )
        }

        binding.RecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner){
            it?.let {
                adapter.submitList(it)
            }
        }

        viewModel.add()

        return binding.root
    }
}