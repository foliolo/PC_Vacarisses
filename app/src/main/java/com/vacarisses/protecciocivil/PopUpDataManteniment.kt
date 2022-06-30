package com.vacarisses.protecciocivil

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.graphics.ColorUtils
import com.google.firebase.firestore.FirebaseFirestore
import com.vacarisses.protecciocivil.MainActivity.Companion.usernameGlobal
import kotlinx.android.synthetic.main.activity_pop_up_data_kilometratge.*
import kotlinx.android.synthetic.main.activity_pop_up_data_kilometratge.popup_window_background
import kotlinx.android.synthetic.main.activity_pop_up_data_kilometratge.popup_window_view_with_border
import java.time.LocalDateTime

class PopUpDataManteniment : AppCompatActivity() {
    private var popupVehicle = ""
    private var darkStatusBar = false
    private val database = FirebaseFirestore.getInstance()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0,0)
        setContentView(R.layout.activity_pop_up_data_manteniment)


        // importamos datos desde el mainactivity
        val bundle = intent.extras
        popupVehicle = bundle?.getString("popupVehicle", "") ?: ""
        darkStatusBar = bundle?.getBoolean("darkstatusbar", false) ?:false

        //set data into labels
        vehicleVariable.text = popupVehicle
        indicatiuLabel.setText(usernameGlobal)
        database.collection(popupVehicle).document("info").get().addOnSuccessListener {
            ultkmtextlabel.setText(it.get("Kilometres")as String?)
        }

        val seleccioTipus = resources.getStringArray(R.array.tipusManteniment)
        val adapter = ArrayAdapter(
            this,
            R.layout.list_item_tipusmanteniment,
            seleccioTipus
        )




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
        registrarDadesButton.setOnClickListener {
            onBackPressed()
        }

        //boton limpiar formulario
        tornarKMButton.setOnClickListener {
            kmsAnteriors_label.setText("")
            observacionsLabel.setText("")
        }

        //boton registrar - enviar datos a firebase
        registrarDadesButton.setOnClickListener {
            val datahora = LocalDateTime.now().toString()
            database.collection(popupVehicle+"_manteniment").document(datahora).set(
                hashMapOf(
                    "Responsable" to indicatiuLabel.text.toString(),
                    "Observacions" to observacionsLabel.text.toString()
                )
            )

         /*   database.collection(popupVehicle).document("info").set(
                hashMapOf(
                "Kilometres" to kmsFinals_label.text.toString()
            ), SetOptions.merge()
                        ) */
            onBackPressed()
        }


    }

    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 250 // milliseconds
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