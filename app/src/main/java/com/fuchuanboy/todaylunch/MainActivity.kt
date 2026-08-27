package com.fuchuanboy.todaylunch

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import java.util.Calendar

class MainActivity : Activity() {
    private val pork=listOf("红烧肉","青椒肉丝","木须肉","土豆烧肉","蒜薹炒肉","回锅肉","香菇滑肉","小炒肉")
    private val beef=listOf("番茄炖牛腩","青椒牛肉","黑椒牛柳","土豆烧牛肉")
    private val lamb=listOf("孜然羊肉","葱爆羊肉","红焖羊肉","羊肉炖萝卜")
    private val duck=listOf("啤酒鸭","姜爆鸭","土豆烧鸭","香酥鸭")
    private val chicken=listOf("宫保鸡丁","香菇炖鸡","青椒鸡片","香煎鸡腿")
    private val fishShrimp=listOf("清蒸鱼","红烧鱼","酸菜鱼","虾仁炒蛋","蒜蓉虾")
    private val beans=listOf("麻婆豆腐","家常豆腐","香干炒肉","腐竹木耳","葱烧豆腐")
    private val seasonal=mapOf(1 to listOf("蒜蓉菠菜","香菇上海青","清炒白菜"),2 to listOf("香菇上海青","蒜蓉生菜","清炒菜心"),3 to listOf("香菇青菜","蒜蓉菜心","清炒菠菜"),4 to listOf("蒜蓉油麦菜","香菇青菜","清炒西兰花"),5 to listOf("蚝油生菜","蒜蓉空心菜","清炒西兰花"),6 to listOf("蒜蓉空心菜","清炒苋菜","蚝油生菜"),7 to listOf("蒜蓉空心菜","清炒丝瓜","蚝油生菜"),8 to listOf("蒜蓉空心菜","清炒苋菜","香菇青菜"),9 to listOf("香菇青菜","蒜蓉上海青","清炒西兰花"),10 to listOf("香菇上海青","蒜蓉菠菜","清炒白菜"),11 to listOf("蒜蓉菠菜","香菇上海青","清炒白菜"),12 to listOf("蒜蓉菠菜","香菇上海青","清炒白菜"))

    override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);show()}
    private fun show(){
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(28,48,28,24);gravity=Gravity.CENTER_HORIZONTAL;setBackgroundColor(Color.rgb(250,247,240))}
        val title=TextView(this).apply{text="今天中午吃什么？";textSize=30f;setTextColor(Color.rgb(60,45,35));gravity=Gravity.CENTER}
        val sub=TextView(this).apply{text="随机四菜一汤 · 家常口味 · 荤素搭配";textSize=16f;gravity=Gravity.CENTER;setPadding(0,8,0,10)}
        val image=ImageView(this).apply{setImageResource(R.drawable.ic_food);adjustViewBounds=true}
        val card=TextView(this).apply{textSize=19f;setTextColor(Color.DKGRAY);gravity=Gravity.CENTER;setPadding(10,8,10,8)}
        val button=Button(this).apply{text="🎲 换一套";textSize=18f;setOnClickListener{card.text=menu()}}
        root.addView(title);root.addView(sub);root.addView(image,LinearLayout.LayoutParams(-1,150));root.addView(card,LinearLayout.LayoutParams(-1,0,1f));root.addView(button,LinearLayout.LayoutParams(-1,64));setContentView(root);card.text=menu()
    }
    private fun menu():String{
        val veg=seasonal[Calendar.getInstance().get(Calendar.MONTH)+1]!!.random()
        val main=listOf("猪肉：${pork.random()}","牛肉：${beef.random()}","羊肉：${lamb.random()}","鸭肉：${duck.random()}","鸡肉：${chicken.random()}","鱼虾：${fishShrimp.random()}").shuffled().take(2)
        val side=beans.random(); val veg2=veg; val veg3=seasonal[Calendar.getInstance().get(Calendar.MONTH)+1]!!.filter{it!=veg}.random()
        return "🍚 今日午餐\n\n🥩 ${main[0]}\n🥩 ${main[1]}\n🫘 豆制品：$side\n🥬 时令蔬菜：$veg2\n🥬 清炒时蔬：$veg3\n\n🍲 汤：${listOf("番茄蛋花汤","紫菜蛋花汤","冬瓜排骨汤","菌菇汤","萝卜汤","青菜豆腐汤").random()}\n\n四菜一汤 · 猪肉优先 · 牛羊肉、鸭肉、鸡肉、鱼虾随机轮换\n主食：米饭"
    }
}
