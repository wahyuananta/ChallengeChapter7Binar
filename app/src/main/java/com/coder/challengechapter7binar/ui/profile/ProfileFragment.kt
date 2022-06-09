package com.coder.challengechapter7binar.ui.profile

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coder.challengechapter7binar.R
import com.coder.challengechapter7binar.data.room.entity.UserEntity
import com.coder.challengechapter7binar.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private val args : ProfileFragmentArgs by navArgs()
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etUsername.setText(args.dataUser.username)
        binding.etUsername.isFocusable = false
        binding.etEmail.setText(args.dataUser.email)
        binding.etPassword.setText(args.dataUser.password)

        binding.btnUpdate.setOnClickListener {
            val user = UserEntity(
                args.dataUser.id,
                binding.etUsername.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                imageUri.toString()
            )
            profileViewModel.updateUser(user)
            profileViewModel.update.observe(viewLifecycleOwner) {
                if (it != null) {
                    if (it != 0) {
                        Toast.makeText(
                            requireContext(), "User berhasil diupdate",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(requireContext(), "User gagal diupdate", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah anda yakin ingin logout?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dialog.dismiss()
                    profileViewModel.deleteUserFromPref()
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}