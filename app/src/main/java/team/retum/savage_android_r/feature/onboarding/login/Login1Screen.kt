package team.retum.savage_android_r.feature.onboarding.login

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import team.retum.savage_android_r.feature.root.NavGroup
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
            text = "로그인을 위해 성함을 입력해 주세요.",
            style = SavageTypography.HeadLine1
        )
    }
}

@Composable
fun Login1Screen(
    navController: NavController
) {

    var name by remember { mutableStateOf("") }
    val keyboardShow by rememberKeyboardIsOpen()

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
            SavageTextField(modifier = Modifier.padding(horizontal = 20.dp), value = name, hint = "성함을 입력해 주세요.", onValueChange = { name = it })
            Spacer(modifier = Modifier.weight(1f))
            SavageButton(
                modifier = if (!keyboardShow) Modifier.padding(horizontal = 16.dp) else Modifier,
                onClick = {
                    if (name.isNotBlank()) {
                        navController.navigate(NavGroup.Onboarding.Login2.id + "/${name}")
                    } else {
                        // handling
                    }
                },
                text = "다음",
                isAbleClick = name.isNotBlank(),
                isKeyShow = keyboardShow
            )
            if (!keyboardShow)
                Spacer(modifier = Modifier.padding(bottom = 24.dp))
        }

    }
}