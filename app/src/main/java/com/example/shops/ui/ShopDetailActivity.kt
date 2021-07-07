package com.example.shops.ui

import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shops.ui.shoplist.Shop
import com.example.shops.ui.theme.Closed
import com.example.shops.ui.theme.LightRose
import com.example.shops.ui.theme.Open
import com.example.shops.utils.Utils
import com.google.android.material.shape.Shapeable
import dagger.hilt.android.AndroidEntryPoint


import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.RectangleShape
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.shops.R
import com.example.shops.ui.shoplist.ShopListViewModel
import com.example.shops.utils.Config
import com.example.shops.utils.Resource
import timber.log.Timber

@AndroidEntryPoint
class ShopDetailActivity : ComponentActivity() {

    private val viewModel: ShopListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {

                val id = intent.getIntExtra(Config.ID, 0)
                viewModel.setStateShop("hotels/hotels/$id")
                ShopDetailFireView(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ShopDetailFireView(viewModel: ShopListViewModel) {

    val data: Resource<Shop>? = viewModel.dataStateShop.observeAsState().value
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        data?.let {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Timber.d("loading...")
                    CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
                }
                Resource.Status.SUCCESS -> {
                    Timber.d("success: ${it.data}")

                    it.data?.let { it1 -> ShopViewDetail(it1) }
                }
                Resource.Status.ERROR -> {
                    Timber.e("error: $${it.message}")

                }

            }
        }


    }
}

@Composable
fun ShopViewDetail(data: Shop) {
    Text(
        text = data.name ?: "--",
        fontSize = 30.sp,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier
            .padding(top = 8.dp),
        fontWeight = FontWeight.SemiBold,
        color = Color.DarkGray
    )

    Image(
        painter = painterResource(R.drawable.header),
        contentDescription = null,
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth()
            .padding(16.dp)
            .clip(shape = RoundedCornerShape(20.dp)),
        contentScale = ContentScale.Crop,
    )

    Box(
    ) {

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 18.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .height(350.dp)
                .fillMaxWidth()
        ) {

            val list = mutableListOf<String>()

            list.add("07:00 AM - 11:00 PM")
            list.add("01:00 PM - 02:00 PM")
            list.add("07:00 PM - 10:00 PM")

            for (i in 1..20) {
                list.add("07:00 PM - 10:00 PM")

            }

            data.time?.let { list ->
                TimeListView(list = list)
            }
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(120.dp)
                .padding(end = 8.dp)
                .align(Alignment.BottomCenter)
                .clip(shape = RoundedCornerShape(50)),
            colors = ButtonDefaults
                .buttonColors(backgroundColor = if (data.status == true) Open else Closed),
            shape = MaterialTheme.shapes.medium,
        ) {
            Text(text = if (data.status == true) "Open" else "Closed", color = Color.White)
        }
    }
}

@Composable
fun TimeListView(list: List<Shop.TimeShop>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 20.dp, bottom = 30.dp)
        ) {

            LazyColumn() {
                items(list.size) { position ->
//            TimeView(time = data)
                    ConstraintLayoutDemo(
                        time = list[position],
                        position = position,
                        count = list.size - 1
                    )
                }

            }
        }
    }

}


@Composable
fun TimeView(time: String) {
    /*Box() {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp, end = 12.dp)
        )
        {

            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clip(shape = CircleShape)
                    .size(25.dp)
                    .background(Color.Red)
            )
            Text(
                text = time,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }*/
}

@Composable
fun ConstraintLayoutDemo(time: Shop.TimeShop, position: Int, count: Int) {
    ConstraintLayout() {
        val (roundView, textTime, recView) = createRefs()

        Box(Modifier
            .constrainAs(roundView) {
            }
            .clip(shape = CircleShape)
            .size(25.dp)
            .background(Color.Red)
        )

        Text(
            text = "${time.from ?: "--"} - ${time.to ?: "--"}", modifier = Modifier
                .constrainAs(textTime) {
                    start.linkTo(roundView.end, margin = 8.dp)
                    top.linkTo(roundView.top)
                    bottom.linkTo(roundView.bottom)
                },
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif
        )

        if (position != count)
            Box(Modifier
                .constrainAs(recView) {
                    start.linkTo(roundView.start)
                    end.linkTo(roundView.end)
                    top.linkTo(roundView.bottom)
                }
                .clip(shape = RectangleShape)
                .height(26.dp)
                .width(2.dp)
                .background(Color.DarkGray)
            )
    }
}

