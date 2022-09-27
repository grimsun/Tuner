package com.fundev.tuner.view

import android.graphics.*
import android.graphics.drawable.Drawable


class AnalogIndicator : Drawable() {
    private val defaultPaint: Paint = Paint().apply {
//        setARGB(255, 255, 0, 0);
        strokeWidth = 2f
        isAntiAlias = true
        style = Paint.Style.STROKE
//        pathEffect = DashPathEffect(floatArrayOf(5f, 10f, 15f, 20f), 0f)
    }

    private val dashedPaint: Paint = Paint().apply {
//        setARGB(255, 255, 0, 0);
        strokeWidth = 100f
        isAntiAlias = true
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(5f, 10f, 15f, 20f), 0f)
    }

    var indicatorPosition : Float = 0f

    override fun draw(canvas: Canvas) {
        val cx = bounds.exactCenterX()
        val cy = bounds.height() * 1f
        canvas.save()
        canvas.rotate(-20f + 40f * indicatorPosition, cx, cy)

        canvas.drawLine(bounds.exactCenterX(), bounds.height().toFloat(), bounds.exactCenterX(), bounds.height() * 0.25F, defaultPaint)
//        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, redPaint)
        canvas.restore()

        val path = Path()
        val rect = RectF(0f, bounds.height()/2f, bounds.width().toFloat(), bounds.height() * 0.6f)
        path.arcTo(rect, 200f, 140f)
        canvas.drawPath(path, dashedPaint)
    }

    override fun setAlpha(alpha: Int) {
        // This method is required
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        // This method is required
    }

    override fun getOpacity(): Int =
            // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
            PixelFormat.OPAQUE
}