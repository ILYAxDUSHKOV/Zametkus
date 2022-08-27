package com.example.zametkus.presentation.composable.homeScreen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.zametkus.domain.zamData.ZamData
import com.example.zametkus.presentation.ZamViewModel
import com.example.zametkus.presentation.ui.theme.*

@ExperimentalMaterialApi
@Composable
fun HomeScreen(viewModel: ZamViewModel){
    MyScaffold(viewModel = viewModel)
}

@ExperimentalMaterialApi
@Composable
fun MyScaffold(
    viewModel: ZamViewModel
){
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.White
            ){
                Spacer(modifier = Modifier.weight(0.2f,true))
                IconButton(onClick = { /*Здесь будет домашняя страница*/}) {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = null,
                        tint = MyLightGray
                    )
                }
                Spacer(modifier = Modifier.weight(0.6f,true))
                Spacer(modifier = Modifier.weight(0.6f,true))
                IconButton(onClick = { /*Здесь будет свернуть все открытые заметки*/ }) {
                    Icon(
                        Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MyLightGray
                    )
                }
                Spacer(modifier = Modifier.weight(0.2f,true))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.addDialogState.value = true },
                backgroundColor = Color.Black
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        content = {
            MyLazyColumn(viewModel = viewModel)
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun MyLazyColumn(
    viewModel: ZamViewModel
){
    val zamList = viewModel.getAll.observeAsState(listOf()).value
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MyLightGray)
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ){
            item(){
                Column() {
                    Spacer(modifier = Modifier.height(15.dp))
                    Row() {
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Home", fontSize = 40.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Row() {
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Notebooks", fontSize = 18.sp, color = MyGray, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        item(){
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                        item(){
                            Card(
                                modifier = Modifier
                                    .size(150.dp, 150.dp)
                                    .padding(end = 15.dp),
                                shape = RoundedCornerShape(15.dp)
                            ){
                                Box(modifier = Modifier.fillMaxSize()){
                                    Card(
                                        backgroundColor = MyPurple,
                                        modifier = Modifier
                                            .padding(start = 20.dp, top = 30.dp)
                                            .size(12.dp)
                                            .align(Alignment.TopStart)
                                        ,shape = CircleShape
                                    ){}
                                    Text(
                                        text = "Family",
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(20.dp)
                                    )
                                }
                            }
                        }
                        item(){
                            Card(
                                modifier = Modifier
                                    .size(150.dp, 150.dp)
                                    .padding(end = 15.dp),
                                shape = RoundedCornerShape(15.dp)
                            ){
                                Box(modifier = Modifier.fillMaxSize()){
                                    Card(
                                        backgroundColor = MyRed,
                                        modifier = Modifier
                                            .padding(start = 20.dp, top = 30.dp)
                                            .size(12.dp)
                                            .align(Alignment.TopStart)
                                        ,shape = CircleShape
                                    ){}
                                    Text(
                                        text = "Family",
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(20.dp)
                                    )
                                }
                            }
                        }
                        item(){
                            Card(
                                modifier = Modifier
                                    .size(150.dp, 150.dp)
                                    .padding(end = 15.dp),
                                shape = RoundedCornerShape(15.dp)
                            ){
                                Box(modifier = Modifier.fillMaxSize()){
                                    Card(
                                        backgroundColor = MyGreen,
                                        modifier = Modifier
                                            .padding(start = 20.dp, top = 30.dp)
                                            .size(12.dp)
                                            .align(Alignment.TopStart)
                                        ,shape = CircleShape
                                    ){}
                                    Text(
                                        text = "Family",
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(20.dp)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row() {
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Notes", fontSize = 18.sp, color = MyGray, fontWeight = FontWeight.Bold)
                    }
                }
            }
            items(zamList.size){ index ->
                ExpandableCard(zametka = zamList[index], viewModel = viewModel)
            }
            item(){
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                )
            }
        }
        if (viewModel.addDialogState.value){
            AddDialog(viewModel = viewModel)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    zametka:ZamData,
    viewModel: ZamViewModel
){
    //Expandable state
    var expandedState by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutLinearInEasing
                )
            )
            .fillMaxWidth()
            .padding(start = 15.dp, top = 15.dp)
        ,
        shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 12.dp, end = 12.dp)
        ) {
            Text(
                text = "${zametka.title}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "${zametka.id}", color = Color.LightGray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(10.dp))
            if(expandedState){
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${zametka.description}",
                    maxLines = 4
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {
                        Log.d("MyTag","IconButton отработал")
                        viewModel.deleteZam(zametka = zametka)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = null
                        )
                    }

                }
                
            }
        }
    }
}

//Add Dialog
@Composable
fun AddDialog(
    viewModel: ZamViewModel
){
    Dialog(
        onDismissRequest = { viewModel.addDialogState.value = false }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                //Зачем я добавил Row?
                Row(
                    Modifier.fillMaxWidth()
                ){
                    Text(
                        text = "Add",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))

                var titleValue = remember { mutableStateOf("") }
                var descriptionValue = remember { mutableStateOf("") }
                OutlinedTextField(
                    value = titleValue.value,
                    onValueChange = { value -> titleValue.value = value },
                    modifier = Modifier.height(60.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.DarkGray,
                        focusedIndicatorColor = Color.DarkGray,
                        focusedLabelColor = Color.DarkGray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    label = { Text(text = "Title", color = MyLightGray)},
                    trailingIcon = {
                        if (titleValue.value.isNotEmpty()) {
                            IconButton(onClick = { titleValue.value = "" }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f),
                        border = BorderStroke(1.dp, Color.Gray),
                        elevation = 0.dp
                    ) {}
                    Spacer(modifier = Modifier.width(15.dp))
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .weight(1f),
                        border = BorderStroke(1.dp, Color.Gray),
                        elevation = 0.dp
                    ) {}
                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                OutlinedTextField(
                    value = descriptionValue.value,
                    onValueChange = { value -> descriptionValue.value = value },
                    modifier = Modifier.height(120.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.DarkGray,
                        focusedIndicatorColor = Color.DarkGray,
                        focusedLabelColor = Color.DarkGray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    label = { Text(text = "Description", color = MyLightGray)},
                    trailingIcon = {
                        if (descriptionValue.value.isNotEmpty()) {
                            IconButton(onClick = { descriptionValue.value = "" }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier
                    .height(35.dp)
                )
                FloatingActionButton(
                    onClick = {
                    viewModel.insertZam(
                        zametka = ZamData(
                            id = 0,
                            title = titleValue.value,
                            description = descriptionValue.value
                        )
                    )
                    viewModel.addDialogState.value = false
                              },
                    shape = RoundedCornerShape(15.dp),
                    backgroundColor = Color.Black,
                    content = {
                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                            text = "Add",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                )
                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }
        }
    }
}