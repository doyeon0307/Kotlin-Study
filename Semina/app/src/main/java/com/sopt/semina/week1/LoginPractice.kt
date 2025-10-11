package com.sopt.semina.week1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sopt.semina.Greeting
import com.sopt.semina.ui.theme.SeminaTheme

@Composable
fun LoginPractice(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 40.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            "Welcome To Sopt",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
            )
        )
        Column {
            LoginTextField(
                label = "ID",
                placeholder = "사용자 이름 입력",
            )
            Spacer(Modifier.height(16.dp))
            LoginTextField(
                label = "비밀번호",
                placeholder = "비밀번호 입력",
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "회원가입하기",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray
                ),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(true) {
                    val intent = Intent(context, RegisterActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                }
            )
            Spacer(Modifier.height(16.dp))
            LoginButton(
                label = "로그인 하기",
                onClick = {}
            )
        }
    }
}

@Preview()
@Composable
fun LoginPracticePreview() {
    LoginPractice()
}

@Composable
fun LoginTextField(
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier
    ) {
        Text(
            label,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = {
                Text(
                    placeholder,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    )
                )
            },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun LoginTextFieldPreview() {
    LoginTextField(
        label = "ID",
        placeholder = "사용자 이름 입력",
    )
}

@Composable
fun LoginButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
    ) {
        Text(
            label,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
            )
        )
    }
}

@Preview
@Composable
fun LoginButtonPreview() {
    LoginButton(
        label = "로그인 하기",
        onClick = {},
    )
}

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeminaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
