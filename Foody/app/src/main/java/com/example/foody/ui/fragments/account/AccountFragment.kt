package com.example.foody.ui.fragments.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.foody.R
import com.example.foody.databinding.FragmentAccountBinding
import com.example.foody.databinding.FragmentRecipesBinding
import com.example.foody.viewmodels.AccountViewModel
import com.example.foody.viewmodels.MainViewModel
import com.example.foody.viewmodels.RecipesViewModel
import kotlinx.coroutines.launch

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountViewModel = ViewModelProvider(requireActivity()).get(AccountViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.accountViewModel = accountViewModel

        binding.logoutButton.setOnClickListener {
            //TODO: DO LOGOUT
        }

        binding.loginButton.setOnClickListener {
            loginUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        accountViewModel.isUserLoggedIn.observe(viewLifecycleOwner, {
            if(it) {
                findNavController().navigate(R.id.action_accountFragment_to_recipesFragment)
            }
        })

        return binding.root
    }

    private fun loginUser(email: String, password: String) {
        lifecycleScope.launch {
            accountViewModel.loginUser(email, password)
        }
    }

}