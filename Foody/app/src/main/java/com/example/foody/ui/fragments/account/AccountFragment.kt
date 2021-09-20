package com.example.foody.ui.fragments.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import coil.load
import com.example.foody.R
import com.example.foody.databinding.FragmentAccountBinding
import com.example.foody.databinding.FragmentRecipesBinding
import com.example.foody.viewmodels.AccountViewModel
import com.example.foody.viewmodels.MainViewModel
import com.example.foody.viewmodels.RecipesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
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
            accountViewModel.logout()
            hideAccountCredentials()
            showLoginCredentials()
            showSnackBar("Logged out successfully")
        }

        binding.loginButton.setOnClickListener {
            loginUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        accountViewModel.isUserLoggedIn.observe(viewLifecycleOwner, {
            if (it) {
//                findNavController().navigate(R.id.action_accountFragment_to_recipesFragment)
                showSnackBar("Logged in successfully")
                hideLoginCredentials()
                showAccountCredentials()
            }
        })

        return binding.root
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setAction("Okay") {}
            .show()
    }

    private fun loginUser(email: String, password: String) {
        accountViewModel.login(email, password)
    }

    private fun hideLoginCredentials() {
        binding.tilEmail.visibility = View.INVISIBLE
        binding.tilPassword.visibility = View.INVISIBLE
        binding.loginButton.visibility = View.INVISIBLE
    }

    private fun showLoginCredentials() {
        binding.tilEmail.visibility = View.VISIBLE
        binding.tilPassword.visibility = View.VISIBLE
        binding.loginButton.visibility = View.VISIBLE
        binding.userProfilePicture.setImageResource(R.drawable.ic_account_square)
    }

    private fun showAccountCredentials() {
        binding.userPhoneNumber.visibility = View.VISIBLE
        binding.userEmail.visibility = View.VISIBLE
        binding.userFullName.visibility = View.VISIBLE
        binding.logoutButton.visibility = View.VISIBLE
        binding.userProfilePicture.load(accountViewModel.loggedInUser.value!!.profileImage) {
            crossfade(600)
            error(R.drawable.ic_error)
        }
    }

    private fun hideAccountCredentials() {
        binding.userPhoneNumber.visibility = View.INVISIBLE
        binding.userEmail.visibility = View.INVISIBLE
        binding.userFullName.visibility = View.INVISIBLE
        binding.logoutButton.visibility = View.INVISIBLE

    }

}