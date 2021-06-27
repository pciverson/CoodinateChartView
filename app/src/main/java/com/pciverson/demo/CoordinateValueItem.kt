package com.pciverson.demo

import com.pciverson.coordinatechartview.ICoordinateValue

/**
 *
 * @author:allen
 * @date:2021/6/25
 * 您好，世界！
 *
 **/
class CoordinateValueItem(var xOffset: Float, var yOffset: Float) : ICoordinateValue {
    override fun getXValue(): Float {
        return xOffset
    }

    override fun getYValue(): Float {
        return yOffset
    }
}