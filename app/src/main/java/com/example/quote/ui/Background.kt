package com.example.quote.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.sin


@Composable
fun ElegantWavesBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "waves")

    val wave1Progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave1"
    )

    val wave2Progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave2"
    )

    val wave3Progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave3"
    )

    val wave4Progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(17000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave4"
    )

    val wave5Progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave5"
    )

    val wave6Progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(13000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave6"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFF8FAFC),
                    Color(0xFFEFF6FF),
                    Color(0xFFF1F5F9)
                )
            )
        )

        drawWave(
            progress = wave1Progress,
            amplitude = 80f,
            frequency = 0.5f,
            phase = 0f,
            yOffset = size.height * 0.85f,
            color = Color(0xFF2563EB).copy(alpha = 0.04f)
        )

        drawWave(
            progress = wave2Progress,
            amplitude = 68f,
            frequency = 0.8f,
            phase = 180f,
            yOffset = size.height * 0.72f,
            color = Color(0xFF4F46E5).copy(alpha = 0.05f)
        )

        drawWave(
            progress = wave3Progress,
            amplitude = 56f,
            frequency = 1.1f,
            phase = 90f,
            yOffset = size.height * 0.58f,
            color = Color(0xFF7C3AED).copy(alpha = 0.07f)
        )

        drawWave(
            progress = wave4Progress,
            amplitude = 44f,
            frequency = 1.4f,
            phase = 270f,
            yOffset = size.height * 0.43f,
            color = Color(0xFF06B6D4).copy(alpha = 0.09f)
        )

        drawWave(
            progress = wave5Progress,
            amplitude = 32f,
            frequency = 1.7f,
            phase = 135f,
            yOffset = size.height * 0.28f,
            color = Color(0xFF14B8A6).copy(alpha = 0.11f)
        )

        drawWave(
            progress = wave6Progress,
            amplitude = 22f,
            frequency = 2.0f,
            phase = 45f,
            yOffset = size.height * 0.15f,
            color = Color(0xFF0EA5E9).copy(alpha = 0.13f)
        )
    }
}

fun DrawScope.drawWave(
    progress: Float,
    amplitude: Float,
    frequency: Float,
    phase: Float,
    yOffset: Float,
    color: Color
) {
    val path = Path()
    val width = size.width
    val height = size.height

    val waveLength = width * 1.5f
    val offset = -waveLength * progress

    path.moveTo(0f, height)
    path.lineTo(0f, yOffset)

    for (x in 0..width.toInt() step 5) {
        val relativeX = x.toFloat()
        val radians = ((relativeX + offset) * frequency * Math.PI / 180.0).toFloat()
        val y = yOffset + sin((radians + phase * Math.PI / 180.0).toDouble()).toFloat() * amplitude

        path.lineTo(relativeX, y)
    }

    path.lineTo(width, height)
    path.close()

    drawPath(
        path = path,
        color = color
    )
}
