package com.fuchuanboy.todaylunch

import android.app.Activity
import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread
import kotlin.random.Random

class Ultimate2026Activity : Activity() {
    private val rnd = Random(System.currentTimeMillis())
    private lateinit var list: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); showMeal() }

    private fun makeMeal(): List<CatalogDish> {
        val animal = ExpandedDishDatabase.all.shuffled(rnd)
        val bean = ExpandedDishDatabase.plantProtein.shuffled(rnd).firstOrNull()
        val green = ExpandedDishDatabase.greenVegetables.shuffled(rnd).firstOrNull()
        val used = mutableSetOf<String>()
        val result = mutableListOf<CatalogDish>()
        animal.firstOrNull { it.vegetables.none { v -> v in used } }?.let { result += it; used += it.vegetables }
        bean?.takeIf { it.vegetables.none { v -> v in used } }?.let { result += it; used += it.vegetables }
        green?.takeIf { it.vegetables.none { v -> v in used } }?.let { result += it; used += it.vegetables }
        val vitamin = animal.firstOrNull { it.vegetables.any { v -> v in setOf("番茄","青椒","彩椒","西兰花","胡萝卜") } && it.vegetables.none { v -> v in used } }
        vitamin?.let { result += it; used += it.vegetables }
        for (d in animal) if (result.size >= 4) break else if (d.vegetables.none { it in used }) { result += d; used += d.vegetables }
        return result.take(4) + ExpandedDishDatabase.soups.random(rnd)
    }

    private fun showMeal() {
        val meal = makeMeal()
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(18), dp(18), dp(22))
            setBackgroundColor(Color.rgb(247,251,246))
        }
        val header = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        header.addView(TextView(this).apply { text="河南省 · 今日午餐"; textSize=15f; setTextColor(Color.DKGRAY) })
        header.addView(TextView(this).apply { text="今天中午吃什么？"; textSize=29f; setTypeface(null,1); setTextColor(Color.rgb(24,91,48)); setPadding(0,dp(5),0,dp(3)) })
        header.addView(TextView(this).apply { text="四菜一汤 · 真实菜品 · 营养结构化搭配"; textSize=15f; setTextColor(Color.GRAY); setPadding(0,0,0,dp(12)) })
        root.addView(header)
        val score = meal.map { it.score }.average().toInt()
        root.addView(TextView(this).apply { text="营养评分  $score / 100   ★★★★★"; textSize=17f; gravity=Gravity.CENTER; setTypeface(null,1); setTextColor(Color.rgb(35,125,65)); setPadding(0,0,0,dp(10)) })
        val scroll = ScrollView(this)
        list = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL }
        meal.forEachIndexed { i,d -> addCard(i+1,d) }
        scroll.addView(list)
        root.addView(scroll, LinearLayout.LayoutParams(-1,0,1f))
        root.addView(Button(this).apply { text="🎲  换一桌"; textSize=18f; setOnClickListener { showMeal() } }, LinearLayout.LayoutParams(-1,dp(58)).apply { topMargin=dp(10) })
        setContentView(root)
    }

    private fun addCard(n:Int,d:CatalogDish) {
        val card=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(dp(12),dp(10),dp(12),dp(10)); setBackgroundColor(Color.WHITE); elevation=dp(2).toFloat() }
        val image=ImageView(this).apply { scaleType=ImageView.ScaleType.CENTER_CROP; setBackgroundColor(Color.rgb(229,237,229)); contentDescription=d.name }
        card.addView(image,LinearLayout.LayoutParams(-1,dp(150)))
        card.addView(TextView(this).apply { text="$n. ${d.name}"; textSize=20f; setTypeface(null,1); setTextColor(Color.rgb(45,55,45)); setPadding(0,dp(8),0,dp(2)) })
        card.addView(TextView(this).apply { text="${d.protein} · ${d.vegetables.joinToString("、")}"; textSize=14f; setTextColor(Color.rgb(48,120,70)) })
        card.addView(TextView(this).apply { text="评分 ${d.score} · ${d.ingredients}"; textSize=13f; setTextColor(Color.DKGRAY); setPadding(0,dp(5),0,dp(5)) })
        card.addView(Button(this).apply { text="查看怎么做"; setOnClickListener { detail(d) } })
        list.addView(card,LinearLayout.LayoutParams(-1,-2).apply { bottomMargin=dp(10) })
        loadCommons(d.imageQuery,image)
    }

    private fun detail(d:CatalogDish) {
        val s=StringBuilder("食材\n${d.ingredients}\n\n做法\n")
        d.steps.forEachIndexed { i,x -> s.append("${i+1}. $x\n") }
        s.append("\n营养评分：${d.score}/100")
        AlertDialog.Builder(this).setTitle(d.name).setMessage(s.toString()).setPositiveButton("知道了",null).show()
    }

    private fun loadCommons(name:String,view:ImageView) {
        thread {
            try {
                val q=URLEncoder.encode(name,"UTF-8")
                val u=URL("https://commons.wikimedia.org/w/api.php?action=query&generator=search&gsrsearch=$q&gsrnamespace=6&gsrlimit=1&prop=imageinfo&iiprop=url&iiurlwidth=600&format=json")
                val c=u.openConnection() as HttpURLConnection; c.connectTimeout=6000; c.readTimeout=6000
                val p=JSONObject(c.inputStream.bufferedReader().use { it.readText() }).optJSONObject("query")?.optJSONObject("pages")
                val keys=p?.keys(); if(keys!=null && keys.hasNext()) {
                    val info=p.getJSONObject(keys.next()).optJSONArray("imageinfo")?.optJSONObject(0)
                    val thumb=info?.optString("thumburl","").orEmpty()
                    if(thumb.isNotBlank()) { val b=BitmapFactory.decodeStream(URL(thumb).openStream()); runOnUiThread { if(b!=null) view.setImageBitmap(b) } }
                }
                c.disconnect()
            } catch(_:Exception) { }
        }
    }

    private fun dp(v:Int):Int=(v*resources.displayMetrics.density).toInt()
}
