package com.flarefridges.fridgeapp.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flarefridges.fridgeapp.R
import com.flarefridges.fridgeapp.ui.theme.Typography

@Composable
fun DrawerDetail(
    viewModel: DrawerDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val viewState by viewModel.drawerDetailViewState.collectAsState()

    DrawerDetailScreen(
        modifier = Modifier.fillMaxSize(),
        viewState = viewState,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerDetailScreen(
    modifier: Modifier,
    viewState: DrawerDetailViewState,
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Drawer 1",
                            style = Typography.headlineMedium
                        )
                        if (!viewState.isLoading) {
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = "(${viewState.itemCount} items)",
                                style = Typography.headlineSmall
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (viewState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "Analyzing..."
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(
                    items = viewState.data,
                    key = { it.name }
                ) {
                    DrawerDetailCard(
                        modifier = Modifier
                            .padding(10.dp),
                        data = it
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DrawerDetailScreenPreview() {
    DrawerDetailScreen(
        modifier = Modifier.fillMaxSize(),
        viewState = DrawerDetailViewState(
            itemCount = 8,
            data = listOf(
                DrawerDetailCardData(
                    name = "Apple",
                    freshQuantity = 2,
                    rottenQuantity = 1,
                    iconRes = R.drawable.apple_icon_24
                ),
                DrawerDetailCardData(
                    name = "Banana",
                    freshQuantity = 1,
                    rottenQuantity = 3,
                    iconRes = R.drawable.banana_icon_24
                ),
                DrawerDetailCardData(
                    name = "Orange",
                    freshQuantity = 1,
                    rottenQuantity = 0,
                    iconRes = R.drawable.orange_icon_24
                )
            )
        )
    )
}