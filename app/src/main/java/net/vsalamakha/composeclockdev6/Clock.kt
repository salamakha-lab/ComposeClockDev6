package net.vsalamakha.composeclockdev6

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.Composable
import androidx.compose.remember
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.ui.animation.Transition
import androidx.ui.foundation.Canvas
import androidx.ui.geometry.Offset
import androidx.ui.graphics.Paint
import androidx.ui.graphics.PaintingStyle
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.LayoutSize
import androidx.ui.layout.Stack
import androidx.ui.unit.Dp
import androidx.ui.unit.PxSize
import androidx.ui.unit.min
import androidx.ui.unit.toRect
import java.util.*
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


/**
 * @author Adib Faramarzi (adibfara@gmail.com)
 * @author Val Salamakha (salamakha.lab@gmail.com)
 */

@Composable
fun ComposeClock() {
    Stack(modifier = LayoutSize.Fill) {


        val clockConfig = ClockConfig(
            Random()
        )
        //ClockBackground(clockConfig)

        //Stack(LayoutSize.Fill + LayoutPadding(Dp(16f))) {
        Surface(color = clockConfig.colorPalette.backgroundColor) {
        Box(
            modifier = LayoutPadding(16.dp),
            gravity = ContentGravity.Center,
            backgroundColor = Color.Transparent
        ) {
            /**
             * Background particles
             */

            /**
             * Background particles
             */
            repeat(1000) {
                ParticleHeartBeat(
                    clockConfig,
                    ParticleObject.Type.Background
                )
            }

            /**
             * Hour handle
             */

            /**
             * Hour handle
             */
            repeat(100) {
                ParticleHeartBeat(
                    clockConfig,
                    ParticleObject.Type.Hour
                )
            }

            /**
             * Minute handle
             */

            /**
             * Minute handle
             */
            repeat(100) {
                ParticleHeartBeat(
                    clockConfig,
                    ParticleObject.Type.Minute
                )
            }

            ClockBackgroundBorder(clockConfig)
            ClockMinuteCircles(clockConfig)
            ClockSecondHand(clockConfig)
        }
    }
}

@Composable
private fun ClockBackground(clockConfig: ClockConfig) {
    val backgroundPaint = remember {
        Paint().apply {
            color = clockConfig.colorPalette.backgroundColor
            style = PaintingStyle.fill
        }
    }

    Canvas(modifier = LayoutSize.Fill) {
        drawRect(size.toRect(), backgroundPaint)
    }
}

@Composable
private fun ClockSecondHand(clockConfig: ClockConfig) {
    val interpolator = FastOutSlowInInterpolator()

    Transition(definition = SecondHandAnimations, initState = 0, toState = 1) {
        Canvas(modifier = LayoutSize.Fill) {
            val clockRadius = (min(size.width /2, size.height / 2) * 0.9f).value
            val paint = Paint().apply {
                style = PaintingStyle.fill
                color = clockConfig.colorPalette.handleColor
            }
            val centerX = (size.width/2).value
            val centerY = (size.height/2).value
            val oneMinuteRadians = Math.PI / 30

            val currentSecondInMillisecond = System.currentTimeMillis() % 1000
            val progression = (currentSecondInMillisecond / 1000.0)
            val interpolatedProgression =
                interpolator.getInterpolation(progression.toFloat())
            val animatedSecond =
                Calendar.getInstance().get(Calendar.SECOND) + interpolatedProgression

            val degree = -Math.PI / 2 + (animatedSecond * oneMinuteRadians)
            val x = centerX + cos(degree) * clockRadius
            val y = centerY + sin(degree) * clockRadius

            paint.style = PaintingStyle.fill
            val radius = 16f//8f

            drawCircle(
                (Offset(x.toFloat(), y.toFloat())),
                radius,
                paint
            )
        }
    }
}

@Composable
private fun ClockMinuteCircles(clockConfig: ClockConfig) {
    Canvas(modifier = LayoutSize.Fill) {
        val clockRadius = 0.95f * min(size.width/2, size.height/2).value
        val paint = Paint().apply {
            style = PaintingStyle.fill
            color = clockConfig.colorPalette.handleColor
        }
        val centerX = (size.width/2).value
        val centerY = (size.height/2).value

        val oneMinuteRadians = Math.PI / 30
        0.rangeTo(59).forEach { minute ->
            val isHour = minute % 5 == 0
            val degree = -Math.PI / 2 + (minute * oneMinuteRadians)
            val x = centerX + cos(degree) * clockRadius
            val y = centerY + sin(degree) * clockRadius

            val radius: Float
            if (isHour) {
                paint.style = PaintingStyle.fill
                radius = 12f
            } else {
                paint.style = PaintingStyle.stroke
                radius = 6f
            }
            drawCircle(
                (Offset(x.toFloat(), y.toFloat())),
                radius,
                paint
            )
        }

    }
}

@Composable
private fun ClockBackgroundBorder(clockConfig: ClockConfig) {

    Canvas(modifier = LayoutSize.Fill) {
        val radius = min((size.width / 2), (size.height/ 2)).value * 0.9f
        val paint = Paint().apply {
            style = PaintingStyle.stroke
            strokeWidth = 10f
            color = clockConfig.colorPalette.borderColor
        }
        drawCircle(
            (Offset((size.width.value / 2), (size.height.value / 2))),
            radius,
            paint
        )
    }
}
