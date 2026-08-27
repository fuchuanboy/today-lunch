package com.fuchuanboy.todaylunch

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import kotlin.random.Random

class MainActivity : Activity() {
    private data class Dish(val name:String, val type:String, val emoji:String)
    private val meats = listOf("红烧肉","青椒肉丝","木须肉","土豆烧肉","蒜薹炒肉","回锅肉","香菇滑肉","小炒肉")
    private val proteins = listOf("番茄炒蛋","清蒸鱼","宫保鸡丁","香煎鸡腿","虾仁炒蛋")
    private val vegetables = listOf("蒜蓉上海青","香菇青菜","清炒西兰花","蚝油生菜","蒜蓉菠菜","醋溜白菜")
    private val beans = listOf("麻婆豆腐","家常豆腐","香干炒肉","腐竹木耳","葱烧豆腐")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        show()
    }

    private fun show() {
        val root = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(36,60,36,36); gravity=Gravity.CENTER_HORIZONTAL; setBackgroundColor(Color.rgb(250,247,240)) }
        val title=TextView(this).apply { text="今天中午吃什么？"; textSize=30f; setTextColor(Color.rgb(60,45,35)); gravity=Gravity.CENTER; setPadding(0,0,0,20) }
        val subtitle=TextView(this).apply { text="家常口味 · 荤素搭配 · 营养均衡"; textSize=16f; gravity=Gravity.CENTER; setPadding(0,0,0,28) }
        val card=TextView(this).apply { textSize=21f; setTextColor(Color.DKGRAY); gravity=Gravity.CENTER; setPadding(20,35,20,35) }
        val button=Button(this).apply { text="🎲  换一套午餐"; textSize=18f; setOnClickListener { card.text=menu() } }
        root.addView(title); root.addView(subtitle); root.addView(card, LinearLayout.LayoutParams(-1,0,1f)); root.addView(button, LinearLayout.LayoutParams(-1,60)); setContentView(root); card.text=menu()
    }
    private fun menu():String {
        val a=meats.random(); val b=proteins.random(); val c=vegetables.random(); val d=beans.random()
        return "🍚 今日午餐\n\n🥩 $a\n🍗 $b\n🥬 $c\n🫘 $d\n\n建议：一荤一蛋白 + 一青菜 + 一豆制品\n主食：米饭"
    }
}
