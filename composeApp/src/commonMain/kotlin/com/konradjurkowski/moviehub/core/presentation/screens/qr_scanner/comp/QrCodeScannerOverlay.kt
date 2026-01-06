package com.konradjurkowski.moviehub.core.presentation.screens.qr_scanner.comp

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

fun ContentDrawScope.drawScannerOverlay(
    overlayColor: Color = Color(0x88000000),
    overlayBorderColor: Color = Color.White,
    cornerLength: Float = 80f,
    cornerThickness: Float = 30f,
) {
    val scanAreaSize = size.width * 0.65f
    val left = (size.width - scanAreaSize) / 2
    val top = (size.height - scanAreaSize) / 3
    val right = left + scanAreaSize
    val bottom = top + scanAreaSize

    drawContent()

    // Draw background overlay
    drawRect(
        color = overlayColor,
        topLeft = Offset.Zero,
        size = Size(size.width, top),
    )
    drawRect(
        color = overlayColor,
        topLeft = Offset(right, top),
        size = Size(size.width - right, scanAreaSize),
    )
    drawRect(
        color = overlayColor,
        topLeft = Offset(0f, bottom),
        size = Size(size.width, size.height - bottom),
    )
    drawRect(
        color = overlayColor,
        topLeft = Offset(0f, top),
        size = Size(left, scanAreaSize),
    )
    drawRect(
        color = overlayColor,
        topLeft = Offset.Zero,
        size = Size(size.width, top),
    )
    drawRect(
        color = overlayColor,
        topLeft = Offset(right, top),
        size = Size(size.width - right, scanAreaSize),
    )
    drawRect(
        color = overlayColor,
        topLeft = Offset(0f, bottom),
        size = Size(size.width, size.height - bottom),
    )
    drawRect(
        color = overlayColor,
        topLeft = Offset(0f, top),
        size = Size(left, scanAreaSize),
    )

    // Left top corner
    val pathTopLeft = Path().apply {
        moveTo(left, top + cornerLength)
        lineTo(left, top)
        lineTo(left + cornerLength, top)
    }

    // Right top corner
    val pathTopRight = Path().apply {
        moveTo(right - cornerLength, top)
        lineTo(right, top)
        lineTo(right, top + cornerLength)
    }

    // Left bottom corner
    val pathBottomLeft = Path().apply {
        moveTo(left, bottom - cornerLength)
        lineTo(left, bottom)
        lineTo(left + cornerLength, bottom)
    }

    // Right bottom corner
    val pathBottomRight = Path().apply {
        moveTo(right - cornerLength, bottom)
        lineTo(right, bottom)
        lineTo(right, bottom - cornerLength)
    }

    val strokeStyle = Stroke(
        width = cornerThickness,
        cap = StrokeCap.Round,
        join = StrokeJoin.Round,
    )

    // Draw all corners
    drawPath(pathTopLeft, overlayBorderColor, style = strokeStyle)
    drawPath(pathTopRight, overlayBorderColor, style = strokeStyle)
    drawPath(pathBottomLeft, overlayBorderColor, style = strokeStyle)
    drawPath(pathBottomRight, overlayBorderColor, style = strokeStyle)
}
