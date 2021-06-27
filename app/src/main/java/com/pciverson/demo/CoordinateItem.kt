package com.pciverson.demo

import com.pciverson.coordinatechartview.ICoordinateData

/**
 *
 * @author:allen
 * @date:2021/6/23
 * 您好，世界！
 *showText:显示的坐标
 **/

class CoordinateItem(var mShowText: String, var mValue: Float) :
    ICoordinateData {

    override fun getShowText(): String {
        return mShowText
    }

    override fun getValue(): Float {
        return mValue
    }

    override fun compareTo(other: ICoordinateData): Int {
        return when {
            mValue - other.getValue() > 0 -> {
                1
            }
            mValue - other.getValue() < 0 -> {
                -1
            }
            else -> {
                0
            }
        }
    }
}