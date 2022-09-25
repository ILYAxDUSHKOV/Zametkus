package com.example.zametkus.presentation.composable.homeScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.zametkus.data.mappers.toHistoryData
import com.example.zametkus.domain.zamData.GroupData
import com.example.zametkus.domain.zamData.ZamData
import com.example.zametkus.presentation.ZamViewModel
import com.example.zametkus.presentation.composable.BottomBarItem
import com.example.zametkus.presentation.ui.theme.*
import com.example.zametkus.presentation.utills.ZamButtonsState

/*Доработать:
    1) В MyLazyRowForGroup и AddDialog происходят дублирующие запросы getAllGroup.
Вынести их на верхний уровень, чтобы вызов происходил 1 раз, а в функции передавать список
в виде аргуента.
*/

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    viewModel: ZamViewModel,
    navController:NavHostController = rememberNavController()
) {
    MyLazyColumn(
        viewModel = viewModel
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MyLazyColumn(
    viewModel: ZamViewModel
) {
    // TODO Внимательно этот момент!
    var zamList = viewModel.getAll.observeAsState(listOf()).value
    when(viewModel.ownerId.value){
        "All notes" -> zamList = viewModel.getAll.observeAsState(listOf()).value
        else -> zamList = viewModel.getAllZamByGroup(viewModel.ownerId.value).observeAsState(listOf()).value
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyLightGray)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item { MyHomeText("Home") }
            item { MyNotebooksText(viewModel = viewModel) }
            item { MyLazyRowForGroup(viewModel = viewModel) }
            item { MyNotesText() }
            items(zamList.size) { index ->
                ExpandableCard(
                    zametka = zamList[index],
                    viewModel = viewModel,
                    buttonsState = ZamButtonsState.HomeState
                )
            }
            //Last Item
            item() {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                )
            }
        }
        if (viewModel.addDialogState.value) {
            AddDialog(viewModel = viewModel)
        }
        if (viewModel.editDialogState.value) {
            EditZamDialog(viewModel = viewModel)
        }
        if (viewModel.addGroupDialogState.value) {
            AddGroupDialog(viewModel = viewModel)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    zametka: ZamData,
    viewModel: ZamViewModel,
    buttonsState:ZamButtonsState
) {
    //Expandable state
    var expandedState by remember {
        mutableStateOf(false)
    }
    Card(
        shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 15.dp),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = LinearEasing
                    )
                )
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .widthIn(min = 0.dp, max = 280.dp),
                    text = "${zametka.title}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f,true))
                Text(
                    text = zametka.date,
                    color = MyGray,
                    modifier = Modifier
                        .padding(5.dp),
                    fontSize = 15.sp
                )
            }
            if (expandedState) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "${zametka.description}",
                    fontSize = 15.sp
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp)
                ) {
                    Card(
                        backgroundColor = viewModel.colorConverter(zametka.color),
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.CenterStart),
                        shape = CircleShape
                    ) {}
                    when(buttonsState){
                        ZamButtonsState.HomeState ->
                            Row(modifier = Modifier.align(Alignment.BottomEnd)) {
                            IconButton(onClick = {
                                viewModel.openEditDialog(zametka = zametka)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Create,
                                    contentDescription = null
                                )
                            }
                            IconButton(onClick = {
                                viewModel.insertHistory(history = zametka.toHistoryData())
                                viewModel.deleteZam(zametka = zametka)
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = null
                                )
                            }
                            }
                        ZamButtonsState.HistoryState -> {
                            Row(modifier = Modifier.align(Alignment.BottomEnd)) {
                                IconButton(onClick = {
                                    viewModel.insertZam(zametka)
                                    viewModel.deleteHistory(zametka.toHistoryData())
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }

                }

            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun GroupItem(
    groupSingle: GroupData,
    viewModel: ZamViewModel
) {
    var editGroupItemState by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(end = 15.dp)
            .size(100.dp, 100.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    enabled = true,
                    onClick = {
                        viewModel.ownerId.value = groupSingle.name
                    },
                    onLongClick = {
                        editGroupItemState = !editGroupItemState
                    }
                )
        ) {
            Card(
                backgroundColor = viewModel.colorConverter(groupSingle.color),
                modifier = Modifier
                    .padding(20.dp)
                    .size(12.dp)
                    .align(Alignment.TopStart), shape = CircleShape
            ) {}
            Text(
                text = groupSingle.name,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                //maxLines = 1
            )
        }
        if (editGroupItemState) {
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 16.dp, end = 10.dp)
                ) {
                    /*Icon(
                        modifier = Modifier
                            .size(18.dp),
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        tint = MyLightGray
                    )
                    Spacer(modifier = Modifier.width(10.dp))*/
                    Icon(
                        modifier = Modifier
                            .size(18.dp)
                            .clickable { viewModel.deleteGroup(groupSingle) },
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = MyLightGray
                    )
                }
            }
        }
    }
}

