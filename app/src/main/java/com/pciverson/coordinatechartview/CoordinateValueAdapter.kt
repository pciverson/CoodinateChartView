package com.pciverson.coordinatechartview

import android.graphics.Point

/**
 *
 * @author:allen
 * @date:2021/6/24
 * 您好，世界！
 *
 **/
abstract class CoordinateValueAdapter<V : ICoordinateValue> {
    private val mPoints = arrayListOf<Point>()
    private var mChartView: ChartView? = null

    abstract fun getCoordinateValues(): List<V>

    fun getPointList(): List<Point> {
        val coordinateValues = getCoordinateValues()
        mPoints.clear()
        mChartView?.apply {
            coordinateValues.forEach {
                //过滤掉x值不再坐标系区间内的值
                if (it.getXValue() <= getXMaxValue() && it.getXValue() >= getXMinValue()) {
                    mPoints.add(
                        Point(
                            (getXStartOffset() + ((it.getXValue() - getXMinValue()) * getXPerOffsetPixel())).toInt(),
                            (getYStartOffset() - ((it.getYValue() - getYMinValue()) * getYPerOffsetPixel())).toInt()
                        )
                    )
                }
            }
        }
        return mPoints
    }

    fun setChartView(chartView: ChartView) {
        this.mChartView = chartView
    }

    abstract fun getMaxValue(): V
}