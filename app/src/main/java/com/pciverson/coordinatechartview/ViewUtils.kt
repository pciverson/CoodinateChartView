package com.pciverson.coordinatechartview

import android.content.Context

/**
 *
 * @author:allen
 * @date:2021/6/25
 * 您好，世界！
 *
 **/
object ViewUtils {

    fun dp2px(mContext: Context, dpValue: Float): Int {
        val scale = mContext.resources.displayMetrics.density;
        return (dpValue * scale + 0.5f).toInt()
    }
}