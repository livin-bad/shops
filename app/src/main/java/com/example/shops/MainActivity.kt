package com.example.shops

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shops.ui.ShopDetailActivity
import com.example.shops.ui.TimeView
import com.example.shops.ui.addshop.AddShopActivity
import com.example.shops.ui.shoplist.ShopListViewModel
import com.example.shops.ui.shoplist.Shop
import com.example.shops.ui.theme.Closed
import com.example.shops.ui.theme.Open
import com.example.shops.ui.theme.Purple500
import com.example.shops.utils.Config
import com.example.shops.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.collections.mutableSetOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ShopListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme() {
//                ShopView(data = Shop("Raja shop", true))
//                ListView(list = Utils.getShops())

                Subs(viewModel = viewModel)
                viewModel.setStateShops("hotels/hotels")
            }
        }


    }


}

@Composable
fun Subs(
    viewModel: ShopListViewModel
) {
    val items: Resource<List<Shop>>? = viewModel.dataStateShops.observeAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /* TopAppBar(
             title = {
                 Text(text = "Shops", color = Color.White)
             },
             backgroundColor = Purple500,
             contentColor = Color.Gray,
             elevation = 2.dp,
             navigationIcon = {
                 IconButton(onClick = {}) {
                     Icon(Icons.Filled.Menu, "Menu")
                 }
             },

         )*/

        ToolbarView()

        items?.let {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Timber.d("loading...")
                    CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
                }
                Resource.Status.SUCCESS -> {
                    Timber.d("success: ${it.data}")

                    val list = it.data ?: emptyList()
                    ListView(list.sortedBy { it.name })
                }
                Resource.Status.ERROR -> {
                    Timber.e("error: $${it.message}")

                }

            }
        }

    }

}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun ListView(list: List<Shop>) {
    LazyColumn() {
        items(list) { data ->
            ShopView(data = data)
            Divider(color = Color.LightGray)
        }

    }
}

@Composable
fun ShopView(data: Shop, context: Context = LocalContext.current) {
    Timber.d("DATA: $data")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = {
                    Timber.d("SHOP: $data")
                    context.startActivity(
                        Intent(context, ShopDetailActivity::class.java).putExtra(
                            Config.ID,
                            data.id
                        )
                    )
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.header),
                contentDescription = null,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop,
            )
            Text(text = data.name ?: "--", Modifier.padding(start = 10.dp))


        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(120.dp)
                .padding(end = 8.dp)
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
fun ToolbarView() {

    val context = LocalContext.current
    var showMenu by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        title = {
            Text(text = "Pets Show")
        },
        backgroundColor = Color.Transparent,
        contentColor = Color.DarkGray,
        elevation = 2.dp,
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Menu Btn",
                    tint = Color.DarkGray
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {

                DropdownMenuItem(onClick = {
                    context.startActivity(Intent(context, AddShopActivity::class.java))
                }) {
                    Text(text = "Add Shop")
                }
                DropdownMenuItem(onClick = {
                }) {
//                        Icon(Icons.Filled.Refresh)
                    Text(text = "About")
                }
            }
        }
    )
}

