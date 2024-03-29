package team.retum.savage_android_r.feature.post

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import team.retum.savage_android_r.data.RetrofitClient
import team.retum.savage_android_r.feature.onboarding.join.getAddress
import team.retum.savage_android_r.feature.onboarding.join.locationPermissions
import team.retum.savage_android_r.feature.root.NavGroup
import team.retum.savage_android_r.model.request.RequestLocation
import team.retum.savage_android_r.model.request.WritePostRequest
import team.retum.savage_android_r.ui.component.SavageAppBar
import team.retum.savage_android_r.ui.component.SavageButton
import team.retum.savage_android_r.ui.component.SavageTextFieldWithEditBtn
import team.retum.savage_android_r.ui.theme.savageClickable
import team.retum.savage_android_r.util.Constant
import team.retum.savage_android_r.util.PermissionUtil

class PostViewModel : ViewModel() {
    var doing by mutableStateOf("")
}

@Composable
fun PostScreen(
    navController: NavController
) {
    val postViewModel: PostViewModel = viewModel()
    val context = LocalContext.current
    var latitude by remember {
        mutableStateOf<Double?>(null)
    }

    var longitude by remember {
        mutableStateOf<Double?>(null)
    }

    var tel by remember { mutableStateOf("010-7702-2563") }
    var name by remember { mutableStateOf("도현욱") }
    var location by remember {
        mutableStateOf(
            "잠시만 기다려주세요"
        )
    }
    var doing by remember { mutableStateOf(postViewModel.doing) }
    var startTime by remember { mutableStateOf("0:00") }
    var endTime by remember { mutableStateOf("0:00") }
    var pay by remember { mutableStateOf("0") }
    var payType by remember { mutableStateOf("일 당") }

    var isAllowLocationPermission by rememberSaveable { mutableStateOf(false) }

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        if (permissionsMap.values.isEmpty()) return@rememberLauncherForActivityResult
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        isAllowLocationPermission = areGranted
    }


    LaunchedEffect(true) {
        PermissionUtil.requestPermissions(context, locationPermissions, launcherMultiplePermissions)
    }
    val coroutine = rememberCoroutineScope()
    // 마지막 위치 불러오는 함수
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locations: LocationResult) {
            for (l in locations.locations) {
//                currentLocation = LatLng.from(location.latitude, location.longitude)
                latitude = l.latitude
                longitude = l.longitude
                location = if (latitude == null || longitude == null) "잠시만 기다려주세요" else getAddress(
                    context,
                    latitude!!,
                    longitude!!
                )
                Log.d(
                    Constant.TAG,
                    "w - ${l.latitude} g - ${l.longitude} - onLocationResult() called"
                )
            }
        }
    }

    // 내 위치 조회 요청 클라이언트
    val fusedLocationClient by remember {
        mutableStateOf(
            LocationServices.getFusedLocationProviderClient(
                context
            )
        )
    }

    fun getAllowLocationPermission() = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    fun locationUpdate() {
        if (!getAllowLocationPermission()) return

        val locationRequest = LocationRequest.Builder(1000) // 1초마다 체크
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    val currentBackStackEntry = navController.currentBackStackEntry

    // 이전 화면에서 전달한 값을 arguments를 통해 읽어옴
    val selectedTime = currentBackStackEntry?.arguments?.getString("selectedTime")
    val selectedPay = currentBackStackEntry?.arguments?.getString("total")

    if (selectedPay != null) {
        // UI에 표시하는 예시
        pay = selectedPay.split("/")[0]
        payType = selectedPay.split("/")[1]
    }

    if (selectedTime != null) {
        // UI에 표시하는 예시
        startTime = selectedTime.split("~")[0]
        endTime = selectedTime.split("~")[1]
    }


    LaunchedEffect(isAllowLocationPermission) {
        if (isAllowLocationPermission) {
            locationUpdate()
        }
    }

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        SavageAppBar(callback = {
            navController.navigateUp()
        }, title = "노동자 모집") {
            SavageTextFieldWithEditBtn(
                title = "현재 위치",
                value = location,
                onValueChange = { location = it },
                editBtnValue = "지도 보기",
                isTextField = false,
            )
            SavageTextFieldWithEditBtn(
                title = "전화번호",
                value = tel,
                hint = "전화번호를 입력해주세요",
                onValueChange = { tel = it })
            SavageTextFieldWithEditBtn(
                title = "이름",
                value = name,
                hint = "이름을 입력해주세요",
                onValueChange = { name = it })
            SavageTextFieldWithEditBtn(
                title = "하는 일",
                value = doing,
                hint = "필요한 일을 입력해주세요",
                onValueChange = {
                    postViewModel.doing = it
                    doing = it
                })
            SavageTextFieldWithEditBtn(
                title = "시간",
                value = "$startTime ~ $endTime",
                onValueChange = {
                    startTime = it.split("~")[0]
                    endTime = it.split("~")[1]
                },
                isTextField = false,
                onclick = {
                    navController.navigate(NavGroup.Main.PostTime.id)
                }
            )
            SavageTextFieldWithEditBtn(
                title = "임금 기준",
                value = "$pay / $payType",
                onValueChange = {
                    pay = it.split("/")[0]
                    payType = it.split("/")[1]
                },
                isTextField = false,
                onclick = {
                    navController.navigate(NavGroup.Main.PostPay.id)
                }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box {
            SavageButton(
                onClick = {
                    coroutine.launch {
                        RetrofitClient.postApi.writePost(
                            WritePostRequest(
                                location = listOf(RequestLocation(
                                    location = location,
                                    latitude = latitude ?: 0.0,
                                    longitude = longitude ?: 0.0,
                                )),
                                todo = doing,
                                payment = pay.toInt(),
                                unit = payType,
                                time = "$startTime ~ $endTime"
                            )
                        )
                    }
                    Toast.makeText(context, "모집이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                },
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 8.dp)
                    .savageClickable { },
                text = "모집하기",
                isAbleClick = tel.isNotEmpty() && name.isNotEmpty() && location.isNotEmpty() && doing.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty() && pay.isNotEmpty() && payType.isNotEmpty()
            )
        }
    }

}
