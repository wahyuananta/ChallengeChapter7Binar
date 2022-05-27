package com.coder.challengechapter7binar.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.coder.challengechapter7binar.R
import com.coder.challengechapter7binar.data.datastore.UserDataStoreManager
import com.coder.challengechapter7binar.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.getDataStore()
        loginViewModel.userDataStore.observe(viewLifecycleOwner) {
            if (it.id != UserDataStoreManager.DEFAULT_ID) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val etUsername = binding.etUsername.text
            val etPassword = binding.etPassword.text

            when {
                etUsername.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), getString(R.string.name_belum_diisi), Toast.LENGTH_SHORT).show()
//                    binding.ilUsername.error = getString(R.string.email_belum_diisi)
                }
                etPassword.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), getString(R.string.password_belum_diisi), Toast.LENGTH_SHORT).show()
//                    binding.ilPassword.error = getString(R.string.password_belum_diisi)
                }
                else -> {
                    loginViewModel.getUser(etUsername.toString())
                    loginViewModel.login.observe(viewLifecycleOwner) {
                        if (it == null) {
                            val snackbar = Snackbar.make(
                                binding.root,
                                "Login gagal, coba periksa email atau password anda",
                                Snackbar.LENGTH_INDEFINITE
                            )
                            snackbar.setAction("Oke") {
                                snackbar.dismiss()
                            }
                            snackbar.show()
                        } else {
                            loginViewModel.saveDataStore(it)
                            Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}