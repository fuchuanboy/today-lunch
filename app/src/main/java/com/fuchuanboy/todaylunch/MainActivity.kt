package com.fuchuanboy.todaylunch

import android.app.Activity
import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.widget.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread
import kotlin.random.Random

class MainActivity : Activity() {
    data class Recipe(val id:Int,val name:String,val type:String,val vegetables:List<String>,val score:Int,val ingredients:String,val steps:String,val imageQuery:String)
    private lateinit var listBox:LinearLayout
    private lateinit var scoreView:TextView
    private lateinit var heroImage:ImageView
    private lateinit var heroTitle:TextView
    private val random=Random(System.currentTimeMillis())
    private var recipes:List<Recipe> = emptyList()

    override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);recipes=loadRecipes();buildUi();makeMeal()}
    private fun dp(v:Int)= (v*resources.displayMetrics.density+0.5f).toInt()
    private fun wrap()=LinearLayout.LayoutParams.WRAP_CONTENT
    private fun rounded(color:Int,radius:Int)=GradientDrawable().apply{setColor(color);cornerRadius=radius.toFloat()}

    private fun loadRecipes():List<Recipe>{
        val out=mutableListOf<Recipe>()
        assets.open("recipes.csv").bufferedReader(Charsets.UTF_8).useLines{lines->
            lines.drop(1).forEach{line->
                val p=parseCsvLine(line)
                if(p.size>=8) out+=Recipe(p[0].toIntOrNull()?:out.size+1,p[1],p[2],p[3].split("、").filter{it.isNotBlank()},p[4].toIntOrNull()?:88,p[5],p[6],p[7])
            }
        }
        return out.distinctBy{it.name}
    }

    private fun parseCsvLine(line:String):List<String>{
        val result=mutableListOf<String>();val sb=StringBuilder();var quoted=false;var i=0
        while(i<line.length){val c=line[i];if(c=='"'){if(quoted&&i+1<line.length&&line[i+1]=='"'){sb.append('"');i++}else quoted=!quoted}else if(c==','&&!quoted){result+=sb.toString();sb.setLength(0)}else sb.append(c);i++};result+=sb.toString();return result
    }

    private fun buildUi(){
        val scroll=ScrollView(this).apply{isFillViewport=true}
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(18),dp(18),dp(18),dp(30));setBackgroundColor(Color.rgb(246,250,247))}
        root.addView(TextView(this).apply{text="今天中午吃什么";textSize=28f;typeface=Typeface.DEFAULT_BOLD;setTextColor(Color.rgb(25,105,55))},LinearLayout.LayoutParams(-1,wrap()))
        root.addView(TextView(this).apply{text="河南家常 · 2000+菜品库 · 营养四菜一汤";textSize=14f;setTextColor(Color.rgb(90,100,94));setPadding(0,dp(3),0,dp(12))},LinearLayout.LayoutParams(-1,wrap()))
        val heroBox=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;background=rounded(Color.WHITE,dp(14));elevation=dp(2).toFloat()}
        heroImage=ImageView(this).apply{scaleType=ImageView.ScaleType.CENTER_CROP;setBackgroundColor(Color.rgb(225,235,227));contentDescription="今日菜品图片"}
        heroBox.addView(heroImage,LinearLayout.LayoutParams(-1,dp(190)))
        heroTitle=TextView(this).apply{textSize=20f;typeface=Typeface.DEFAULT_BOLD;setTextColor(Color.rgb(40,55,46));setPadding(dp(14),dp(12),dp(14),dp(14))}
        heroBox.addView(heroTitle,LinearLayout.LayoutParams(-1,wrap()))
        root.addView(heroBox,LinearLayout.LayoutParams(-1,wrap()).apply{setMargins(0,0,0,dp(14))})
        root.addView(Button(this).apply{text="随机一桌";textSize=19f;isAllCaps=false;setTextColor(Color.WHITE);background=rounded(Color.rgb(35,125,70),dp(12));setOnClickListener{makeMeal()}},LinearLayout.LayoutParams(-1,dp(54)).apply{setMargins(0,0,0,dp(10))})
        scoreView=TextView(this).apply{textSize=18f;typeface=Typeface.DEFAULT_BOLD;gravity=Gravity.CENTER;setTextColor(Color.rgb(30,120,62));setPadding(0,dp(4),0,dp(14))}
        root.addView(scoreView,LinearLayout.LayoutParams(-1,wrap()))
        listBox=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL};root.addView(listBox,LinearLayout.LayoutParams(-1,wrap()));scroll.addView(root);setContentView(scroll)
    }

    private fun makeMeal(){
        if(recipes.isEmpty())return
        val animal=recipes.filter{it.type=="动物蛋白"};val plant=recipes.filter{it.type=="植物蛋白"};val green=recipes.filter{it.type=="绿叶蔬菜"};val veg=recipes.filter{it.type=="蔬菜"};val soup=recipes.filter{it.type=="汤"}
        if(animal.isEmpty()||plant.isEmpty()||green.isEmpty()||veg.isEmpty()||soup.isEmpty())return
        val chosen=mutableListOf<Recipe>();chosen+=animal.random(random)
        chosen+=plant.filter{it.vegetables.none{v->v in chosen.flatMap{r->r.vegetables}}}.randomOrNull(random)?:plant.random(random)
        chosen+=green.filter{it.vegetables.none{v->v in chosen.flatMap{r->r.vegetables}}}.randomOrNull(random)?:green.random(random)
        chosen+=veg.filter{it.vegetables.none{v->v in chosen.flatMap{r->r.vegetables}}}.randomOrNull(random)?:veg.random(random)
        chosen+=soup.filter{it.vegetables.none{v->v in chosen.flatMap{r->r.vegetables}}}.randomOrNull(random)?:soup.random(random)
        listBox.removeAllViews();chosen.forEachIndexed{index,r->addCard(r,index+1)}
        scoreView.text="营养搭配评分  ${chosen.map{it.score}.average().toInt()} / 100  ★★★★★";heroTitle.text="今日推荐：${chosen[0].name}";loadImage(heroImage,chosen[0].imageQuery)
    }

    private fun addCard(r:Recipe,number:Int){
        val card=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;gravity=Gravity.CENTER_VERTICAL;background=rounded(Color.WHITE,dp(14));elevation=dp(1).toFloat();setPadding(dp(10),dp(10),dp(10),dp(10));setOnClickListener{showDetail(r)}}
        val image=ImageView(this).apply{scaleType=ImageView.ScaleType.CENTER_CROP;setBackgroundColor(Color.rgb(232,240,234));contentDescription=r.name}
        card.addView(image,LinearLayout.LayoutParams(dp(88),dp(88)).apply{setMargins(0,0,dp(10),0)})
        val textBox=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL}
        textBox.addView(TextView(this).apply{text="$number  ${r.name}";textSize=18f;typeface=Typeface.DEFAULT_BOLD;setTextColor(Color.rgb(45,52,48));maxLines=1;ellipsize=TextUtils.TruncateAt.END},LinearLayout.LayoutParams(-1,wrap()))
        textBox.addView(TextView(this).apply{text="${r.type} · ${r.vegetables.joinToString("、")}";textSize=13f;setTextColor(Color.rgb(95,105,99));maxLines=2;ellipsize=TextUtils.TruncateAt.END;setPadding(0,dp(4),0,0)},LinearLayout.LayoutParams(-1,wrap()))
        textBox.addView(TextView(this).apply{text="营养评分 ${r.score} · 点击看做法";textSize=13f;setTextColor(Color.rgb(45,125,70));maxLines=1;ellipsize=TextUtils.TruncateAt.END;setPadding(0,dp(4),0,0)},LinearLayout.LayoutParams(-1,wrap()))
        card.addView(textBox,LinearLayout.LayoutParams(0,wrap(),1f));listBox.addView(card,LinearLayout.LayoutParams(-1,wrap()).apply{setMargins(0,0,0,dp(10))});loadImage(image,r.imageQuery)
    }

    private fun showDetail(r:Recipe){AlertDialog.Builder(this).setTitle(r.name).setMessage("【分类】${r.type}\n【食材】${r.ingredients}\n\n【做法】${r.steps}").setPositiveButton("知道了",null).show()}

    private fun loadImage(view:ImageView,query:String){
        view.setTag(query);thread{try{
            val q=URLEncoder.encode(query,"UTF-8");val api="https://commons.wikimedia.org/w/api.php?action=query&generator=search&gsrsearch=$q&gsrnamespace=6&gsrlimit=8&prop=imageinfo&iiprop=url&iiurlwidth=800&format=json&origin=*"
            val conn=(URL(api).openConnection() as HttpURLConnection).apply{connectTimeout=7000;readTimeout=10000;requestMethod="GET";setRequestProperty("User-Agent","TodayLunchApp/1.0")}
            val text=conn.inputStream.bufferedReader().use{it.readText()};conn.disconnect();val patterns=listOf(Regex("\"thumburl\"\\s*:\\s*\"(https?:\\\\/\\\\/[^\"\\\\]+)\""),Regex("\"url\"\\s*:\\s*\"(https?:\\\\/\\\\/[^\"\\\\]+)\""));var imageUrl:String?=null
            for(p in patterns){imageUrl=p.find(text)?.groupValues?.getOrNull(1);if(!imageUrl.isNullOrBlank())break};if(imageUrl.isNullOrBlank())return@thread;imageUrl=imageUrl!!.replace("\\/","/")
            val imgConn=(URL(imageUrl).openConnection() as HttpURLConnection).apply{connectTimeout=7000;readTimeout=10000;setRequestProperty("User-Agent","TodayLunchApp/1.0")};val bitmap=imgConn.inputStream.use{BitmapFactory.decodeStream(it)};imgConn.disconnect();if(bitmap!=null)runOnUiThread{if(view.tag==query)view.setImageBitmap(bitmap)}
        }catch(_:Exception){}}
    }
}
