package team.retum.savage_android_r.feature.root

sealed class NavGroup(val group: String) {
    sealed class Onboarding(val id: String) : NavGroup("onboarding") {
        data object Start : Onboarding(id = "start")
        data object Join1 : Onboarding(id = "join1")
        data object Join2 : Onboarding(id = "join2")
        data object Login1 : Onboarding(id = "login1")
        data object Login2 : Onboarding(id = "login2")
    }

    sealed class Main(val id: String) : NavGroup("main") {
        data object Map : Main(id = "map")
        data object Loading : Main(id = "loading")
        data object Complete : Main(id = "complete")
        data object Post : Main(id = "post")
        data object PostTime : Main(id = "posttime")
        data object PostPay : Main(id = "postpay")
        data object MatchingWorker : Main(id = "matchingworker")

        data object Setting : Main(id = "setting")
        data object Membership : Main(id = "membership")
    }
}