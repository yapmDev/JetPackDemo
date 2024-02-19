package com.example.jetpackdemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackdemo.ui.theme.JetPackDemoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackDemoTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 3.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(HomeViewModel())
                }
            }
        }
    }
}

@Composable
fun MyApp(homeVM: HomeViewModel = viewModel()) {

    var value by rememberSaveable { mutableStateOf("") }
    var asGrid by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(value, { value = it }, {
            asGrid = !asGrid
        }) },
        bottomBar = { BottomAppBar() }) {
        if (asGrid) GreetingGridList(
            homeVM.list,
            filter = value,
            modifier = Modifier.padding(it),
            homeVM
        )
        else GreetingColumnList(
            homeVM.list,
            filter = value,
            modifier = Modifier.padding(it),
            homeVM
        )
    }
}

@Composable
fun TopAppBar(value: String, onValueChange: (String) -> Unit, onList: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary)
            .fillMaxWidth(1f)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                leadingIcon = {
                    Icon(
                        Icons.Default.Search, null, tint = MaterialTheme.colorScheme.primary
                    )
                },
                placeholder = { Text(text = "Search", color = MaterialTheme.colorScheme.primary) },
                onValueChange = onValueChange,
                maxLines = 2,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.primary
                ),
                //modifier = Modifier.border(BorderStroke(2.dp, Brush.linearGradient())),
                shape = CutCornerShape(12.dp)
            )
            IconButton(
                onClick = onList
            ) {
                Icon(
                    Icons.Default.List,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun GreetingGridList(
    list: MutableList<String>,
    filter: String,
    modifier: Modifier,
    homeVM: HomeViewModel
) {
    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    Box {
        LazyVerticalGrid(
            modifier = modifier,
            state = listState,
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            items(list) {
                if (it.contains(filter)) GreetingItem(text = it) { homeVM.onRemove(it) }
            }
        }
        IconButton(
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            },
            modifier.align(Alignment.BottomCenter)
        ) {
            Icon(
                Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun GreetingColumnList(
    list: MutableList<String>,
    filter: String,
    modifier: Modifier,
    homeVM: HomeViewModel
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Box {
        LazyColumn(
            modifier = modifier,
            state = listState,
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            items(list) {
                if (it.contains(filter)) GreetingItem(text = it) { homeVM.onRemove(it) }
            }
        }
        IconButton(
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            },
            modifier.align(Alignment.BottomCenter)
        ) {
            Icon(
                Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun GreetingItem(text: String, onRemove: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.primary)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.weight(0.1f))
        Text(
            text = text,
            modifier = Modifier.weight(0.5f),
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.weight(0.1f))
        IconButton(onClick = onRemove, Modifier.weight(0.3f)) {
            Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun BottomAppBar() {
    val context = LocalContext.current
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        NavigationBarItem(selected = true,
            onClick = { Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show() },
            label = { Text(text = "Home", color = MaterialTheme.colorScheme.primary) },
            icon = {
                Icon(
                    Icons.Default.Home, null, tint = MaterialTheme.colorScheme.primary
                )
            })

        NavigationBarItem(selected = true,
            onClick = { Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show() },
            label = { Text(text = "Profile", color = MaterialTheme.colorScheme.primary) },
            icon = {
                Icon(
                    Icons.Default.AccountCircle, null, tint = MaterialTheme.colorScheme.primary
                )
            })

        NavigationBarItem(selected = true,
            onClick = { Toast.makeText(context, "Setting", Toast.LENGTH_SHORT).show() },
            label = { Text(text = "Setting", color = MaterialTheme.colorScheme.primary) },
            icon = {
                Icon(
                    Icons.Default.Settings, null, tint = MaterialTheme.colorScheme.primary
                )
            })
    }
}