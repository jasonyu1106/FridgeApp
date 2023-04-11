package com.flarefridges.fridgeapp.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flarefridges.fridgeapp.R
import com.flarefridges.fridgeapp.ui.theme.FridgeAppTheme
import com.flarefridges.fridgeapp.ui.theme.KellyGreen
import com.flarefridges.fridgeapp.ui.theme.Maroon
import com.flarefridges.fridgeapp.ui.theme.Typography

@Composable
fun DrawerDetailCard(
    modifier: Modifier,
    data: DrawerDetailCardData,
) {
    ElevatedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            data.iconRes?.let {
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(bottom = 10.dp),
                    painter = painterResource(id = data.iconRes),
                    contentDescription = "${data.name} icon",
                )
            }
            Text(
                text = data.name,
                style = Typography.bodyLarge
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (data.freshQuantity > 0) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = data.freshQuantity.toString(),
                            style = Typography.titleMedium,
                            color = KellyGreen
                        )
                        Text(
                            text = "Fresh",
                            style = Typography.labelSmall
                        )
                    }
                }
                if (data.rottenQuantity > 0) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = data.rottenQuantity.toString(),
                            style = Typography.titleMedium,
                            color = Maroon
                        )
                        Text(
                            text = "Expired",
                            style = Typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DrawerDetailCardPreview() {
    FridgeAppTheme {
        DrawerDetailCard(
            modifier = Modifier
                .width(200.dp)
                .aspectRatio(1f),
            data = DrawerDetailCardData(
                name = "Apple",
                freshQuantity = 2,
                rottenQuantity = 1,
                iconRes = R.drawable.apple_icon_24
            )
        )
    }
}