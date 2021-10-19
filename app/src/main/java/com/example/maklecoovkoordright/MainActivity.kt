package com.example.maklecoovkoordright
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
class MainActivity : AppCompatActivity() {

    var xMin: Double? = null
    var xMax: Double? = null
    var yMin: Double? = null
    var yMax: Double? = null
    var graphColor: Int? = null
    var width: Int? = null
    var graph: Int? = null
    var axisColor: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        ///////////получаем настройки из хранилища
        val sharedPreferences = getSharedPreferences("cartesianSettings", Context.MODE_PRIVATE)

        xMin = if (checkEmptyStr(sharedPreferences.getString("xMin", "-10")))
            sharedPreferences.getString("xMin", "-10")?.toDouble()
        else -10.0

        xMax = if (checkEmptyStr(sharedPreferences.getString("xMax", "10")))
            sharedPreferences.getString("xMax", "10")?.toDouble()
        else 10.0

        yMin = if (checkEmptyStr(sharedPreferences.getString("yMin", "-10")))
            sharedPreferences.getString("yMin", "-10")?.toDouble()
        else -10.0

        yMax = if (checkEmptyStr(sharedPreferences.getString("yMax", "10")))
            sharedPreferences.getString("yMax", "10")?.toDouble()
        else 10.0

        when (sharedPreferences.getInt("graphColor", 0xffff0000.toInt())) {
            R.id.rb_graph_red -> graphColor = 0xffff0000.toInt()
            R.id.rb_graph_green -> graphColor = 0xff00ff00.toInt()
            R.id.rb_graph_blue -> graphColor = 0xff0000ff.toInt()
        }

        when (sharedPreferences.getInt("graphWidth", 5)) {
            R.id.rb_thin -> width = 5
            R.id.rb_medium -> width = 10
            R.id.rb_wide -> width = 15
        }


        when (sharedPreferences.getInt("graph", 1)) {
            R.id.rb_graph1 -> graph = 1
            R.id.rb_graph2 -> graph = 2
        }

        when (sharedPreferences.getInt("axisColor", 0xff000000.toInt())) {
            R.id.rb_axis_black -> axisColor = 0xff000000.toInt()
            R.id.rb_axis_red -> axisColor = 0xffff0000.toInt()
            R.id.rb_axis_blue -> axisColor = 0xff0000ff.toInt()
        }
        //////рисуем координаты и график
        cartesianPainter.init(
            xMin!!,
            xMax!!,
            yMin!!,
            yMax!!,
            graphColor!!,
            width!!,
            graph!!,
            axisColor!!
        )
    }
    //проверка на заполняемость строки
    private fun checkEmptyStr(str: String?): Boolean {
        if (str != null && str.isNotEmpty())
            return true
        return false
    }

}