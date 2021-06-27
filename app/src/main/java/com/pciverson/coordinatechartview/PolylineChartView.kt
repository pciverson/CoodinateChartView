package com.pciverson.coordinatechartview

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Path
import android.graphics.Shader

/**
 *
 * @author:allen
 * @date:2021/6/25
 * 您好，世界！
 *
 * 折线图
 **/
class PolylineChartView(context: Context) : LineChartView(context) {

    override fun draw(
        canvas: Canvas
    ) {
        mCoordinateValueAdapter?.let { adapter ->
            val path = Path()
            val fillPath = Path()
            val points = adapter.getPointList()
            points.forEachIndexed { index, value ->
                when (index) {
                    0 -> {
                        path.moveTo(
                            value.x.toFloat(),
                            value.y.toFloat()
                        )
                        fillPath.moveTo(
                            value.x.toFloat(),
                            getYStartOffset() + mCoordinateView!!.startPositionEdgePixel
                        )
                        fillPath.lineTo(
                            value.x.toFloat(),
                            value.y.toFloat()
                        )
                    }
                    points.size - 1 -> {
                        path.lineTo(
                            value.x.toFloat(),
                            value.y.toFloat()
                        )
                        fillPath.lineTo(
                            value.x.toFloat(),
                            value.y.toFloat()
                        )
                        fillPath.lineTo(
                            value.x.toFloat(),
                            getYStartOffset() + mCoordinateView!!.startPositionEdgePixel
                        )
                        fillPath.close()
                    }
                    else -> {
                        path.lineTo(
                            value.x.toFloat(),
                            value.y.toFloat()
                        )
                        fillPath.lineTo(
                            value.x.toFloat(),
                            value.y.toFloat()
                        )
                    }
                }
            }

            canvas.drawPath(path, pathPaint)
            fillPaint.shader = LinearGradient(
                0f,
                getYStartOffset() + mCoordinateView!!.startPositionEdgePixel,
                0f,
                getYStartOffset() - (adapter.getMaxValue().getYValue() * getYPerOffsetPixel()),
                startColor,
                endColor,
                Shader.TileMode.CLAMP
            )
            canvas.drawPath(fillPath, fillPaint)
        }
    }
}