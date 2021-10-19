package com.example.maklecoovkoordright
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        loadSettings()

        btn_build.setOnClickListener {
            saveSettings()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    ////////Загрузка настроек из хранилища
    private fun loadSettings() {
        val sharedPreferences = getSharedPreferences("cartesianSettings", Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            et_xMin.setText(sharedPreferences.getString("xMin", "-10"))
            et_xMax.setText(sharedPreferences.getString("xMax", "10"))
            et_yMin.setText(sharedPreferences.getString("yMin", "-10"))
            et_yMax.setText(sharedPreferences.getString("yMax", "10"))
            rg_graph_color.check(sharedPreferences.getInt("graphColor", rb_graph_red.id))
            rg_width.check(sharedPreferences.getInt("graphWidth", rb_medium.id))
            rg_graph.check(sharedPreferences.getInt("graph", rb_graph1.id))
            rg_axis_color.check(sharedPreferences.getInt("axisColor", rb_axis_black.id))
        }
    }

    //////////Сохранение настроек в хранилище
    private fun saveSettings() {
        val sharedPreferences = getSharedPreferences("cartesianSettings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("xMin", et_xMin.text.toString())
        editor.putString("xMax", et_xMax.text.toString())
        editor.putString("yMin", et_yMin.text.toString())
        editor.putString("yMax", et_yMax.text.toString())
        editor.putInt("graphColor", rg_graph_color.checkedRadioButtonId)
        editor.putInt("graphWidth", rg_width.checkedRadioButtonId)
        editor.putInt("graph", rg_graph.checkedRadioButtonId)
        editor.putInt("axisColor", rg_axis_color.checkedRadioButtonId)
        editor.apply()
    }
}
