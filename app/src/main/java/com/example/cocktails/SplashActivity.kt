package com.example.cocktails

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    private lateinit var splashLogo: ImageView
    private val ANIMATION_DURATION = 2000L
    private val DELAY_TO_MAIN_ACTIVITY = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashLogo = findViewById(R.id.splash_logo)

        supportActionBar?.hide()
        animateLogo()
    }

    private fun animateLogo() {
        val alphaAnimator = ObjectAnimator.ofFloat(splashLogo, View.ALPHA, 0f, 1f).apply {
            duration = ANIMATION_DURATION / 2
            interpolator = AccelerateDecelerateInterpolator()
        }

        val scaleXAnimator = ObjectAnimator.ofFloat(splashLogo, View.SCALE_X, 0.8f, 1f).apply {
            duration = ANIMATION_DURATION
            interpolator = AccelerateDecelerateInterpolator()
        }
        val scaleYAnimator = ObjectAnimator.ofFloat(splashLogo, View.SCALE_Y, 0.8f, 1f).apply {
            duration = ANIMATION_DURATION
            interpolator = AccelerateDecelerateInterpolator()
        }


        alphaAnimator.start()
        scaleXAnimator.start()
        scaleYAnimator.start()

        scaleXAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                goToMainActivity()
            }
        })
    }

    private fun goToMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_TO_MAIN_ACTIVITY)
    }
}