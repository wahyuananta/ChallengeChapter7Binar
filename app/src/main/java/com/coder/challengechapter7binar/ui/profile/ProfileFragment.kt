package com.coder.challengechapter7binar.ui.profile

import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coder.challengechapter7binar.R
import com.coder.challengechapter7binar.data.room.entity.UserEntity
import com.coder.challengechapter7binar.databinding.FragmentProfileBinding
import com.coder.challengechapter7binar.ui.home.HomeViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
//    private lateinit var repository: UserRepository
    private val args : ProfileFragmentArgs by navArgs()
//    private lateinit var pref: UserDataStoreManager
    private var imageUri: Uri? = null

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    imageUri = data?.data
                    loadImage(imageUri)

                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }
        }

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
//        repository = UserRepository(requireContext())
//
//        pref = UserDataStoreManager(requireContext())

        binding.profileImage.setImageURI(args.dataUser.uri.toString().toUri())
        binding.etUsername.setText(args.dataUser.username)
        binding.etUsername.isFocusable = false
        binding.etEmail.setText(args.dataUser.email)
        binding.etPassword.setText(args.dataUser.password)

        binding.profileImage.setOnClickListener {
            openImagePicker()
        }

        binding.btnUpdate.setOnClickListener {
            val user = UserEntity(
                args.dataUser.id,
                binding.etUsername.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                imageUri.toString()
            )
            homeViewModel.updateUser(user)
            homeViewModel.resultUpdate.observe(viewLifecycleOwner) {
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
//            lifecycleScope.launch(Dispatchers.IO) {
//                val result = repository.updateUser(user)
//                runBlocking(Dispatchers.Main) {
//                    if (result != 0){
//                        Toast.makeText(requireContext(), "Profile berhasil diupdate", Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(requireContext(), "Profile gagal diupdate", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
                findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
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
                        homeViewModel.deleteUserFromPref()
//                        lifecycleScope.launch(Dispatchers.IO) {
//                            pref.deleteUserFromPref()
//                        }
                        findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                    }
                    .setNegativeButton("Batal") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    requireContext().externalCacheDir,
                    "ImagePicker"
                )
            ) //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private fun loadImage(uri: Uri?) {
        uri?.let {
            binding.profileImage.setImageURI(it)
        }
    }

}