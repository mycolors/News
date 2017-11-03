package com.fengniao.news.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.fengniao.news.R
import com.fengniao.news.widget.konfetti.Shape
import com.fengniao.news.widget.konfetti.Size
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    private var startX: Float = 0f
    private var startY: Float = 0f
    private var speed: Int = 0
    private var degrees: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        konfetti_view.setOnClickListener(View.OnClickListener {
            konfetti_view.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnable(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .addSizes(Size(12, 5f))
                    .setPosition(-50f, konfetti_view.getWidth() + 50f, -50f, -50f)
                    .stream(300, 5000L)
        })
    }
}
