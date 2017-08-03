package com.example.leet.myminlist

import android.accessibilityservice.GestureDescription
import android.app.Service
import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.support.v7.app.AlertDialog
import android.util.Size
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.Toast
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
    var time:Long=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_time_counter)
        var get=intent
        var note=get.getStringExtra("note")
        show.text=note
//        count.start(64300)
//        var time:Long=0
//        while(time<1000){
//            count.updateShow(time)
//            time++
//        }
        var i:Long=0
        ten.setOnClickListener {
            time=1000*60*10
            count.stop()
            toast("点击开始，进行10分钟的专注")
           start.text="开　始"
        }
        i=0
        twenty.setOnClickListener {
            time=20*60*1000
            count.stop()
            toast("点击开始，进行20分钟的专注")
            start.text="开　始"
        }
        i=0
        thirty.setOnClickListener {
            time=30*60*1000
            count.stop()
            toast("点击开始，进行30分钟的专注")
            start.text="开　始"
        }
        i=0
        start.setOnClickListener {
            ten.visibility=View.GONE
            twenty.visibility=View.GONE
            thirty.visibility=View.GONE
            focus.visibility=View.VISIBLE
            if (start.text.toString().equals("开　始")) {
                count.start(time)
                while (i < 1000) {
                    count.updateShow(i)
                    i++
                }
                start.text="暂　停"
            }else if(start.text.toString().equals("暂　停")){
                count.pause()
                start.text="继　续"
            }else{
                start.text="暂　停"
                count.start(count.remainTime)
            }
        }
        count.setOnCountdownEndListener {
            var viber:Vibrator= getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
            viber.vibrate(3000)
            toast("恭喜你已经成功专注了这么久！")
            focus.visibility=View.GONE
            ten.visibility=View.VISIBLE
            twenty.visibility=View.VISIBLE
            thirty.visibility=View.VISIBLE
        }
        back.setOnClickListener {
            finish()
            var intent= Intent(this,MainActivity::class.java)
            overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_top)
            startActivity(intent)
        }

    }
    //弹出对话框
    fun dialog(){
        var builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("确认退出吗？")
        builder.setTitle("提示")
        builder.setPositiveButton("确认", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
        })
        builder.setNegativeButton("取消", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
        })
        builder.create().show()
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
//                    var builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
//                    builder.setTitle("确认退出吗？")
//                    builder.setTitle("提示")
//                    builder.setPositiveButton("确认", DialogInterface.OnClickListener { dialogInterface, i ->
//                        dialogInterface.dismiss()
//                        finish()
//                        var intent= Intent(this,MainActivity::class.java)
//                        overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_top)
//                        startActivity(intent)
//                    })
//                    builder.setNegativeButton("取消", DialogInterface.OnClickListener { dialogInterface, i ->
//                        dialogInterface.dismiss()
//                    })
//                    builder.create().show()
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

    fun toast(str:String){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        finish()
        var intent= Intent(this,MainActivity::class.java)
        overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_top)
        startActivity(intent)
    }
}
