package com.pciverson.coordinatechartview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint

/**
 *
 * @author:allen
 * @date:2021/6/26
 * 您好，世界！
 *
 **/
abstract class LineChartView(context: Context) : ChartView() {
    //线条笔
    protected lateinit var pathPaint: Paint

    //填充笔
    protected lateinit var fillPaint: Paint

    /**
     * 填充渐变色的开始色
     */
    var startColor: Int = Color.parseColor("#1100C1BA")
        set(value) {
            field = value
            mCoordinateView?.invalidate()
        }

    /**
     * 填充渐变色的结束色
     */
    var endColor: Int = Color.parseColor("#6600C1BA")
        set(value) {
            field = value
            mCoordinateView?.invalidate()
        }

    //线条颜色
    var lineColor = Color.parseColor("#00BCBC")
        set(value) {
            pathPaint.color = value
            field = value
            mCoordinateView?.invalidate()
        }

    //线条粗细
    var lineWidth = ViewUtils.dp2px(context, 1f)
        set(value) {
            pathPaint.strokeWidth = lineWidth.toFloat()
            field = value
            mCoordinateView?.invalidate()
        }

    init {
        initPaint()
    }

    private fun initPaint() {
        pathPaint = Paint()
        pathPaint.isAntiAlias = true
        pathPaint.style = Paint.Style.STROKE
        pathPaint.strokeWidth = lineWidth.toFloat()
        pathPaint.color = lineColor

        fillPaint = Paint()
        fillPaint.isAntiAlias = true
        fillPaint.style = Paint.Style.FILL
    }
}