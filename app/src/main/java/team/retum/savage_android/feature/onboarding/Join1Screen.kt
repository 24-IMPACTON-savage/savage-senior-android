package team.retum.savage_android.feature.onboarding

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
import team.retum.savage_android.ui.component.SavageAppBar
import team.retum.savage_android.ui.component.SavageButton
import team.retum.savage_android.ui.component.SavageTextField
import team.retum.savage_android.ui.theme.SavageColor
import team.retum.savage_android.ui.theme.SavageTypography

@Composable
private fun Title() {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp)
    ) {
        Text(
            text = "안녕하세요! ",
            style = SavageTypography.HeadLine1
        )
        Text(
            text = "성함",
            style = SavageTypography.HeadLine1,
            color = SavageColor.Primary40
        )
        Text(
            text = "을 알려주세요!",
            style = SavageTypography.HeadLine1
        )
    }
}

@Composable
fun Join1Screen(
    navController: NavController
) {

    var name by remember { mutableStateOf("") }

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
            SavageTextField(value = name, hint = "성함을 입력해 주세요.", onValueChange = { name = it })
            Spacer(modifier = Modifier.weight(1f))
            SavageButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = {

                },
                text = "다음",
                isAbleClick = true
            )
            Spacer(modifier = Modifier.padding(bottom = 24.dp))
        }

    }

}