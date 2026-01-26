package com.lazysloth.pocketlog.ui

import com.lazysloth.pocketlog.R

enum class ApplicationBottomNavigation(
    val label : String,
    val icon : Int
) {
    HOME("Home", R.drawable.home_24px),
    PROFILE("Profile", R.drawable.account_circle_24px),
    STATS("Stats", icon = R.drawable.pie_chart_24px),
    ACCOUNT("Account",R.drawable.account_balance_24px)
}