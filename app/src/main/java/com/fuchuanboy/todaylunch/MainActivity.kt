package com.fuchuanboy.todaylunch

import android.app.Activity
import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread
import kotlin.random.Random

class MainActivity : Activity() {
    data class Recipe(val name:String,val protein:String,val vegetables:List<String>,val score:Int,val image:String,val ingredients:String,val steps:String)
    private lateinit var listBox:LinearLayout
    private lateinit var scoreView:TextView
    private lateinit var hero:ImageView
    private val random=Random(System.currentTimeMillis())

    private val recipes=listOf(
        Recipe("红烧肉","猪肉",listOf("五花肉","葱"),92,"红烧肉","五花肉、冰糖、生抽、老抽、姜、葱、料酒","五花肉切块焯水；锅中炒糖色，下肉块翻匀；加入姜葱、生抽老抽和热水，小火炖40分钟，收汁。"),
        Recipe("青椒肉丝","猪肉",listOf("青椒","猪肉"),90,"青椒肉丝","里脊肉、青椒、生抽、淀粉、姜蒜","肉丝加生抽和淀粉腌10分钟；热锅滑油炒肉丝；加入青椒和蒜片快速翻炒至断生。"),
        Recipe("洋葱肥牛","牛肉",listOf("洋葱","牛肉"),91,"洋葱肥牛","肥牛、洋葱、生抽、蚝油、黑胡椒","肥牛焯至变色捞出；洋葱炒香；加入肥牛、生抽、蚝油和黑胡椒，大火翻炒。"),
        Recipe("黑椒牛柳","牛肉",listOf("彩椒","牛肉"),93,"黑椒牛柳","牛里脊、彩椒、黑胡椒、生抽、淀粉","牛肉切条加淀粉腌制；大火快炒至变色；加入彩椒和黑椒汁翻炒均匀。"),
        Recipe("照烧鸡腿","鸡肉",listOf("西兰花","鸡腿"),94,"照烧鸡腿","鸡腿、料酒、生抽、蜂蜜、西兰花","鸡腿去骨腌制；煎至两面金黄；加入生抽、料酒和蜂蜜收汁，配焯熟西兰花。"),
        Recipe("可乐鸡翅","鸡肉",listOf("鸡翅","葱"),89,"可乐鸡翅","鸡翅、可乐、生抽、姜、葱","鸡翅划口焯水；煎至金黄；加入可乐、生抽和姜葱，中火煮至浓稠。"),
        Recipe("清蒸鲈鱼","鱼",listOf("鲈鱼","葱"),95,"清蒸鲈鱼","鲈鱼、姜、葱、蒸鱼豉油","鲈鱼处理干净，放姜片；水开后蒸8-10分钟；倒掉汤汁，铺葱丝，淋热油和蒸鱼豉油。"),
        Recipe("蒜蓉虾","虾",listOf("虾","蒜"),94,"蒜蓉虾","鲜虾、蒜、生抽、蚝油","鲜虾开背；蒜末炒香；铺在虾上蒸8分钟，淋少量生抽。"),
        Recipe("麻婆豆腐","豆制品",listOf("豆腐","青蒜"),93,"麻婆豆腐","嫩豆腐、肉末、豆瓣酱、花椒、青蒜","豆腐焯水；肉末炒香加入豆瓣酱；加水和豆腐烧5分钟，勾薄芡，撒花椒和青蒜。"),
        Recipe("家常豆腐","豆制品",listOf("豆腐","木耳","青椒"),92,"家常豆腐","老豆腐、木耳、青椒、豆瓣酱、生抽","豆腐煎至两面金黄；加入木耳青椒；加豆瓣酱和少量水烧入味。"),
        Recipe("香菇烧豆腐","豆制品",listOf("豆腐","香菇"),91,"香菇烧豆腐","豆腐、香菇、生抽、蚝油、葱","豆腐煎香；香菇炒软；加豆腐、生抽蚝油和少量水，小火烧8分钟。"),
        Recipe("香菇炒上海青","绿叶蔬菜",listOf("上海青","香菇"),90,"香菇炒上海青","上海青、鲜香菇、蒜、盐","上海青洗净沥干；香菇切片炒香；加入上海青大火快速翻炒，加盐出锅。"),
        Recipe("蒜蓉空心菜","绿叶蔬菜",listOf("空心菜","蒜"),91,"蒜蓉空心菜","空心菜、蒜、盐","空心菜洗净沥干；蒜末爆香；加入空心菜大火快速翻炒至断生，加盐。"),
        Recipe("蒜蓉苋菜","绿叶蔬菜",listOf("苋菜","蒜"),90,"蒜蓉苋菜","苋菜、蒜、盐","苋菜洗净沥干；蒜末爆香；加入苋菜快速翻炒至变软，加盐调味。"),
        Recipe("蚝油生菜","绿叶蔬菜",listOf("生菜","蒜"),89,"蚝油生菜","生菜、蒜、蚝油、生抽","生菜焯水30秒捞出；蒜末炒香，加入蚝油生抽和少量水煮开，淋在生菜上。"),
        Recipe("丝瓜炒蛋","蔬菜",listOf("丝瓜","鸡蛋"),92,"丝瓜炒蛋","丝瓜、鸡蛋、蒜、盐","鸡蛋炒熟盛出；丝瓜去皮切块炒至断生；倒回鸡蛋，加盐快速翻匀。"),
        Recipe("番茄炒蛋","蔬菜",listOf("番茄","鸡蛋"),93,"番茄炒蛋","番茄、鸡蛋、葱、盐","鸡蛋炒熟盛出；番茄炒出汁；倒回鸡蛋，加盐和葱翻匀。"),
        Recipe("清炒豇豆","蔬菜",listOf("豇豆","蒜"),88,"清炒豇豆","豇豆、蒜、盐","豇豆切段；热油爆香蒜末；加入豇豆炒熟，加少量水焖至断生。"),
        Recipe("冬瓜虾皮汤","汤",listOf("冬瓜","虾皮"),91,"冬瓜虾皮汤","冬瓜、虾皮、葱、盐","冬瓜切片；锅中加水煮沸，放冬瓜和虾皮；煮10分钟，加盐和葱。"),
        Recipe("番茄蛋花汤","汤",listOf("番茄","鸡蛋"),92,"番茄蛋花汤","番茄、鸡蛋、葱、盐","番茄炒出汁加水煮开；淋入蛋液形成蛋花；加盐和葱即可。"),
        Recipe("紫菜蛋花汤","汤",listOf("紫菜","鸡蛋"),90,"紫菜蛋花汤","紫菜、鸡蛋、葱、盐","水煮开后放紫菜；蛋液缓慢倒入并搅成蛋花；加盐和葱。"),
        Recipe("菌菇豆腐汤","汤",listOf("菌菇","豆腐"),93,"菌菇豆腐汤","菌菇、豆腐、葱、盐","菌菇洗净下锅煮开；加入豆腐煮5分钟；加盐和葱调味。")
    )

