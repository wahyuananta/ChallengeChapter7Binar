package com.coder.challengechapter7binar.ui.login

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.coder.challengechapter7binar.R
import com.coder.challengechapter7binar.data.datastore.UserDataStoreManager
import com.coder.challengechapter7binar.ui.theme.ChallengeChapter7BinarTheme
import com.coder.challengechapter7binar.ui.theme.colorAccent
import com.coder.challengechapter7binar.ui.theme.colorPrimaryDark
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModels()
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
            loginViewModel.getDataStore()
            loginViewModel.userDataStore.observe(viewLifecycleOwner) {
                if (it.id != UserDataStoreManager.DEFAULT_ID) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    setContent {
                        ChallengeChapter7BinarTheme {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = colorPrimaryDark
                            ) {
                                Column {
                                    Header()
                                    ActionLogin()
                                }
                            }
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
                text = "Login",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 32.sp,
                    fontFamily = montserratBold
                )
            )
        }
    }


    @Composable
    fun ActionLogin() {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Spacer(
            modifier = Modifier
                .height(64.dp)
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier.fillMaxWidth()
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (username == "" || password == "") {
                        AlertDialog.Builder(requireContext())
                            .setTitle("")
                            .setMessage("Username atau Password tidak boleh kosong")
                            .setPositiveButton("Coba login kembali") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .show()
                    } else {
                        loginViewModel.getUser(username)
                        loginViewModel.login.observe(viewLifecycleOwner) {
                            if (it == null) {
                                Toast.makeText(
                                    requireContext(),
                                    "Login gagal, coba periksa email atau password anda",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                loginViewModel.saveDataStore(it)
                                Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorAccent),
                modifier = Modifier.width(260.dp)
            ) {
                Text(
                    text = "Login",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = montserratBold
                    ),
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
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

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(200.dp))
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
                    ActionLogin()
                }
            }
        }
    }
}