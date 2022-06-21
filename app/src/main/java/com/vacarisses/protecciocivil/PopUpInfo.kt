package com.vacarisses.protecciocivil

import android.animation.Animator
import com.google.firebase.ktx.Firebase
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.core.graphics.ColorUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_pop_up_data.*
import kotlinx.android.synthetic.main.activity_pop_up_data.popup_window_background
import kotlinx.android.synthetic.main.activity_pop_up_data.popup_window_view_with_border
import kotlinx.android.synthetic.main.activity_pop_up_data.vehicleTextView
import kotlinx.android.synthetic.main.activity_pop_up_info.*

class PopUpInfo : AppCompatActivity() {
    private var popupVehicle = ""
    private var darkStatusBar = false
    private val database = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0,0)
        setContentView(R.layout.activity_pop_up_info)

        // importamos datos desde el mainactivity
        val bundle = intent.extras
        popupVehicle = bundle?.getString("popupVehicle", "") ?: ""
        darkStatusBar = bundle?.getBoolean("darkstatusbar", false) ?:false

        //set data into labels
        vehicleTextView.setText(popupVehicle)

        //fade animation for the background of popup window
        val alpha = 100 //btwn 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 //milliseconds
        colorAnimation.addUpdateListener { animator ->
            popup_window_background.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()

        //fade animation popup window
        popup_window_view_with_border.alpha = 0f
        popup_window_view_with_border.animate().alpha(1f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        // Close the Popup Window when you press the button
        tornarButton.setOnClickListener {
            finish()
        }

        //show the data on firebase ddbb

            database.collection(popupVehicle).document("info").get().addOnSuccessListener{
                itvEstatTextView.setText(it.get("ITVestat")as String?)
                fechaItvTextView.setText(it.get("ITVdata") as String?)
                kilometresTextView.setText(it.get("Kilometres")as String?)
                ultrevisioTextView.setText(it.get("Ultrevisio") as String?)
                ultcheckTextView.setText(it.get("Ultcheck") as String?)
                numPlacesTextView.setText(it.get("Places") as String?)
                numBastidorTextView.setText(it.get("Bastidor") as String?)
                numMatriculaTextView.setText(it.get("Matricula") as String?)
            }




    }

    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            popup_window_background.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        // Fade animation for the Popup Window when you press the back button
        popup_window_view_with_border.animate().alpha(0f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()

    }





}
