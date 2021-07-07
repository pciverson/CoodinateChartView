package com.pciverson.coordinatechartview

import android.graphics.Canvas
import android.graphics.Point

/**
 *
 * @author:allen
 * @date:2021/6/25
 * 您好，世界！
 * 坐标的值的视图，子类实现各种图表
 * 折线图、柱状图、内塞尔曲线图
 **/
abstract class ChartView {

    private var mTouchDownPositionListener: TouchDownPositionListener? = null

    protected var mCoordinateView: CoordinateView? = null

    protected var mCoordinateValueAdapter: CoordinateValueAdapter<*>? = null

    private var mTouchFloatView: TouchFloatView? = null

    private var mTouchPoint: Point? = null

    private var mCoordinateData: ICoordinateData? = null

    fun getXPerOffsetPixel(): Float {
        return mCoordinateView!!.getXPerOffsetPixel()
    }

    fun getYPerOffsetPixel(): Float {
        return mCoordinateView!!.getYPerOffsetPixel()
    }

    fun getXStartOffset(): Float {
        return mCoordinateView!!.getXStartOffset()
    }

    fun getYStartOffset(): Float {
        return mCoordinateView!!.getYStartOffset()
    }

    fun setCoordinateView(coordinateView: CoordinateView) {
        this.mCoordinateView = coordinateView
    }

    fun setAdapter(coordinateValueAdapter: CoordinateValueAdapter<*>) {
        this.mCoordinateValueAdapter = coordinateValueAdapter
        mCoordinateValueAdapter?.setChartView(this)
    }

    fun setTouchFloatView(touchFloatView: TouchFloatView) {
        this.mTouchFloatView = touchFloatView
        this.mTouchFloatView?.setChartView(this)
    }

    fun getXMinValue(): Float {
        return mCoordinateView?.getAdapter()?.getXMinValue()!!.getValue()
    }

    fun getXMaxValue(): Float {
        return mCoordinateView?.getAdapter()?.getXMaxValue()!!.getValue()
    }

    fun getYMinValue(): Float {
        return mCoordinateView?.getAdapter()?.getYMinValue()!!.getValue()
    }

    fun getYMaxValue(): Float {
        return mCoordinateView?.getAdapter()?.getYMaxValue()!!.getValue()
    }

    fun touchDown(cd: ICoordinateData?) {
        mTouchFloatView?.let {
            mTouchPoint = findPointByX(cd)
            mCoordinateData = cd
            mCoordinateView?.invalidate()
        }
    }

    private fun findPointByX(cd: ICoordinateData?): Point? {
        mCoordinateValueAdapter?.getPointList()?.let { it ->
            it.forEach { p ->
                cd?.let {
                    val tx =
                        (getXStartOffset() + (cd.getValue() - getXMinValue()) * getXPerOffsetPixel()).toInt()
                    if (p.x == tx) {
                        return p
                    }
                }
            }
        }
        return null
    }

    fun touchUp() {
        mCoordinateView?.pointPosition?.clear()
    }

    abstract fun draw(
        canvas: Canvas
    )

    fun drawTouchFloatView(canvas: Canvas) {
        mTouchPoint?.apply {
            mCoordinateData?.let { mc ->
                //根据当前点x的值找到y值
                val cv = mCoordinateValueAdapter?.getCoordinateValues()?.find {
                    it.getXValue() == mc.getValue()
                }
                mTouchFloatView?.draw(canvas, this, cv)

                mTouchDownPositionListener?.onTouchDownCoordinateValue(cv)
            }
        }
    }

    fun setTouchDownPositionListener(touchDownPositionListener: TouchDownPositionListener) {
        mTouchDownPositionListener = touchDownPositionListener
    }

    interface TouchDownPositionListener {
        /**
         * 触摸x轴上对应的坐标对应点的值
         */
        fun onTouchDownCoordinateValue(coordinateValue: ICoordinateValue?)
    }
}