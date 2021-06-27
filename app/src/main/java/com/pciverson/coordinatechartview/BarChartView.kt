package com.pciverson.coordinatechartview

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader

/**
 *
 * @author:allen
 * @date:2021/6/26
 * 您好，世界！
 * 柱状图
 * mOffset距离x轴的坐标点偏移距离
 **/
class BarChartView(context: Context, private var mOffset: Int = 0) : LineChartView(context) {
    var barWidth = ViewUtils.dp2px(context, 12f)
        set(value) {
            field = value
            mCoordinateView?.invalidate()
        }

    var roundR = ViewUtils.dp2px(context, 4f)

    override fun draw(canvas: Canvas) {
        mCoordinateValueAdapter?.apply {
            val pointList = getPointList()
            fillPaint.shader = LinearGradient(
                0f,
                getYStartOffset() + mCoordinateView!!.startPositionEdgePixel,
                0f,
                getYStartOffset() - (getMaxValue().getYValue() * getYPerOffsetPixel()),
                startColor,
                endColor,
                Shader.TileMode.CLAMP
            )
            pointList.forEach {
                canvas.drawRoundRect(
                    it.x.toFloat() + mOffset,
                    it.y.toFloat(),
                    it.x.toFloat() + mOffset + barWidth,
                    getYStartOffset(),
                    roundR.toFloat(),
                    roundR.toFloat(),
                    fillPaint
                )
            }
        }
    }
}