package team.retum.savage_android_r.feature.onboarding.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import team.retum.savage_android_r.application.SavageApp
import kotlinx.coroutines.launch
import team.retum.savage_android_r.data.RetrofitClient
import team.retum.savage_android_r.feature.root.NavGroup
import team.retum.savage_android_r.model.request.SignInRequest
import team.retum.savage_android_r.ui.component.SavageAppBar
import team.retum.savage_android_r.ui.component.SavageButton
import team.retum.savage_android_r.ui.component.SavageTextField
import team.retum.savage_android_r.ui.theme.SavageTypography
import team.retum.savage_android_r.ui.theme.rememberKeyboardIsOpen


@Composable
private fun Title() {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
    ) {
        Text(
            text = "전화번호를 입력해 주세요.",
            style = SavageTypography.HeadLine1
        )
    }
}

@Composable
fun Login2Screen(
    navController: NavController,
    name: String,
) {

    var tel by remember { mutableStateOf("") }
    val keyboardShow by rememberKeyboardIsOpen()
    val coroutine = rememberCoroutineScope()
    SavageAppBar(
        callback = {
            navController.popBackStack()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Title()
            Spacer(modifier = Modifier.padding(top = 48.dp))
            SavageTextField(
                modifier = Modifier.padding(horizontal = 20.dp),
                value = tel,
                hint = "전화번호를 입력해 주세요.",
                onValueChange = { tel = it })
            Spacer(modifier = Modifier.weight(1f))
            SavageButton(
                modifier = if (!keyboardShow) Modifier.padding(horizontal = 16.dp) else Modifier,
                onClick = {
                    if (tel.isNotBlank()) {
                        Log.d("로그", "Login2Screen: $name, $tel")
                        coroutine.launch {
                            val response = RetrofitClient.authApi.signIn(SignInRequest(name, tel))
                            SavageApp.prefs.accessToken = response.data?.accesstoken?: ""
                            navController.navigate(NavGroup.Main.Map.id)
                        }
                    } else {
                        // handling
                    }
                },
                text = "다음",
                isAbleClick = tel.isNotBlank(),
                isKeyShow = keyboardShow
            )
            if (!keyboardShow)
                Spacer(modifier = Modifier.padding(bottom = 24.dp))
        }
    }
}