package com.flarefridges.fridgeapp.homepage

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.flarefridges.fridgeapp.R
import com.flarefridges.fridgeapp.ui.theme.FridgeAppTheme

@Composable
fun LockWarningDialog(
    modifier: Modifier = Modifier,
    onDismissed: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismissed() },
        title = {
            Text(text = "Unable to lock drawer")
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.warning_icon_24),
                contentDescription = "Warning",
                tint = Color.Unspecified
            )
        },
        text = {
            Text(
                text = "Please push the drawer in completely before locking",
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            TextButton(onClick = { onDismissed() }) {
                Text(text = "OK")
            }
        },
    )
}

@Preview
@Composable
fun LockWarningDialogPreview() {
    FridgeAppTheme {
        LockWarningDialog(
            onDismissed = {}
        )
    }
}