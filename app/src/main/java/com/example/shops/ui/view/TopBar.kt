package com.example.shops.ui.view

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBarView(string: String, activity: Activity) {
    TopAppBar(

        title = {
            Text(
                text = string,
                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = { activity.onBackPressed() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Menu Btn",
                )
            }
        },
        backgroundColor = Color.Transparent,
        contentColor = Color.DarkGray,
        elevation = 2.dp
    )
}