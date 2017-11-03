package com.fengniao.news.module.test

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fengniao.news.R
import kotlinx.android.synthetic.main.activity_property_animation.*

class PropertyAnimationActivity : AppCompatActivity() {
    lateinit var translationXAnimator: ObjectAnimator
    lateinit var scaleYAnimator: ObjectAnimator
    lateinit var alphaAnimator: ObjectAnimator
    lateinit var rotationAnimator: ObjectAnimator
    lateinit var animatorSet: AnimatorSet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_animation)
        initView()
        initAnimation()
    }

    private fun initView() {
        btn_test.setOnClickListener {
            //            translationXAnimator.start()
//            scaleYAnimator.start()
//           rotationAnimator.start()
            animatorSet.start()
        }

    }

    private fun initAnimation() {
        //平移
        translationXAnimator = ObjectAnimator.ofFloat(tv_test, "translationX",
                tv_test.translationX, (-500).toFloat(), tv_test.translationX)
        translationXAnimator.duration = 2000
        //伸缩
        scaleYAnimator = ObjectAnimator.ofFloat(tv_test, "scaleY", 1f, 3f, 1f)
        scaleYAnimator.duration = 2000
        //透明度
        alphaAnimator = ObjectAnimator.ofFloat(tv_test, "alpha", 1f, 0f, 1f)
        alphaAnimator.duration = 2000
        //旋转
        rotationAnimator = ObjectAnimator.ofFloat(tv_test, "rotation", 0f, 360f)
        rotationAnimator.duration = 2000

        animatorSet = AnimatorSet()
        animatorSet.play(translationXAnimator).with(scaleYAnimator).before(alphaAnimator).after(rotationAnimator)
        animatorSet.duration = 5000
    }
}
