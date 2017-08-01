package com.example.leet.myminlist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Size
import android.view.MotionEvent
import android.view.VelocityTracker
import kotlinx.android.synthetic.main.activity_time_counter.*

class TimeCounterActivity : AppCompatActivity() {
    val YSPEED_MIN=1000
    val XDISTANCE_MIN=50
    val YDISTANCE_MIN=100
    var xDown:Float=0.1f
    var yDown:Float=0.1f
    var xMove:Float=0.1f
    var yMove:Float=0.1f
    var mVelocityTracker: VelocityTracker = VelocityTracker.obtain()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_time_counter)
        var get=intent
        var note=get.getStringExtra("note")
        show.text=note
        back.setOnClickListener {
            finish()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)

        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        createVelocityTracker(ev)
        when(ev?.action){
            MotionEvent.ACTION_DOWN->{xDown= ev.rawX
                yDown=ev.rawY}
            MotionEvent.ACTION_MOVE->{
                xMove=ev.rawX
                yMove=ev.rawY
                var distanceX:Int= (xMove-xDown).toInt()
                var distanceY:Int=(yMove-yDown).toInt()
                var ySpeed:Int=getScrollVelocity()
                if(distanceX > XDISTANCE_MIN &&(distanceY<YDISTANCE_MIN&&distanceY>-YDISTANCE_MIN)&& ySpeed < YSPEED_MIN) {
                    finish()
                    var intent= Intent(this,MainActivity::class.java)
                    overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_top)
                    startActivity(intent)

                }
            }
            MotionEvent.ACTION_UP->{
                recycleVelocity()

            }


        }
        return super.dispatchTouchEvent(ev)
    }

    fun  createVelocityTracker(ev: MotionEvent?){
        if (mVelocityTracker==null){
            mVelocityTracker= VelocityTracker.obtain()
        }
        mVelocityTracker.addMovement(ev)
    }
    fun recycleVelocity(){
        mVelocityTracker.recycle()
        mVelocityTracker= VelocityTracker.obtain()
    }
    fun getScrollVelocity():Int{
        mVelocityTracker.computeCurrentVelocity(1000)
        var velocity:Int=mVelocityTracker.getYVelocity().toInt()
        return Math.abs(velocity)
    }
}
