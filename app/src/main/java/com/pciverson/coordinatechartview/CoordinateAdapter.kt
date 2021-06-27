package com.pciverson.coordinatechartview

import java.util.*

/**
 *
 * @author:allen
 * @date:2021/6/24
 * 您好，世界！
 *
 **/
abstract class CoordinateAdapter<T : ICoordinateData> {

    abstract fun getCoordinateXs(): TreeSet<T>

    abstract fun getCoordinateYs(): TreeSet<T>

    /**
     * 不为0的最小值
     */
    fun getXMinValue(): T {
        return getCoordinateXs().first()
    }

    /**
     * 最大值
     */
    fun getXMaxValue(): T {
        return getCoordinateXs().last()
    }

    /**
     * 不为0的最小值
     */
    fun getYMinValue(): T {
        return getCoordinateYs().first()
    }

    /**
     * 最大值
     */
    fun getYMaxValue(): T {
        return getCoordinateYs().last()
    }
}