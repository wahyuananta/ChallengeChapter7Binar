package com.coder.challengechapter7binar.ui.register

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.coder.challengechapter7binar.R
import com.coder.challengechapter7binar.data.room.entity.UserEntity
import com.coder.challengechapter7binar.ui.theme.ChallengeChapter7BinarTheme
import com.coder.challengechapter7binar.ui.theme.colorAccent
import com.coder.challengechapter7binar.ui.theme.colorPrimaryDark
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val registerViewModel: RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                ChallengeChapter7BinarTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = colorPrimaryDark
                    ) {
                        Column {
                            Header()
                            ActionRegister()
                        }
                    }
                }
            }
        }
    }

    private val montserratBold = FontFamily(
        Font(R.font.montserrat_bold)
    )
    private val montserratRegular = FontFamily(
        Font(R.font.montserrat_regular)
    )

    @Composable
    fun Header() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = "Register",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 32.sp,
                    fontFamily = montserratBold
                )
            )
        }
    }

    @Composable
    fun ActionRegister() {
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        Spacer(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text(text = "Username") },
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = montserratRegular
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "Email") },
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = montserratRegular
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "Password") },
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = montserratRegular
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text(text = "Confirm Password") },
                shape = RoundedCornerShape(16.dp),
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = montserratRegular
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (username == "" || email == "" || password == "" || confirmPassword == "") {
                            AlertDialog.Builder(requireContext())
                                .setTitle("")
                                .setMessage("Kolomtidak boleh kosong")
                                .setPositiveButton("Coba register kembali") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        } else if (password != confirmPassword) {
                            Toast.makeText(
                                requireContext(),
                                "Password konfirmasi tidak sama",
                                Toast.LENGTH_LONG
                            ).show()
                            confirmPassword = ""
                        } else {
                            val user = UserEntity(null, username, email, password, "")
                            registerViewModel.addUser(user)
                            registerViewModel.register.observe(viewLifecycleOwner) {
                                if (it != 0.toLong()) {
                                    Toast.makeText(
                                        context,
                                        "Registrasi berhasil",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Registrasi gagal, coba lagi nanti!",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorAccent),
                    modifier = Modifier.width(260.dp)
                ) {
                    Text(
                        text = "Register",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontFamily = montserratBold
                        ),
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(150.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_movie),
                contentDescription = "Image App",
                modifier = Modifier.size(40.dp, 40.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "movieapp",
                fontSize = 26.sp,
                color = Color.White,
                fontFamily = montserratBold
            )
        }
    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        ChallengeChapter7BinarTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = colorPrimaryDark
            ) {
                Column {
                    Header()
                    ActionRegister()
                }
            }
        }
    }
}