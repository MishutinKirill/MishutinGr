package com.example.maklecoovkoordright.ui.main.graphics
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
class CartesianPainter(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    constructor(context: Context) : this(context, null)

    var xMin = -10.0
    var xMax = 10.0
    var yMin = -11.0
    var yMax = 11.0
    var bgPaint = Paint()
    var graph = 1

    var plane = Converter(xMin, xMax, yMin, yMax, width, height)
    val fgPaint = Paint()
    val grPaint = Paint()
    //массивы для точек графиков
    var points1: MutableList<Pair<Double, Double>> = mutableListOf()
    var points2: MutableList<Pair<Double, Double>> = mutableListOf()

    fun init(
        x_min: Double,
        x_max: Double,
        y_min: Double,
        y_max: Double,
        graphColor: Int,
        graphWidth: Int,
        grf: Int,
        axisColor: Int
    ) {
        //фон
        bgPaint.color = 0xffffffd8.toInt()
        //значения для координат
        fgPaint.color = 0xff0000ff.toInt()
        fgPaint.strokeWidth = 3F
        fgPaint.textSize = 30F
        fgPaint.color = axisColor
        //значения для графика
        grPaint.color = graphColor
        grPaint.strokeWidth = graphWidth.toFloat()
        //размеры
        xMin = x_min
        xMax = x_max
        yMin = y_min
        yMax = y_max

        plane = Converter(xMin, xMax, yMin, yMax, width, height)
        points1.clear()
        points2.clear()

        graph = grf

        if (graph == 1)
            graph1(xMin, xMax)
        else graph2(xMin, xMax)

        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        with(plane) {
            width = this@CartesianPainter.width
            height = this@CartesianPainter.height
        }
        canvas?.apply {
            //////заливаем фон
            drawPaint(bgPaint)
            val widthLine = plane.fromXToScreen(0.0)
            val heightLine = plane.fromYToScreen(0.0)
            ///////рисуем координатные прямые
            drawLine(widthLine.toFloat(), 0F, widthLine.toFloat(), height.toFloat(), fgPaint)
            drawLine(0F, heightLine.toFloat(), width.toFloat(), heightLine.toFloat(), fgPaint)

            val k = xMax - xMin + 1
            //////рисуем координаты x
            var f = xMin
            for (i in 0..k.toInt()) {
                val t1X = plane.fromXToScreen(f)
                if (f != 0.0) {
                    drawLine(
                        t1X.toFloat(),
                        heightLine.toFloat() - 10F,
                        t1X.toFloat(),
                        heightLine.toFloat() + 10F,
                        fgPaint
                    )
                    drawText(
                        f.toInt().toString(),
                        t1X.toFloat() - 10F,
                        heightLine.toFloat() + 70F,
                        fgPaint
                    )
                }
                f++
            }
            ///////рисуем координаты y
            val l = yMax - yMin + 1
            for (i in 0..l.toInt()) {
                val t1Y = plane.fromYToScreen(yMin)
                if (yMin != 0.0) {
                    drawLine(
                        widthLine.toFloat() - 10F,
                        t1Y.toFloat(),
                        widthLine.toFloat() + 10F,
                        t1Y.toFloat(),
                        fgPaint
                    )
                    drawText(
                        yMin.toInt().toString(),
                        widthLine.toFloat() - 70F,
                        t1Y.toFloat() + 10F,
                        fgPaint
                    )
                }
                yMin++
            }
            /////////рисуем график 1 либо 2
            if (graph == 1)
                for (i in 0..((xMax - xMin) / 0.1 - 1).toInt()) {
                    val p1X = plane.fromXToScreen(points1[i].first)
                    val p1Y = plane.fromYToScreen(points1[i].second)
                    val p2X = plane.fromXToScreen(points1[i + 1].first)
                    val p2Y = plane.fromYToScreen(points1[i + 1].second)
                    drawLine(p1X.toFloat(), p1Y.toFloat(), p2X.toFloat(), p2Y.toFloat(), grPaint)
                }
            else
                for (i in 0..((xMax - xMin) * 2 / 0.1 - 1).toInt()) {
                    val p1X = plane.fromXToScreen(points2[i].first)
                    val p1Y = plane.fromYToScreen(points2[i].second)
                    val p2X = plane.fromXToScreen(points2[i + 1].first)
                    val p2Y = plane.fromYToScreen(points2[i + 1].second)
                    drawLine(p1X.toFloat(), p1Y.toFloat(), p2X.toFloat(), p2Y.toFloat(), grPaint)
                }
        }
    }
    /////////вычисление точек 1 графа
    private fun graph1(xMin: Double, xMax: Double) {
        var x = xMin
        val h = 0.1
        while (x <= xMax) {
            val yi = String.format("%.5f", 4*x - 7 * sin(x))
            val y = yi.replace(',','.').toDouble()
            points1.add(Pair(x, y))
            val xi = String.format("%.5f", x + h)
            x = xi.replace(',','.').toDouble()
        }
        var i = 0
        points1.forEach {

            Log.d("points", "$it $i")
            i++
        }
        invalidate()
    }
    ////////вычисление точек 2 графа
    private fun graph2(xMin: Double, xMax: Double) {
        var x = xMin
        val h = 0.1
        while (x <= xMax) {
            val yi = String.format("%.5f", sqrt(16 + 16 * x * x / 4))
            val y = yi.replace(',','.').toDouble()
            points2.add(Pair(x, y))
            val xi = String.format("%.5f", x + h)
            x = xi.replace(',','.').toDouble()
        }
        x = xMax
        while (x >= xMin) {
            val yi = String.format("%.5f", -sqrt(16 + 16 * x * x / 4))
            val y = yi.replace(',','.').toDouble()
            points2.add(Pair(x, y))
            val xi = String.format("%.5f", x - h)
            x = xi.replace(',','.').toDouble()
        }
        var i = 0
        points2.forEach {

            Log.d("points", "$it $i")
            i++
        }
        invalidate()
    }


}