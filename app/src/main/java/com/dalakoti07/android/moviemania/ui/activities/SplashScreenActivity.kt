package com.dalakoti07.android.moviemania.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.dalakoti07.android.moviemania.R
import kotlinx.android.synthetic.main.activity_spash_screen.*
import kotlinx.android.synthetic.main.activity_spash_screen.view.*

class SplashScreenActivity : AppCompatActivity() {
    private val TIME_OUT_TIME = 3000
    lateinit var topAnimation: Animation
    lateinit var bottomAnimation:Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_spash_screen)
        topAnimation=AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation=AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        startAnimation()

        Handler().postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            overridePendingTransition(R.anim.next_screen_move_in, R.anim.next_screen_move_out)
            finish()
        }, TIME_OUT_TIME.toLong())
    }

    private fun startAnimation() {
        iv_logo.animation=topAnimation
        tv_name.animation=bottomAnimation
        tv_tagline.animation=bottomAnimation
    }
}