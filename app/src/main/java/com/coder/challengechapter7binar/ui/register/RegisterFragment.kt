package com.coder.challengechapter7binar.ui.register

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.coder.challengechapter7binar.R
import com.coder.challengechapter7binar.data.room.entity.UserEntity
import com.coder.challengechapter7binar.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val imageUri: Uri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + resources.getResourcePackageName(R.drawable.default_profile)
                        + '/' + resources.getResourceTypeName(R.drawable.default_profile) + '/'
                        + resources.getResourceEntryName(R.drawable.default_profile)
            )
            val username = binding.etUsername.text
            val email = binding.etEmail.text
            val password = binding.etPassword.text
            val konfirmasiPassword = binding.etKonfirmasiPassword.text

            when {
                username.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), getString(R.string.name_belum_diisi), Toast.LENGTH_SHORT).show()
//                    binding.ilUsername.error = getString(R.string.name_belum_diisi)
                }
                email.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), getString(R.string.email_belum_diisi), Toast.LENGTH_SHORT).show()
//                    binding.ilEmail.error = getString(R.string.email_belum_diisi)
                }
                password.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), getString(R.string.password_belum_diisi), Toast.LENGTH_SHORT).show()
//                    binding.ilPassword.error = getString(R.string.password_belum_diisi)
                }
                konfirmasiPassword.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), getString(R.string.konfirmasi_password_belum_diisi), Toast.LENGTH_SHORT).show()
//                    binding.ilKonfirmasiPassword.error = getString(R.string.konfirmasi_password_belum_diisi)
                }
                password.toString().lowercase() != konfirmasiPassword.toString().lowercase() -> {
                    Toast.makeText(requireContext(), getString(R.string.password_tidak_sama), Toast.LENGTH_SHORT).show()
//                    binding.ilKonfirmasiPassword.error = getString(R.string.password_tidak_sama)
                }
                else -> {
                    val user = UserEntity(null, username.toString(), email.toString(), password.toString(), imageUri.toString())
                    registerViewModel.addUser(user)
                    registerViewModel.register.observe(viewLifecycleOwner) {
                        if (it != 0.toLong()) {
                            Toast.makeText(context, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        } else {
                            val snackbar = Snackbar.make(binding.root,"Registrasi gagal, coba lagi nanti!", Snackbar.LENGTH_INDEFINITE)
                            snackbar.setAction("Oke") {
                                snackbar.dismiss()
                            }
                            snackbar.show()
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