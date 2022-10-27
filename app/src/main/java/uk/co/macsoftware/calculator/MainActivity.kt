package uk.co.macsoftware.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {



    private fun init(){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }


    fun onDigit(view: View){
        Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show()
    }



}