package com.lazysloth.pocketlog.ui.screen.other

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.util.TableInfo
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme

@Composable
fun SettingsScreen(
    onClickLogout: () -> Unit,
) {

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Theme"
        )
        Text(
            text = "Logout",
            Modifier.clickable(onClick = { onClickLogout() })
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun SettingPreview() {
    PocketLogTheme() {
        SettingsScreen(onClickLogout = {})
    }
}