package com.pciverson.demo

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pciverson.coordinatechartview.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        val cv = findViewById<CoordinateView>(R.id.cv)
        cv.setAdapter(object :
            CoordinateAdapter<CoordinateItem>() {
            override fun getCoordinateXs(): TreeSet<CoordinateItem> {
                return xList
            }

            override fun getCoordinateYs(): TreeSet<CoordinateItem> {
                return yList
            }
        })
        val curveView = BarChartView(this, ViewUtils.dp2px(this, 12f))
        curveView.lineColor = Color.parseColor("#FF3131")
        curveView.startColor = Color.parseColor("#11FF3131")
        curveView.endColor = Color.parseColor("#66FF3131")
        val ovalTouchFloatView =
            BarTouchFloatView(this, ViewUtils.dp2px(this, 12f), ViewUtils.dp2px(this, 12f))
        ovalTouchFloatView.floatLineColor = Color.parseColor("#FF3131")
        curveView.setTouchFloatView(ovalTouchFloatView)

        curveView.setAdapter(object : CoordinateValueAdapter<CoordinateValueItem>() {
            override fun getCoordinateValues(): List<CoordinateValueItem> {
                return values
            }

            override fun getMaxValue(): CoordinateValueItem {
                return values[2]
            }
        })

        val curveView1 = BarChartView(this)
        curveView1.lineColor = Color.parseColor("#50E383")
        curveView1.startColor = Color.parseColor("#1150E383")
        curveView1.endColor = Color.parseColor("#6650E383")

        val ovalTouchFloatView1 = BarTouchFloatView(this, ViewUtils.dp2px(this, 12f))
        ovalTouchFloatView1.floatLineColor = Color.parseColor("#50E383")
        curveView1.setTouchFloatView(ovalTouchFloatView1)

        curveView1.setAdapter(object : CoordinateValueAdapter<CoordinateValueItem>() {
            override fun getCoordinateValues(): List<CoordinateValueItem> {
                return values1
            }

            override fun getMaxValue(): CoordinateValueItem {
                return values1[5]
            }
        })

        val curveView2 = PolylineChartView(this)
        curveView2.lineColor = Color.parseColor("#E97528")
        curveView2.startColor = Color.parseColor("#11E97528")
        curveView2.endColor = Color.parseColor("#66E97528")
        val ovalTouchFloatView2 =
            LineTouchFloatView(this)
        ovalTouchFloatView2.floatLineColor = Color.parseColor("#E97528")
        curveView2.setTouchFloatView(ovalTouchFloatView2)

        curveView2.setAdapter(object : CoordinateValueAdapter<CoordinateValueItem>() {
            override fun getCoordinateValues(): List<CoordinateValueItem> {
                return values
            }

            override fun getMaxValue(): CoordinateValueItem {
                return values[2]
            }
        })

        val curveView3 = CurveChartView(this)
        curveView3.lineColor = Color.parseColor("#1970F3")
        curveView3.startColor = Color.parseColor("#1119DAF3")
        curveView3.endColor = Color.parseColor("#6619DAF3")
        val ovalTouchFloatView3 =
            LineTouchFloatView(this)
        ovalTouchFloatView2.floatLineColor = Color.parseColor("#1970F3")
        curveView3.setTouchFloatView(ovalTouchFloatView3)

        curveView3.setAdapter(object : CoordinateValueAdapter<CoordinateValueItem>() {
            override fun getCoordinateValues(): List<CoordinateValueItem> {
                return values1
            }

            override fun getMaxValue(): CoordinateValueItem {
                return values1[5]
            }
        })

        cv.addChartView(curveView3)
        cv.addChartView(curveView2)
        cv.addChartView(curveView1)
        cv.addChartView(curveView)
    }

    private val xList = TreeSet<CoordinateItem>()
    private val yList = TreeSet<CoordinateItem>()
    private val values = arrayListOf<CoordinateValueItem>()
    private val values1 = arrayListOf<CoordinateValueItem>()

    private fun initData() {
        xList.add(CoordinateItem("01:00", 0f))
        xList.add(CoordinateItem("01:30", 10f))
        xList.add(CoordinateItem("02:00", 20f))
        xList.add(CoordinateItem("02:30", 30f))
        xList.add(CoordinateItem("03:00", 40f))
        xList.add(CoordinateItem("03:30", 50f))

        yList.add(CoordinateItem(" 0", 0f))
        yList.add(CoordinateItem("10", 10f))
        yList.add(CoordinateItem("20", 20f))
        yList.add(CoordinateItem("30", 30f))
        yList.add(CoordinateItem("40", 40f))
        yList.add(CoordinateItem("50", 50f))

        values.add(CoordinateValueItem(0f, 15f))
        values.add(CoordinateValueItem(10f, 10f))
        values.add(CoordinateValueItem(20f, 50f))
        values.add(CoordinateValueItem(30f, 40f))
        values.add(CoordinateValueItem(40f, 10f))
        values.add(CoordinateValueItem(50f, 30f))

        values1.add(CoordinateValueItem(0f, 40f))
        values1.add(CoordinateValueItem(10f, 30f))
        values1.add(CoordinateValueItem(20f, 20f))
        values1.add(CoordinateValueItem(30f, 40f))
        values1.add(CoordinateValueItem(40f, 30f))
        values1.add(CoordinateValueItem(50f, 45f))
    }
//柱状
}