//Add Dialog
@Composable
fun AddDialog(
    viewModel: ZamViewModel
) {

    val groupList = viewModel.getAllGroup.observeAsState(listOf()).value

    Dialog(
        onDismissRequest = { viewModel.addDialogState.value = false }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Note",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))

                var titleValue = remember { mutableStateOf("") }
                var descriptionValue = remember { mutableStateOf("") }
                var dropDownMenuState by remember { mutableStateOf(false) }
                var selectedGroup by remember { mutableStateOf(GroupData(1, "No Group", "Black")) }

                OutlinedTextField(
                    value = titleValue.value,
                    onValueChange = { value -> titleValue.value = value },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.DarkGray,
                        focusedIndicatorColor = Color.DarkGray,
                        focusedLabelColor = Color.DarkGray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    label = { Text(text = "Title", color = MyLightGray) },
                    trailingIcon = {
                        if (titleValue.value.isNotEmpty()) {
                            IconButton(onClick = { titleValue.value = "" }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(15.dp))
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(),
                    border = BorderStroke(1.dp, Color.Gray),
                    elevation = 0.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            backgroundColor = viewModel.colorConverter(selectedGroup.color),
                            shape = CircleShape,
                            modifier = Modifier
                                .padding(18.dp)
                                .size(20.dp)
                        ) { }
                        Text(text = selectedGroup.name)
                        Spacer(modifier = Modifier.weight(1f, true))
                        Box(
                            modifier = Modifier
                                //.align(Alignment.CenterEnd)
                                .padding(end = 15.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clickable { dropDownMenuState = !dropDownMenuState },
                                imageVector = if (dropDownMenuState) {
                                    Icons.Filled.KeyboardArrowUp
                                } else {
                                    Icons.Filled.KeyboardArrowDown
                                },
                                contentDescription = null
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = dropDownMenuState,
                        onDismissRequest = { dropDownMenuState = false }
                    ) {
                        groupList.forEach { item ->
                            DropdownMenuItem(
                                modifier = Modifier.width(280.dp),
                                contentPadding = PaddingValues(0.dp),
                                onClick = {
                                    selectedGroup = item
                                    dropDownMenuState = false
                                }
                            ) {
                                Card(
                                    backgroundColor = viewModel.colorConverter(item.color),
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .size(15.dp)
                                        .align(Alignment.CenterVertically)
                                ) { }
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = item.name
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
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
                    label = { Text(text = "Description", color = MyLightGray) },
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
                Spacer(modifier = Modifier.height(20.dp))
                FloatingActionButton(
                    backgroundColor = Color.Black,
                    shape = CircleShape,
                    onClick = {
                        viewModel.insertZam(
                            zametka = ZamData(
                                id = 0,
                                title = titleValue.value,
                                description = descriptionValue.value,
                                ownerId = selectedGroup.name,
                                color = selectedGroup.color,
                                date = viewModel.getCurrentDate()
                            )
                        )
                        viewModel.addDialogState.value = false
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                )
            }
        }
    }
}

//Edit Dialog
@Composable
fun EditZamDialog(
    viewModel: ZamViewModel
) {

    val groupList = viewModel.getAllGroup.observeAsState(listOf()).value
    var currentZam = viewModel.currentNote

    Dialog(
        onDismissRequest = { viewModel.editDialogState.value = false }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(30.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Note",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))

                var titleValue = remember { mutableStateOf(currentZam.title) }
                var descriptionValue = remember { mutableStateOf(currentZam.description) }
                var dropDownMenuState by remember { mutableStateOf(false) }
                var safeGroup = groupList.find { it.name == currentZam.ownerId }
                var selectedGroup by remember { mutableStateOf(GroupData(1, "No Group", "Black")) }
                if (safeGroup != null) {
                    selectedGroup = safeGroup
                }

                OutlinedTextField(
                    value = titleValue.value,
                    onValueChange = { value -> titleValue.value = value },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.DarkGray,
                        focusedIndicatorColor = Color.DarkGray,
                        focusedLabelColor = Color.DarkGray
                    ),
                    shape = RoundedCornerShape(20.dp),
                    label = { Text(text = "Title", color = MyLightGray) },
                    trailingIcon = {
                        if (titleValue.value.isNotEmpty()) {
                            IconButton(onClick = { titleValue.value = "" }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(15.dp))
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(),
                    border = BorderStroke(1.dp, Color.Gray),
                    elevation = 0.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            backgroundColor = viewModel.colorConverter(selectedGroup.color),
                            shape = CircleShape,
                            modifier = Modifier
                                .padding(18.dp)
                                .size(20.dp)
                        ) { }
                        Text(text = selectedGroup.name)
                        Spacer(modifier = Modifier.weight(1f, true))
                        Box(
                            modifier = Modifier
                                //.align(Alignment.CenterEnd)
                                .padding(end = 15.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clickable { dropDownMenuState = !dropDownMenuState },
                                imageVector = if (dropDownMenuState) {
                                    Icons.Filled.KeyboardArrowUp
                                } else {
                                    Icons.Filled.KeyboardArrowDown
                                },
                                contentDescription = null
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = dropDownMenuState,
                        onDismissRequest = { dropDownMenuState = false }
                    ) {
                        groupList.forEach { item ->
                            DropdownMenuItem(
                                modifier = Modifier.width(280.dp),
                                contentPadding = PaddingValues(0.dp),
                                onClick = {
                                    selectedGroup = item
                                    dropDownMenuState = false
                                }
                            ) {
                                Card(
                                    backgroundColor = viewModel.colorConverter(item.color),
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .size(15.dp)
                                        .align(Alignment.CenterVertically)
                                ) { }
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = item.name
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
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
                    label = { Text(text = "Description", color = MyLightGray) },
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
                Spacer(modifier = Modifier.height(20.dp))
                FloatingActionButton(
                    backgroundColor = Color.Black,
                    shape = CircleShape,
                    onClick = {
                        viewModel.updateZam(
                            zametka = ZamData(
                                id = viewModel.currentNote.id,
                                title = titleValue.value,
                                description = descriptionValue.value,
                                ownerId = selectedGroup.name,
                                color = selectedGroup.color,
                                date = viewModel.getCurrentDate()
                            )
                        )
                        viewModel.editDialogState.value = false
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun AddGroupDialog(
    viewModel: ZamViewModel
) {
    Dialog(
        onDismissRequest = { viewModel.addGroupDialogState.value = false }
    ) {
        Card(
            shape = RoundedCornerShape(30.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var groupName by remember { mutableStateOf("") }
                var groupDropdownMenuState by remember { mutableStateOf(false) }
                var selectedColor by remember { mutableStateOf("Black") }
                Text(
                    text = "Group",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(7f, true),
                        value = groupName,
                        onValueChange = { value -> groupName = value },
                        maxLines = 1,
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = Color.DarkGray,
                            focusedIndicatorColor = Color.DarkGray,
                            focusedLabelColor = Color.DarkGray
                        ),
                        shape = RoundedCornerShape(20.dp),
                        label = { Text(text = "Group", color = MyLightGray) },
                        trailingIcon = {
                            if (groupName.isNotEmpty()) {
                                IconButton(onClick = { groupName = "" }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Close,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Card(
                        onClick = { groupDropdownMenuState = !groupDropdownMenuState },
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, Color.Gray),
                        elevation = 0.dp,
                        modifier = Modifier
                            .weight(2f, false)
                            .align(Alignment.Bottom)
                    ) {
                        Card(
                            backgroundColor = viewModel.colorConverter(selectedColor),
                            shape = CircleShape,
                            modifier = Modifier
                                .padding(18.dp)
                                .size(20.dp)
                        ) {

                        }
                        DropdownMenu(
                            expanded = groupDropdownMenuState,
                            onDismissRequest = { groupDropdownMenuState = false }
                        ) {
                            DropdownMenuItem(
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(56.dp),
                                onClick = {
                                    selectedColor = "Red"
                                    groupDropdownMenuState = false
                                }
                            ) {
                                Card(
                                    backgroundColor = myRed,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .padding(18.dp)
                                        .size(20.dp)
                                ) {}
                            }
                            DropdownMenuItem(
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(56.dp),
                                onClick = {
                                    selectedColor = "Green"
                                    groupDropdownMenuState = false
                                }
                            ) {
                                Card(
                                    backgroundColor = myGreen,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .padding(18.dp)
                                        .size(20.dp)
                                ) {}
                            }
                            DropdownMenuItem(
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(56.dp),
                                onClick = {
                                    selectedColor = "Purple"
                                    groupDropdownMenuState = false
                                }
                            ) {
                                Card(
                                    backgroundColor = myPurple,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .padding(18.dp)
                                        .size(20.dp)
                                ) {}
                            }
                            DropdownMenuItem(
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(56.dp),
                                onClick = {
                                    selectedColor = "Orange"
                                    groupDropdownMenuState = false
                                }
                            ) {
                                Card(
                                    backgroundColor = myOrange,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .padding(18.dp)
                                        .size(20.dp)
                                ) {}
                            }
                            DropdownMenuItem(
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.size(56.dp),
                                onClick = {
                                    selectedColor = "Blue"
                                    groupDropdownMenuState = false
                                }
                            ) {
                                Card(
                                    backgroundColor = myBlue,
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .padding(18.dp)
                                        .size(20.dp)
                                ) {}
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                FloatingActionButton(
                    backgroundColor = Color.Black,
                    shape = CircleShape,
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    onClick = {
                        viewModel.insertGroup(
                            GroupData(
                                id = 0,
                                name = groupName,
                                color = selectedColor
                            )
                        )
                        viewModel.addGroupDialogState.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun MyBottomAppBar(
    leadingItem: BottomBarItem,
    finalItem: BottomBarItem,
    navController: NavController,
    onItemClick: (BottomBarItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 5.dp
    ) {
        Spacer(modifier = Modifier.weight(0.2f, true))
        BottomNavigationItem(
            selected = leadingItem.route == backStackEntry.value?.destination?.route,
            onClick = { onItemClick(leadingItem) },
            selectedContentColor = Color.Black,
            unselectedContentColor = MyLightGray,
            icon = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = leadingItem.icon, contentDescription = leadingItem.name)
                    if(leadingItem.route == backStackEntry.value?.destination?.route){
                        Text(
                            text = leadingItem.name,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.weight(0.6f, true))
        Spacer(modifier = Modifier.weight(0.6f, true))
        BottomNavigationItem(
            selected = finalItem.route == backStackEntry.value?.destination?.route,
            onClick = { onItemClick(finalItem) },
            selectedContentColor = Color.Black,
            unselectedContentColor = MyLightGray,
            icon = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = finalItem.icon, contentDescription = finalItem.name)
                    if(finalItem.route == backStackEntry.value?.destination?.route){
                        Text(
                            text = finalItem.name,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.weight(0.2f, true))
    }
}

@Composable
fun MyHomeText(text:String) {
    Spacer(modifier = Modifier.height(15.dp))
    Text(
        modifier = Modifier.padding(start = 20.dp),
        text = text,
        fontSize = 40.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun MyNotebooksText(
    viewModel: ZamViewModel
) {
    Spacer(modifier = Modifier.height(15.dp))
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.dp),
            text = "Notebooks",
            fontSize = 18.sp,
            color = MyGray,
            fontWeight = FontWeight.Bold
        )
        TextButton(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            onClick = { viewModel.addGroupDialogState.value = true }
        ) {
            Text(
                text = "New",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MyLazyRowForGroup(
    viewModel: ZamViewModel
) {
    val groupList = viewModel.getAllGroup.observeAsState(listOf()).value
    Spacer(modifier = Modifier.height(15.dp))
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        item() {
            Spacer(modifier = Modifier.width(20.dp))
        }
        item(){
            Card(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .padding(end = 15.dp)
                    .size(100.dp, 100.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .combinedClickable(
                            enabled = true,
                            onClick = {
                                viewModel.ownerId.value = "All notes"
                            }
                        )
                ) {
                    Card(
                        backgroundColor = Color.Gray,
                        modifier = Modifier
                            .padding(20.dp)
                            .size(12.dp)
                            .align(Alignment.TopStart), shape = CircleShape
                    ) {}
                    Text(
                        text = "All notes",
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(20.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
        items(groupList.size) { index ->
            GroupItem(
                groupSingle = groupList[index],
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun MyNotesText() {
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        modifier = Modifier.padding(start = 20.dp),
        text = "Notes",
        fontSize = 18.sp, color = MyGray,
        fontWeight = FontWeight.Bold
    )
}