package com.example.asteroidRadarApp.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteroidRadarApp.adapter.AsteroidAdapter
import com.example.asteroidRadarApp.databinding.FragmentMainBinding
import com.example.asteroidRadarApp.model.AsteroidModel


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)

        val viewModel: MainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(requireContext())
        )[MainViewModel::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.RecyclerView.adapter = AsteroidAdapter {
            findNavController().navigate(
                MainFragmentDirections.actionShowDetail(it)
            )
        }

        return binding.root
    }
}