package com.flarefridges.fridgeapp.ui.shapes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class CurvedEdgeRectangle : Shape by GenericShape(builder = { size, _ ->
    lineTo(0f, size.height * 0.7f)
    quadraticBezierTo(
        size.width / 2f,
        size.height,
        size.width,
        size.height * 0.7f,
    )
    lineTo(size.width, 0f)
})

@Preview
@Composable
fun CurvedEdgeRectanglePreview() {
    Surface(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        color = androidx.compose.ui.graphics.Color.Cyan,
        shape = CurvedEdgeRectangle()
    ) {}
}