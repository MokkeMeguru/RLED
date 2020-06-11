package com.example.rled

import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.widget.TextView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class RaspberryPiLedHttpGetTask(
    queue: RequestQueue,
    httpStatus: TextView
) {
    private final val DEFAULTURL: String = "http://192.168.0.104/~pi/ledtest.php?num=%d&stat=%d"
    private val httpStatus = httpStatus
    private val queue: RequestQueue = queue

    fun httpGet(ledNum: Int, state: Int): String? {
        this.httpStatus.text = "Loading..."
        Log.d("led", "led$ledNum --- $state")
        Log.d("url",  String.format(this.DEFAULTURL, ledNum, state))
        val stringRequest = StringRequest(Request.Method.GET,
            String.format(this.DEFAULTURL, ledNum, state),
            Response.Listener {
                this.httpStatus.text = "Success!"
           }, Response.ErrorListener {
                this.httpStatus.text = "Failed..."
           })
        stringRequest.retryPolicy = DefaultRetryPolicy(
            1000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        queue.add(stringRequest)
        return "Request is Accepted"
    }
}