package com.pciverson.coordinatechartview

/**
 *
 * @author:allen
 * @date:2021/6/24
 * 您好，世界！
 *
 **/
interface ICoordinateData : Comparable<ICoordinateData> {
    /**
     * 获取显示的文字
     */
    fun getShowText(): String

    /**
     * 距离前一个坐标的偏移值（最大值减去最小值除以坐标格数就得到偏移比例）,第一个点（最小点）的偏移值为0
     */
    fun getValue(): Float
}