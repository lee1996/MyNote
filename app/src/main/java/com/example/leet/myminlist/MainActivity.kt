package com.example.leet.myminlist

import android.app.Activity
import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.*
import android.graphics.Color.GRAY
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.GestureDetector.OnGestureListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item.*
import kotlinx.android.synthetic.main.item.view.*

class MainActivity : AppCompatActivity(), MyItemClickListener {
    lateinit var datas:ArrayList<String>
    lateinit var gesture:GestureDetector
    lateinit var myHelper:MyDBHelper
    lateinit var db:SQLiteDatabase
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        recycle.layoutManager=LinearLayoutManager(this)
        recycle.setHasFixedSize(true)
        datas= arrayListOf()
        //数据库操作
        myHelper= MyDBHelper(this)
        db=myHelper.writableDatabase
        var cursor:Cursor=db.query("note",null,null,null,null,null,null)
        if(cursor.moveToFirst()) {
            var i=0
            while (i<cursor.count) {
                var note = cursor.getString(cursor.getColumnIndex("note"))
                datas.add(note)
                i++
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        myHelper.close()
        var i:Int=0
        while(i<datas.size){
            println("这是第"+i+"条　　　"+datas[i])
            i++
        }
        var adapter=MyAdapter(datas)
        recycle.adapter=adapter
        adapter.setOnClickListener(this)

        recycle.setOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

        })
        add.setOnEditorActionListener { textView, i, keyEvent ->
            if(i==EditorInfo.IME_ACTION_SEND||i==EditorInfo.IME_ACTION_DONE||(keyEvent!=null&&KeyEvent.KEYCODE_ENTER==keyEvent.keyCode&&KeyEvent.ACTION_DOWN==keyEvent.action)){
                var te=textView.text.toString()
                db=myHelper.readableDatabase
                var cv: ContentValues = ContentValues()
                cv.put("note", te)
                db.insert("note", null, cv)
                textView.text = ""
                textView.isFocusableInTouchMode = false
                textView.isFocusable = false
                var imm: InputMethodManager = add.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(add.windowToken, 0)
                recycle.adapter.notifyDataSetChanged()
                cursor.close()
                db.close()
                myHelper.close()
                finish()
                var intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out)

            }
            false
        }
//        add.setOnFocusChangeListener { view, b ->
//            if(!b){
//                var im:InputMethodManager= getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                im.hideSoftInputFromWindow(view.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
//            }
//        }
        add.setOnClickListener {
            add.isFocusable=true
            add.isFocusableInTouchMode=true
            add.requestFocus()
            var imm:InputMethodManager= add.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(add,0)
        }




    }
    override fun OnItemClickListener(v: View?,flag:Int) {
//        add?.visibility=View.GONE
        v?.isFocusable=true
        v?.isFocusableInTouchMode=true
        v?.requestFocus()
        var imm:InputMethodManager= add.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v?.windowToken,0)
       if(v is EasySwipeMenuLayout){
           var del=v?.findViewById<TextView>(R.id.zone)
           var delText=del.text.toString()
           var delTextString= arrayOf(delText)
           myHelper= MyDBHelper(this)
           db=myHelper.readableDatabase
           println("要删除的是"+delText)
           db.delete("note","note=?",delTextString)
           db.beginTransaction()
           db.endTransaction()
           db.close()
           myHelper.close()
           v.visibility=View.GONE
          // Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show()
       }else if(v is LinearLayout){
           var text=v.findViewById<TextView>(R.id.zone).text.toString()
           var intent=Intent(this,TimeCounterActivity::class.java)
           intent.putExtra("note",text)
           startActivity(intent)
       }

    }
    fun toast(str:String){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        var intent=Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

}