    override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState); buildUi(); makeMeal()}
    private fun buildUi(){
        val scroll=ScrollView(this); val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(24,24,24,32);setBackgroundColor(Color.rgb(246,250,246))}
        val title=TextView(this).apply{text="今天中午吃什么";textSize=30f;typeface=android.graphics.Typeface.DEFAULT_BOLD;setTextColor(Color.rgb(25,110,55))}
        root.addView(title,LinearLayout.LayoutParams(-1,wrap()))
        val sub=TextView(this).apply{text="河南家常 · 营养四菜一汤";textSize=16f;setTextColor(Color.DKGRAY);setPadding(0,4,0,18)};root.addView(sub)
        hero=ImageView(this).apply{scaleType=ImageView.ScaleType.CENTER_CROP;setBackgroundColor(Color.LTGRAY)};root.addView(hero,LinearLayout.LayoutParams(-1,dp(190)).apply{setMargins(0,0,0,16)})
        val btn=Button(this).apply{text="🎲  随机一桌";textSize=19f;setOnClickListener{makeMeal()}};root.addView(btn,LinearLayout.LayoutParams(-1,dp(58)).apply{setMargins(0,0,0,12)})
        scoreView=TextView(this).apply{textSize=17f;gravity=Gravity.CENTER;typeface=android.graphics.Typeface.DEFAULT_BOLD;setTextColor(Color.rgb(30,120,60));setPadding(0,8,0,16)};root.addView(scoreView)
        listBox=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL};root.addView(listBox);scroll.addView(root);setContentView(scroll)
    }
    private fun makeMeal(){
        val animal=recipes.filter{it.protein in setOf("猪肉","牛肉","羊肉","鸡肉","鸭肉","鱼","虾")};val plant=recipes.filter{it.protein=="豆制品"};val green=recipes.filter{it.protein=="绿叶蔬菜"};val vitamin=recipes.filter{it.protein=="蔬菜"};
        val chosen=mutableListOf<Recipe>();chosen+=animal.random(random);chosen+=plant.random(random);chosen+=green.random(random);val used=chosen.flatMap{it.vegetables}.toSet();chosen+=vitamin.filter{r->r.vegetables.none{it in used}}.randomOrNull(random)?:vitamin.random(random);val soup=recipes.filter{it.protein=="汤"}.filter{it.vegetables.none{v->chosen.flatMap{it.vegetables}.contains(v)}}.randomOrNull(random)?:recipes.filter{it.protein=="汤"}.random(random);val meal=chosen+soup;listBox.removeAllViews();meal.forEachIndexed{index,r->addCard(r,index+1)};scoreView.text="营养搭配评分  ${meal.map{it.score}.average().toInt()} / 100 ★★★★★";loadImage(hero,meal.first().image)}
    private fun addCard(r:Recipe,number:Int){val box=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;setPadding(dp(14),dp(12),dp(14),dp(12));setBackgroundColor(Color.WHITE);setOnClickListener{showDetail(r)}};val label=TextView(this).apply{setText("$number  ${r.name}\n${r.protein} · ${r.vegetables.joinToString("、")}\n营养评分 ${r.score}");textSize=17f;setTextColor(Color.DKGRAY)};box.addView(label,LinearLayout.LayoutParams(0,wrap(),1f));val arrow=TextView(this).apply{text="›";textSize=30f;gravity=Gravity.CENTER_VERTICAL;setTextColor(Color.GRAY)};box.addView(arrow,LinearLayout.LayoutParams(dp(40),-1));listBox.addView(box,LinearLayout.LayoutParams(-1,wrap()).apply{setMargins(0,0,0,dp(10))})}
    private fun showDetail(r:Recipe){AlertDialog.Builder(this).setTitle(r.name).setMessage("食材：${r.ingredients}\n\n做法：${r.steps}").setPositiveButton("知道了",null).show()}
    private fun loadImage(view:ImageView,dish:String){thread{try{val q=URLEncoder.encode("$dish food", "UTF-8");val url=URL("https://commons.wikimedia.org/w/api.php?action=query&generator=search&gsrsearch=$q&gsrnamespace=6&gsrlimit=1&prop=imageinfo&iiprop=url&iiurlwidth=900&format=json");val json=url.readText();val thumb=Regex("\\\"thumburl\\\":\\\"([^\\\"]+)").find(json)?.groupValues?.get(1)?.replace("\\/","/");if(thumb!=null){val bmp=BitmapFactory.decodeStream(URL(thumb).openStream());runOnUiThread{view.setImageBitmap(bmp)}}}catch(_:Exception){}}}
    private fun dp(v:Int)= (v*resources.displayMetrics.density).toInt();private fun wrap()=LinearLayout.LayoutParams.WRAP_CONTENT
}
