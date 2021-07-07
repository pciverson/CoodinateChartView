package com.pciverson.coordinatechartview

import android.graphics.Canvas
import android.graphics.Point

/**
 *
 * @author:allen
 * @date:2021/6/26
 * 您好，世界！
 *
 **/
abstract class TouchFloatView {
    protected var mChartView: ChartView? = null
    protected var mCoordinateView: CoordinateView? = null

    abstract fun draw(
        canvas: Canvas,
        point: Point,
        it: ICoordinateValue?
    )

    fun setChartView(chartView: ChartView) {
        this.mChartView = chartView
    }

    fun setCoordinateView(coordinateView: CoordinateView) {
        this.mCoordinateView = coordinateView;
    }
}