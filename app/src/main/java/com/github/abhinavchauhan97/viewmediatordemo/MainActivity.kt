package com.github.abhinavchauhan97.viewmediatordemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.doOnAttach
import com.github.abhinavchauhan97.viewmediator.ClicksMediator
import com.github.abhinavchauhan97.viewmediator.ViewMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mediator = findViewById<ViewMediator>(R.id.mediator) // get reference to view mediator
        for(i in 0..5){
            val textview = layoutInflater.inflate(R.layout.textview,findViewById(R.id.container),false)
            findViewById<LinearLayout>(R.id.container).addView(textview)
            mediator.addView(textview)
            if(i == 3){
                mediator.addDefaultSelectedView(textview)
            }
        }
        mediator.maxSelection = 5
//        mediator.addView(findViewById(R.id.textview1))
//        mediator.addView(findViewById(R.id.textview2))
//        mediator.addView(findViewById(R.id.textview3))
//        mediator.addView(findViewById(R.id.textview4))
//        mediator.addDefaultSelectedView(findViewById(R.id.textview3))
//        mediator.addDefaultSelectedView(findViewById(R.id.textview1))

        mediator.clicksMediator = object : ClicksMediator{  // and setup the callback
            override fun doWithOtherViews(otherViews: List<View>) { // here you get all the views except the clicked/selected view
                otherViews.forEach {
                    val textView = it as TextView
                    textView.setTextColor(Color.RED)
                }
            }

            override fun doWithClickedView(clickedView: View) { // here you get the clicked/selected
                    val textView = clickedView as TextView
                    textView.setTextColor(Color.GREEN)
            }

            override fun onLimitReached() {
                // when the limit is reached this callback is called , in this example we have app:vm_canSelect="2" so when user clicks third view this callback will be called may be you want to show a message saying you cannot select more that two options , you do it here
                Toast.makeText(this@MainActivity,"can not select more than 2 options",Toast.LENGTH_LONG).show()
            }

            override fun onSelectedViewReClick(reClickedView: View) {
                val textView = reClickedView as TextView
                textView.setTextColor(Color.RED)
                // this callback is called when already selected view is clicked again to make it unselected
                // here you can make it look like it a unselected view should look like
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val mediator = findViewById<ViewMediator>(R.id.mediator)
        mediator.doOnAttach {
            val textview =
                layoutInflater.inflate(R.layout.textview, findViewById(R.id.container), false)
            findViewById<LinearLayout>(R.id.container).addView(textview)
            mediator.addView(textview)
            mediator.addDefaultSelectedView(textview)
        }
    }
}