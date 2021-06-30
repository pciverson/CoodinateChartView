package com.pciverson.coordinatechartview

import android.content.Context
import android.graphics.*

/**
 *
 * @author:allen
 * @date:2021/6/26
 * 您好，世界！
 *
 * 折线图的触控悬浮视图
 **/
class LineTouchFloatView(var context: Context) : TouchFloatView() {
    private lateinit var floatPaint: Paint
    private lateinit var textPaint: Paint

    private var textOffsetY = ViewUtils.dp2px(context, 10f)
    private var radius = ViewUtils.dp2px(context, 4f)
    private var selectPointRadius = ViewUtils.dp2px(context, 4f)

    var floatLineColor = Color.parseColor("#00BCBC")
        set(value) {
            field = value
            floatPaint.color = value
        }

    init {
        initPaint()
    }

    private fun initPaint() {
        floatPaint = Paint()
        floatPaint.isAntiAlias = true
        floatPaint.strokeWidth = ViewUtils.dp2px(context, 1f).toFloat()
        floatPaint.textSize = 50f
        floatPaint.style = Paint.Style.FILL
        floatPaint.color = floatLineColor

        textPaint = Paint()
        textPaint.isAntiAlias = true
        textPaint.strokeWidth = ViewUtils.dp2px(context, 1f).toFloat()
        textPaint.textSize = 30f
        textPaint.color = Color.WHITE
    }

    override fun draw(canvas: Canvas, point: Point, it: ICoordinateValue?) {
        //绘制之前检测位置是否冲突
        it?.let { it ->
            textOffsetY = ViewUtils.dp2px(context, 10f)
            val txt = it.getYValue().toString()
            val rect = Rect()
            floatPaint.getTextBounds(txt, 0, txt.length, rect)
            val textBgWidth = rect.right - rect.left
            val textBgHeight = rect.bottom - rect.top

            var rectF = RectF(
                point.x.toFloat() - textBgWidth / 2,
                (point.y - textOffsetY - textBgHeight).toFloat(),
                point.x.toFloat() + textBgWidth / 2,
                (point.y - textOffsetY).toFloat()
            )
            FloatViewPositionManager.pointPosition.forEach {
                if (rectF.intersect(it)) {
                    textOffsetY -= (it.top - it.bottom).toInt()
                    rectF = RectF(
                        point.x.toFloat() - textBgWidth / 2,
                        (point.y - textOffsetY - textBgHeight).toFloat(),
                        point.x.toFloat() + textBgWidth / 2,
                        (point.y - textOffsetY).toFloat()
                    )
                }
            }
            canvas.drawRoundRect(
                rectF
                , radius.toFloat(), radius.toFloat(), floatPaint
            )
            canvas.drawLine(
                point.x.toFloat(),
                mChartView!!.getYStartOffset(),
                point.x.toFloat(),
                (point.y - textOffsetY).toFloat(), floatPaint
            )
            FloatViewPositionManager.pointPosition.add(rectF)

            val textRect = Rect()
            textPaint.getTextBounds(txt, 0, txt.length, textRect)

            val textWidth = textRect.right - textRect.left
            val textHeight = textRect.bottom - textRect.top

            canvas.drawText(
                txt,
                point.x.toFloat() - textWidth / 2,
                (point.y - textOffsetY - ((textBgHeight - textHeight) / 2)).toFloat(),
                textPaint
            )

            canvas.drawOval(
                RectF(
                    point.x.toFloat() - selectPointRadius,
                    (point.y - selectPointRadius).toFloat(),
                    point.x.toFloat() + selectPointRadius,
                    (point.y + selectPointRadius).toFloat()
                ),
                floatPaint
            )
        }
    }
}