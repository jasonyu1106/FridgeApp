package com.flarefridges.fridgeapp.homepage

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flarefridges.fridgeapp.R
import com.flarefridges.fridgeapp.ui.shapes.CurvedEdgeRectangle
import com.flarefridges.fridgeapp.ui.theme.FridgeAppTheme
import com.flarefridges.fridgeapp.ui.theme.Jade

@Composable
fun Home(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDrawerItems: (Int) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.homeNavigationEvent.collect { navEvent ->
            when (navEvent) {
                is HomeNavigationEvent.ToDrawerItems -> onNavigateToDrawerItems(navEvent.drawerId)
            }
        }
    }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.toastEvent.collect { toastMessage ->
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }
    val viewState by viewModel.homeState.collectAsState()

    HomeScreen(
        modifier = Modifier.fillMaxSize(),
        viewState = viewState,
        onRefresh = { viewModel.refresh() },
        onUnlock = { id -> viewModel.onUnlock(id) },
        onLock = { id -> viewModel.onLock(id) },
        onLockWarningClicked = { viewModel.onLockWarningClicked() },
        onLockWarningDialogDismissed = { viewModel.onLockWarningDialogDismissed() },
        onSnapshot = { id, bitmap -> viewModel.onSnapshot(id, bitmap) },
        onViewItems = { id -> viewModel.onViewItems(id) },
        onExpandCard = { id -> viewModel.onExpandCard(id) },
        onCollapseCard = { id -> viewModel.onCollapseCard(id) }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeScreen(
    modifier: Modifier,
    viewState: HomeState,
    onRefresh: () -> Unit = {},
    onUnlock: (Int) -> Unit = { _ -> },
    onLock: (Int) -> Unit = { _ -> },
    onLockWarningClicked: () -> Unit = {},
    onLockWarningDialogDismissed: () -> Unit = {},
    onSnapshot: (Int, Bitmap) -> Unit = { _, _ -> },
    onViewItems: (Int) -> Unit = { _ -> },
    onExpandCard: (Int) -> Unit = { _ -> },
    onCollapseCard: (Int) -> Unit = { _ -> },
    res: Resources = LocalContext.current.resources,
    isPreview: Boolean = LocalInspectionMode.current
) {
    val pullRefreshState =
        rememberPullRefreshState(refreshing = viewState.isRefreshing, onRefresh = onRefresh)
    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        if (viewState.showErrorToast) {
            Toast.makeText(LocalContext.current, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
        val visible = remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            visible.value = true
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    shape = CurvedEdgeRectangle(),
                    brush = Brush.verticalGradient(
                        0.25f to MaterialTheme.colorScheme.tertiaryContainer,
                        1.0f to MaterialTheme.colorScheme.primaryContainer
                    )
                )
                .padding(20.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                AnimatedVisibility(
                    modifier = Modifier.weight(1f),
                    visible = visible.value || isPreview,
                    enter = slideInHorizontally() + fadeIn()
                ) {
                    Column {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp),
                            text = "Hi ${viewState.displayName}",
                            style = MaterialTheme.typography.headlineLarge,
                            maxLines = 1
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            text = "Welcome to your smart fridge",
                            style = MaterialTheme.typography.bodyLarge,
                        )

                    }
                }
                HomeWidget(
                    modifier = Modifier.padding(start = 10.dp),
                    viewState
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, top = 220.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            for (drawer in viewState.drawerData) {
                DrawerCard(
                    modifier = Modifier
                        .fillMaxWidth(),
                    data = drawer,
                    onUnlock = { id -> onUnlock(id) },
                    onLock = { id -> onLock(id) },
                    onLockWarningClicked = { onLockWarningClicked() },
                    onSnapshot = { id ->
                        onSnapshot(
                            id,
                            BitmapFactory.decodeResource(res, R.drawable.fruit)
                        )
                    },
                    onViewItems = { id -> onViewItems(id) },
                    onExpandCard = { id -> onExpandCard(id) },
                    onCollapseCard = { id -> onCollapseCard(id) },
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
        PullRefreshIndicator(
            refreshing = viewState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        if (viewState.showLockWarningDialog) {
            LockWarningDialog(onDismissed = { onLockWarningDialogDismissed() })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeWidget(
    modifier: Modifier,
    viewState: HomeState
) {
    val pageCount = 2
    val pagerState = rememberPagerState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WidgetPager(
            modifier = Modifier.size(width = 170.dp, height = 140.dp),
            viewState = viewState,
            pageCount = pageCount,
            pagerState = pagerState
        )
        Row(horizontalArrangement = Arrangement.Center) {
            repeat(pageCount) { iteration ->
                val color =
                    if ((pagerState.currentPage % pageCount) == iteration) {
                        Color.DarkGray
                    } else {
                        Color.LightGray
                    }
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WidgetPager(
    modifier: Modifier,
    viewState: HomeState,
    pageCount: Int,
    pagerState: PagerState
) {
    HorizontalPager(
        modifier = modifier
            .graphicsLayer { alpha = 0.99f }
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.horizontalGradient(
                        0.0f to Color.Transparent,
                        0.2f to Color.White,
                        0.8f to Color.White,
                        1.0f to Color.Transparent
                    ),
                    blendMode = BlendMode.DstIn
                )
            },
        pageCount = Int.MAX_VALUE,
        state = pagerState
    ) { page ->
        when (page % pageCount) {
            0 -> TemperatureWidget(temperature = viewState.temperature.toInt())
            1 -> FridgeWidget()
        }
    }
}


@Composable
private fun TemperatureWidget(temperature: Int) {
    val progress = remember { mutableStateOf(0f) }
    LaunchedEffect(temperature) {
        progress.value = (temperature / 5f)
    }
    BaseWidget {
        Text(
            text = "${temperature}°C",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 36.sp,
            maxLines = 1,
            color = MaterialTheme.colorScheme.primary
        )
        CircularProgressIndicator(
            modifier = Modifier.size(120.dp),
            strokeWidth = 10.dp,
            progress = animateFloatAsState(
                targetValue = progress.value,
                animationSpec = tween(2000)
            ).value,
            trackColor = MaterialTheme.colorScheme.surface,
            strokeCap = StrokeCap.Round
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FridgeWidget() {
    BaseWidget {
        Box(contentAlignment = Alignment.TopStart) {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.fridge),
                contentDescription = "Fridge"
            )
            Badge(
                modifier = Modifier.offset(x = 10.dp, y = (-10).dp),
                containerColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.wifi_icon_24),
                    contentDescription = "Connected",
                    tint = Jade
                )
            }
        }
    }
}

@Composable
private fun BaseWidget(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { content() }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    FridgeAppTheme {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            HomeState(
                temperature = 4f,
                drawerData = listOf(
                    DrawerCardData(
                        id = 1,
                        name = "Drawer 1",
                        lockStatus = DrawerCardData.LockStatus.UNLOCKED,
                        isCameraSupported = true
                    ),
                    DrawerCardData(
                        id = 2,
                        name = "Drawer 2",
                        lockStatus = DrawerCardData.LockStatus.LOCKED,
                        isCameraSupported = false
                    ),
                ),
                drawerImage = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            ),
            onUnlock = { _ -> }
        )
    }
}