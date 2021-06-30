package com.pciverson.coordinatechartview

import android.graphics.RectF
import java.util.*

/**
 *
 * @author:allen
 * @date:2021/6/30
 * 您好，世界！
 * 管理悬浮view位置重叠问题
 *
 **/
object FloatViewPositionManager {
    val pointPosition = TreeSet(object : Comparator<RectF> {
        override fun compare(o1: RectF?, o2: RectF?): Int {
            if (o1?.centerX() == o2?.centerX() && o1?.centerY() == o2?.centerY()) {
                return 0
            }
            if (o1 != null && o2 != null) {
                return if (o1.centerY() > o2.centerY()) {
                    -1
                } else {
                    1
                }
            }
            return 1
        }
    })

    fun clear() {
        pointPosition.clear()
    }
}