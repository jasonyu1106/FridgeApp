package com.flarefridges.fridgeapp.homepage

import android.graphics.BitmapFactory
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flarefridges.fridgeapp.R
import com.flarefridges.fridgeapp.homepage.DrawerCardData.LockStatus
import com.flarefridges.fridgeapp.ui.theme.FridgeAppTheme
import com.flarefridges.fridgeapp.ui.theme.Jade
import com.flarefridges.fridgeapp.ui.theme.Typography

@Composable
fun DrawerCard(
    modifier: Modifier,
    data: DrawerCardData,
    onUnlock: (Int) -> Unit = { _ -> },
    onLock: (Int) -> Unit = { _ -> },
    onLockWarningClicked: () -> Unit = {},
    onSnapshot: (Int) -> Unit = { _ -> },
    onViewItems: (Int) -> Unit = { _ -> },
    onExpandCard: (Int) -> Unit = { _ -> },
    onCollapseCard: (Int) -> Unit = { _ -> }
) {
    ElevatedCard(modifier) {
        Column(
            Modifier
                .animateContentSize()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (data.id == 1) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        painter = painterResource(id = R.drawable.drawer_top_icon_48),
                        contentDescription = "Top Drawer",
                        tint = Color.Unspecified
                    )
                } else if (data.id == 2) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        painter = painterResource(id = R.drawable.drawer_bottom_icon_24),
                        contentDescription = "Bottom Drawer",
                        tint = Color.Unspecified
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = data.name,
                    style = Typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (data.showLockWarning) {
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(onClick = { onLockWarningClicked() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.warning_icon_24),
                            contentDescription = "Warning",
                            tint = Color.Unspecified
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Switch(
                    checked = data.lockStatus == LockStatus.UNLOCKED ||
                            data.lockStatus == LockStatus.UNLOCKING,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            onUnlock(data.id)
                        } else {
                            onLock(data.id)
                        }
                    },
                    enabled = data.lockStatus == LockStatus.LOCKED ||
                            data.lockStatus == LockStatus.UNLOCKED,
                    thumbContent = {
                        when (data.lockStatus) {
                            LockStatus.LOCKED -> Icon(
                                painter = painterResource(id = R.drawable.outline_lock_24),
                                contentDescription = "Closed Lock",
                                modifier = Modifier.padding(2.dp),
                                tint = MaterialTheme.colorScheme.error
                            )
                            LockStatus.UNLOCKED -> Icon(
                                painter = painterResource(id = R.drawable.outline_lock_open_24),
                                contentDescription = "Open Lock",
                                modifier = Modifier.padding(2.dp),
                                tint = Jade
                            )
                            LockStatus.LOCKING -> CircularProgressIndicator(
                                modifier = Modifier.padding(4.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.error
                            )
                            LockStatus.UNLOCKING -> CircularProgressIndicator(
                                modifier = Modifier.padding(4.dp),
                                strokeWidth = 2.dp,
                                color = Jade
                            )
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        disabledCheckedThumbColor = Color.White,
                        disabledUncheckedThumbColor = Color.White,
                        checkedTrackColor = Jade,
                        uncheckedTrackColor = MaterialTheme.colorScheme.error,
                        disabledCheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledUncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                        checkedBorderColor = Color.Transparent,
                        uncheckedBorderColor = Color.Transparent,
                        disabledCheckedBorderColor = Color.Transparent,
                        disabledUncheckedBorderColor = Color.Transparent
                    )
                )
            }
            if (data.isCameraSupported) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilledTonalButton(
                        enabled = !data.isSnapshotLoading,
                        onClick = { onSnapshot(data.id) },
                    ) {
                        if (data.isSnapshotLoading) {
                            Box(modifier = Modifier.padding(2.dp)) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    strokeWidth = 2.dp,
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_camera_24),
                                contentDescription = "View Snapshot",
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Snapshot",
                            style = Typography.labelMedium
                        )
                    }
                    FilledTonalButton(
                        onClick = { onViewItems(data.id) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_list_bulleted_24),
                            contentDescription = "View Items"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Items",
                            style = Typography.labelMedium
                        )
                    }
                }
                data.snapshot?.let {
                    Divider(thickness = 1.dp)
                    if (data.isExpanded) {
                        Image(
                            bitmap = it.asImageBitmap(),
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .height(200.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentDescription = "Drawer Snapshot"
                        )
                        IconButton(onClick = { onCollapseCard(data.id) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.expand_less_24),
                                contentDescription = "Collapse"
                            )
                        }
                    } else {
                        IconButton(onClick = { onExpandCard(data.id) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.expand_more_24),
                                contentDescription = "Expand"
                            )
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
fun DrawerCardUnlockedPreview() {
    FridgeAppTheme {
        DrawerCard(
            modifier = Modifier, data = DrawerCardData(
                id = 1,
                name = "Drawer 1",
                lockStatus = LockStatus.UNLOCKED,
                isCameraSupported = true,
                snapshot = BitmapFactory.decodeResource(
                    LocalContext.current.resources,
                    R.drawable.fruit
                )
            )
        )
    }
}

@Preview
@Composable
fun DrawerCardUnlockingPreview() {
    FridgeAppTheme {
        DrawerCard(
            modifier = Modifier, data = DrawerCardData(
                id = 1,
                name = "Drawer 1",
                lockStatus = LockStatus.UNLOCKING,
                isCameraSupported = true
            )
        )
    }
}

@Preview
@Composable
fun DrawerCardLockWarningPreview() {
    FridgeAppTheme {
        DrawerCard(
            modifier = Modifier, data = DrawerCardData(
                id = 1,
                name = "Drawer 1",
                lockStatus = LockStatus.UNLOCKED,
                showLockWarning = true,
                isCameraSupported = true
            )
        )
    }
}

