package com.coder.challengechapter7binar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import com.coder.challengechapter7binar.R
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coder.challengechapter7binar.ui.theme.ChallengeChapter7BinarTheme
import com.coder.challengechapter7binar.ui.theme.colorPrimaryDark

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeChapter7BinarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorPrimaryDark
                ) {
                    Column {
                        Splash()
                    }
                }
            }
        }
    }

    private val montserratBold = FontFamily(
        Font(R.font.montserrat_bold)
    )

    @Composable
    fun Splash() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(360.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_movie),
                contentDescription = "Image App",
                modifier = Modifier.size(80.dp, 80.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "movieapp",
                fontSize = 36.sp,
                color = Color.White,
                fontFamily = montserratBold
            )

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
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
                    Splash()
                }
            }
        }
    }
}