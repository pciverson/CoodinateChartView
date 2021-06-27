package com.pciverson.coordinatechartview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 *
 * @author:allen
 * @date:2021/6/23
 * 您好，世界！
 *
 * 坐标系View
 **/
class CoordinateView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var minHeight: Int
    private var minWidth: Int
    private var mContext: Context = context


    //坐标笔
    private lateinit var coordinateLinePaint: Paint

    //坐标文字笔
    private lateinit var coordinatePaint: Paint

    // X坐标文字高度
    private var coordinateLetterHeight: Int = ViewUtils.dp2px(mContext, 20f)

    // Y坐标文字宽度
    private var coordinateTextWidth: Int = ViewUtils.dp2px(mContext, 20f)

    //x轴起始坐标偏移量
    private var xStartPositionPixel = 0f

    //y轴起始坐标偏移量
    private var yStartPositionPixel = 0f

    //x轴单位偏移量的像素数
    private var xk = 1f

    //y轴单位偏移量的像素数
    private var yk = 1f

    //距离坐标末端的间距（也就是x/y最后一个点的）
    var endPositionEdgePixel = ViewUtils.dp2px(mContext, 20f)

    //距离坐标起始端的间距（也就是x/y第一个点的）
    var startPositionEdgePixel = ViewUtils.dp2px(mContext, 20f)

    //坐标颜色
    var coordinateColor = Color.parseColor("#DBDBDB")
        set(value) {
            if (value != field) {
                coordinateLinePaint.color = value
                field = value
                invalidate()
            }
        }

    //坐标文字颜色
    var coordinateTextColor = Color.parseColor("#999999")
        set(value) {
            if (value != field) {
                coordinatePaint.color = value
                field = value
                invalidate()
            }
        }

    //坐标线粗细
    var coordinateLineWidth = ViewUtils.dp2px(mContext, 0.5f)
        set(value) {
            if (value != field) {
                coordinateLinePaint.strokeWidth = value.toFloat()
                field = value
                invalidate()
            }
        }

    //坐标文字大小
    var coordinateTextSize = 30f
        set(value) {
            if (value != field) {
                coordinatePaint.textSize = value
                field = value
                invalidate()
            }
        }

    //坐标文字和坐标线直接的间隔
    var coordinateWithTextSpace = ViewUtils.dp2px(mContext, 10f)
        set(value) {
            if (value != field) {
                field = value
                invalidate()
            }
        }

    //坐标类型
    var mChartViews: MutableList<ChartView> = arrayListOf()

    init {
        minHeight = ViewUtils.dp2px(mContext, 200f)
        minWidth = ViewUtils.dp2px(mContext, 300f)
        initPaint()
    }

    private fun initPaint() {
        coordinateLinePaint = Paint()
        coordinateLinePaint.isAntiAlias = true
        coordinateLinePaint.color = coordinateColor
        coordinateLinePaint.strokeWidth = coordinateLineWidth.toFloat()

        coordinatePaint = Paint()
        coordinatePaint.isAntiAlias = true
        coordinatePaint.color = coordinateTextColor
        coordinatePaint.strokeWidth = coordinateLineWidth.toFloat()
        coordinatePaint.textSize = coordinateTextSize
        coordinateLetterHeight = measureTextHeight("例子", coordinatePaint)
    }

    private fun measureTextHeight(text: String, coordinatePaint: Paint): Int {
        val rect = Rect()
        coordinatePaint.getTextBounds(text, 0, 1, rect)
        return rect.bottom - rect.top
    }

    private fun measureTextWidth(text: String, coordinatePaint: Paint): Int {
        val rect = Rect()
        coordinatePaint.getTextBounds(text, 0, text.length, rect)
        return rect.right - rect.left
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST) {
            width = minWidth
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            height = minHeight
        }

        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {

            drawCoordinateLine(this)

            drawCoordinateValue(this)

            drawTouchFloatView(this)
        }
    }


    private var coordinateAdapter: CoordinateAdapter<*>? = null

    fun getAdapter(): CoordinateAdapter<*>? {
        return coordinateAdapter
    }

    fun setAdapter(coordinateAdapter: CoordinateAdapter<*>) {
        this.coordinateAdapter = coordinateAdapter
        findYTextMaxWidth()
        invalidate()
    }

    private fun findYTextMaxWidth() {
        coordinateAdapter?.let { it ->
            it.getCoordinateYs().forEach {
                val measureTextWidth = measureTextWidth(it.getShowText(), coordinatePaint)
                if (measureTextWidth > coordinateTextWidth) {
                    coordinateTextWidth = measureTextWidth
                }
            }
        }
    }

    /**
     * 画坐标系
     */
    private fun drawCoordinateLine(canvas: Canvas) {
        drawCoordinateLineX(canvas)
        drawCoordinateLineY(canvas)
    }

    private fun drawCoordinateLineY(canvas: Canvas) {
        yCoordinatePixel =
            (measuredHeight - paddingBottom - coordinateLetterHeight - coordinateWithTextSpace).toFloat() - paddingTop.toFloat()
        canvas.drawLine(
            (paddingLeft + coordinateTextWidth + coordinateWithTextSpace).toFloat(),
            (measuredHeight - paddingBottom - coordinateLetterHeight - coordinateWithTextSpace).toFloat(),
            (paddingLeft + coordinateTextWidth + coordinateWithTextSpace).toFloat(),
            paddingTop.toFloat(),
            coordinateLinePaint
        )

        coordinateAdapter?.getCoordinateYs()?.let { it ->
            //坐标原点到第一个点的偏移值对应的像素值
            startPositionEdgePixel =
                if (coordinateAdapter!!.getYMinValue()
                        .getValue() == 0f
                ) 0 else startPositionEdgePixel
            yStartPositionPixel =
                (measuredHeight - paddingBottom - coordinateLetterHeight - coordinateWithTextSpace).toFloat() - startPositionEdgePixel

            val totalOffset =
                coordinateAdapter!!.getYMaxValue().getValue() - coordinateAdapter!!.getYMinValue()
                    .getValue()
            yk = (yCoordinatePixel - startPositionEdgePixel - endPositionEdgePixel) / totalOffset

            it.forEach { iCoordinateData ->
                canvas.drawText(
                    iCoordinateData.getShowText(),
                    paddingLeft.toFloat(),
                    yStartPositionPixel - yk * (iCoordinateData.getValue() - coordinateAdapter!!.getYMinValue()
                        .getValue()),
                    coordinatePaint
                )
                canvas.drawLine(
                    (paddingLeft + coordinateTextWidth + coordinateWithTextSpace).toFloat(),
                    yStartPositionPixel - yk * (iCoordinateData.getValue() - coordinateAdapter!!.getYMinValue()
                        .getValue()),
                    (measuredWidth - paddingRight).toFloat(),
                    yStartPositionPixel - yk * (iCoordinateData.getValue() - coordinateAdapter!!.getYMinValue()
                        .getValue()),
                    coordinateLinePaint
                )
            }
        }
    }

    //x坐标长度
    private var xCoordinatePixel = 0f

    //y坐标长度
    private var yCoordinatePixel = 0f

    private fun drawCoordinateLineX(canvas: Canvas) {
        //计算出文字高度，横坐标底部预留一行文字高度的空间
        xCoordinatePixel =
            (measuredWidth - paddingRight).toFloat() - (paddingLeft + coordinateTextWidth + coordinateWithTextSpace).toFloat()
        canvas.drawLine(
            (paddingLeft + coordinateTextWidth + coordinateWithTextSpace).toFloat(),
            (measuredHeight - coordinateLetterHeight - paddingBottom - coordinateWithTextSpace).toFloat(),
            (measuredWidth - paddingRight).toFloat(),
            (measuredHeight - coordinateLetterHeight - paddingBottom - coordinateWithTextSpace).toFloat(),
            coordinateLinePaint
        )

        coordinateAdapter?.getCoordinateXs()?.let { it ->
            //坐标原点到第一个点的偏移值对应的像素值
            startPositionEdgePixel =
                if (coordinateAdapter!!.getXMinValue()
                        .getValue() == 0f
                ) 0 else startPositionEdgePixel

            xStartPositionPixel =
                startPositionEdgePixel + (paddingLeft + coordinateTextWidth + coordinateWithTextSpace).toFloat()

            val totalOffset =
                coordinateAdapter!!.getXMaxValue().getValue() - coordinateAdapter!!.getXMinValue()
                    .getValue()
            xk = (xCoordinatePixel - startPositionEdgePixel - endPositionEdgePixel) / totalOffset
            var lastShowTextXPixel = startPositionEdgePixel.toFloat()
            it.forEach { iCoordinateData ->
                val measureTextWidth =
                    measureTextWidth(iCoordinateData.getShowText(), coordinatePaint)
                //文字显示不下，就省略
                val fl =
                    xk * iCoordinateData.getValue() + xStartPositionPixel - lastShowTextXPixel
                if (measureTextWidth < fl) {
                    canvas.drawText(
                        iCoordinateData.getShowText(),
                        xk * (iCoordinateData.getValue() - coordinateAdapter!!.getXMinValue()
                            .getValue()) + xStartPositionPixel - measureTextWidth / 2,
                        (measuredHeight - paddingBottom).toFloat(),
                        coordinatePaint
                    )
                    lastShowTextXPixel =
                        xk * iCoordinateData.getValue() + xStartPositionPixel + measureTextWidth / 2
                }
            }
        }
    }

    fun addChartView(chartView: ChartView) {
        mChartViews.add(chartView)
        chartView.setCoordinateView(this)
        invalidate()
    }

    /**
     * 画里面的坐标值
     */
    private fun drawCoordinateValue(canvas: Canvas) {
        mChartViews.forEach {
            it.let {
                it.draw(canvas)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event?.apply {
            val rect = Rect(
                xStartPositionPixel.toInt(),
                endPositionEdgePixel,
                (xStartPositionPixel + xCoordinatePixel).toInt(),
                yCoordinatePixel.toInt()
            )
            if (rect.contains(x.toInt(), y.toInt())) {
                when (action) {
                    MotionEvent.ACTION_DOWN -> {
                        mChartViews.forEach {
                            it.touchDown(findXCoordinate(x))
                        }
                    }
                    MotionEvent.ACTION_MOVE -> {

                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        mChartViews.forEach {
                            it.touchUp()
                        }
                    }
                }
                return true
            } else {
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mChartViews.forEach {
                        it.touchUp()
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun findXCoordinate(touchX: Float): ICoordinateData? {
        var d = Float.MAX_VALUE
        var target: ICoordinateData? = null
        coordinateAdapter?.getCoordinateXs()?.forEach {
            val abs =
                Math.abs(
                    getXStartOffset() + (it.getValue() - coordinateAdapter!!.getXMinValue()
                        .getValue()) * getXPerOffsetPixel() - touchX
                )
            if (abs < d) {
                d = abs
                target = it
            }
        }
        return target
    }

    /**
     * 画touch时，显示的y值
     */
    private fun drawTouchFloatView(canvas: Canvas) {
        mChartViews.forEach {
            it.drawTouchFloatView(canvas)
        }
    }

    fun getXPerOffsetPixel(): Float {
        return xk
    }

    fun getYPerOffsetPixel(): Float {
        return yk
    }

    fun getXStartOffset(): Float {
        return xStartPositionPixel
    }

    fun getYStartOffset(): Float {
        return yStartPositionPixel
    }
}