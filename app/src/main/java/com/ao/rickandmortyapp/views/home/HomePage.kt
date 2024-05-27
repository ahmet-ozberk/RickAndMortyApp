package com.ao.rickandmortyapp.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ao.rickandmortyapp.model.CharacterModel
import com.ao.rickandmortyapp.model.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(homeViewModel: HomeViewModel = viewModel()) {
    val characterData = homeViewModel.characterData.observeAsState().value
    val listState = rememberLazyListState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.collect { index ->
            if (index == listState.layoutInfo.totalItemsCount - 5) {
                homeViewModel.getPageCharacters()
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Rick and Morty")
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray.copy(alpha = 0.2f)
                )
            )
        }, containerColor = Color.White
    ) {
        when (characterData) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading...", fontSize = 20.sp, textAlign = TextAlign.Center
                    )
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = characterData.message, fontSize = 20.sp, textAlign = TextAlign.Center
                    )
                }
            }

            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(it),
                    state = listState,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    characterData.data.results.forEachIndexed { index, character ->
                        item {
                            Row {
                                Text(
                                    text = "${index + 1}.",
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                                CharacterCardComponent(character)
                            }
                        }
                    }
                    item {
                        CircularProgressIndicator(modifier = Modifier.size(44.dp))
                    }
                }

            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No data available", fontSize = 20.sp, textAlign = TextAlign.Center
                    )
                    CircularProgressIndicator(modifier = Modifier.size(44.dp))
                }
            }
        }
    }
}

@Composable
private fun CharacterCardComponent(character: CharacterModel) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray.copy(alpha = 0.2f))
            .fillMaxWidth()
            .height(200.dp),
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = character.image,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = character.name,
                fontSize = 20.sp,
            )
            Text(text = character.gender)
            Text(text = character.status)
            Text(
                text = character.species,
                fontSize = 16.sp,
            )
        }
    }
}


@Preview
@Composable
fun HomePagePreview() {
    HomePage()
}