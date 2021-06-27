package com.pciverson.coordinatechartview

import android.content.Context
import android.graphics.*

/**
 *
 * @author:allen
 * @date:2021/6/25
 * 您好，世界！
 *
 * 平滑曲线图
 **/
class CurveChartView(context: Context) : LineChartView(context) {

    override fun draw(
        canvas: Canvas
    ) {
        mCoordinateValueAdapter?.let { adapter ->
            val path = Path()
            val fillPath = Path()

            val mPoints = adapter.getPointList()

            for (j in mPoints.indices) {
                val startP: Point = mPoints[j]
                var endP: Point
                if (j != mPoints.size - 1) {
                    endP = mPoints[j + 1]
                    val wt = (startP.x + endP.x) / 2
                    val p3 = Point()
                    val p4 = Point()
                    p3.y = startP.y
                    p3.x = wt
                    p4.y = endP.y
                    p4.x = wt
                    if (j == 0) {
                        path.moveTo(startP.x.toFloat(), startP.y.toFloat())
                        fillPath.moveTo(startP.x.toFloat(), getYStartOffset()+mCoordinateView!!.startPositionEdgePixel)
                        fillPath.lineTo(startP.x.toFloat(), startP.y.toFloat())
                    }
                    path.cubicTo(
                        p3.x.toFloat(),
                        p3.y.toFloat(),
                        p4.x.toFloat(),
                        p4.y.toFloat(),
                        endP.x.toFloat(),
                        endP.y.toFloat()
                    )
                    fillPath.cubicTo(
                        p3.x.toFloat(),
                        p3.y.toFloat(),
                        p4.x.toFloat(),
                        p4.y.toFloat(),
                        endP.x.toFloat(),
                        endP.y.toFloat()
                    )
                } else {
                    fillPath.lineTo(
                        startP.x.toFloat(),
                        getYStartOffset()+mCoordinateView!!.startPositionEdgePixel
                    )
                    fillPath.close()
                }
            }

            canvas.drawPath(path, pathPaint)
            fillPaint.shader = LinearGradient(
                0f,
                getYStartOffset()+mCoordinateView!!.startPositionEdgePixel,
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