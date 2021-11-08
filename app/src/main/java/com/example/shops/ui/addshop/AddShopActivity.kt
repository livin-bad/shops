package com.example.shops.ui.addshop

import android.app.Activity
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shops.R
import com.example.shops.ui.ShopDetailFireView
import com.example.shops.ui.view.TopAppBarView
import com.example.shops.utils.Config

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.shops.ShopView
import com.example.shops.ui.shoplist.Shop
import com.example.shops.utils.Utils
import timber.log.Timber
import java.util.*

class AddShopActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                GetShopDetailsView()
            }
        }
    }
}

@Composable
fun GetShopDetailsView() {
//    val activity = LocalContext.current as Activity

    var textShopName by remember { mutableStateOf("") }
    var textShopTiming by remember { mutableStateOf("") }
    var textStartTime by remember { mutableStateOf("Start Time") }
    var textEndTime by remember { mutableStateOf("End Time") }
    var isStartTime by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        TopAppBarView(string = "Add Shop", activity = activity)


        Image(
            painter = painterResource(R.drawable.header),
            contentDescription = "Shop image",
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop,
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            value = textShopName,
            onValueChange = { textShopName = it },
            label = { Text("Shop Name") },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                .fillMaxWidth(),

            )

        Button(
            onClick = {/**/ },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Localized description",
                modifier = Modifier.padding(end = 8.dp),
            )

            Text("Add Location")
        }

        val timePickerDialog = TimePickerDialog(
            LocalContext.current,
            { view, hourOfDay, minute ->
                val time = Utils.getTime(hourOfDay = hourOfDay, minute = minute)
                Timber.d("time: $time")

                if (isStartTime)
                    textStartTime = time
                else
                    textEndTime = time

            }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            OutlinedButton(
                onClick = {
                    timePickerDialog.show()
                    isStartTime = true
                },
                modifier = Modifier
                    .padding(start = 0.dp, end = 16.dp, top = 20.dp)
            ) {

                Text(textStartTime)
            }

            Button(
                onClick = {
                    textStartTime = "Start Time"
                    textEndTime = "End Time"
                    isStartTime = false
                },
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {

                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Localized description",
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                )
            }

            OutlinedButton(
                onClick = {
                    timePickerDialog.show()
                    isStartTime = false
                },
                modifier = Modifier
                    .padding(start = 16.dp, end = 0.dp, top = 20.dp)
            ) {


                Text(textEndTime)
            }

        }

        /* TextTimeView(string = "10:00 AM - 11:00 PM")
         TextTimeView(string = "10:00 AM - 11:00 PM")
         TextTimeView(string = "10:00 AM - 11:00 PM")
         TextTimeView(string = "10:00 AM - 11:00 PM")
         TextTimeView(string = "10:00 AM - 11:00 PM")*/

        val list = mutableListOf<String>()

        list.add("10:00 AM - 12:00 PM")
        list.add("01:00 PM - 03:00 PM")

        ListView(list)

    }

}

@Composable
fun ListView(list: List<String>) {
    LazyColumn() {
        items(list) { data ->
            TextTimeView(string = data)
        }

    }
}

@Composable
fun TextTimeView(string: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth(),
    ) {

        Text(
            text = ""
        )
        Text(
            text = string,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        )

        IconButton(onClick = {
            Log.d("AddShopActivity", "TextTimeView: $string")
        }) {
//            materialIcon()
            Icon(imageVector = Icons.Filled.Delete,
                contentDescription = "Delete Time ",
                tint = Color.Red,
            )
        }
        /*Icon(imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Time ",
            tint = Color.Red,
            modifier = Modifier
                .clickable() {}
                .heightIn(30.dp)
        )*/
    }

}

@Preview(showBackground = true)
@Composable
fun ComposablePreview() {
    GetShopDetailsView()
//    TextTimeView(string = "10:00 AM - 11:00 PM")
}

