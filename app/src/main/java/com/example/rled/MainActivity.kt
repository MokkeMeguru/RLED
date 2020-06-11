package com.example.rled

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.TextView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity(), OnCheckedChangeListener{
    private lateinit var ledToggleButton1: CompoundButton
    private lateinit var ledToggleButton2: CompoundButton
    private lateinit var ledToggleButton3: CompoundButton
    private lateinit var ledToggleButton4: CompoundButton
    private lateinit var ledToggleButton5: CompoundButton
    private lateinit var httpStatus: TextView
    private var ledNum: Int = 0;
    private final val LED1 = 0
    private final val LED2 = 1
    private final val LED3 = 2
    private final val LED4 = 3
    private final val LED5 = 4
    private var stat = 0
    private lateinit var queue: RequestQueue
    private lateinit var task: RaspberryPiLedHttpGetTask
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ledToggleButton1 = findViewById<CompoundButton>(R.id.toggle_button_led_1)
        ledToggleButton2 = findViewById<CompoundButton>(R.id.toggle_button_led_2)
        ledToggleButton3 = findViewById<CompoundButton>(R.id.toggle_button_led_3)
        ledToggleButton4 = findViewById<CompoundButton>(R.id.toggle_button_led_4)
        ledToggleButton5 = findViewById<CompoundButton>(R.id.toggle_button_led_5)
        ledToggleButton1.setOnCheckedChangeListener(this)
        ledToggleButton2.setOnCheckedChangeListener(this)
        ledToggleButton3.setOnCheckedChangeListener(this)
        ledToggleButton4.setOnCheckedChangeListener(this)
        ledToggleButton5.setOnCheckedChangeListener(this)

        httpStatus = findViewById(R.id.http_status)
        this.queue =  Volley.newRequestQueue(this)
        task =  RaspberryPiLedHttpGetTask(this.queue, this.httpStatus)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

        if (buttonView != null) {
            when(buttonView.id) {
                R.id.toggle_button_led_1 -> ledNum = LED1
                R.id.toggle_button_led_2 -> ledNum = LED2
                R.id.toggle_button_led_3 -> ledNum = LED3
                R.id.toggle_button_led_4 -> ledNum = LED4
                R.id.toggle_button_led_5 -> ledNum = LED5
            }
        }
        stat = if (isChecked) {
            1
        } else {
            0
        }
        task.httpGet(ledNum, stat)
    }
}