package com.fuchuanboy.todaylunch

import android.app.Activity
import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread
import kotlin.random.Random

class MainActivity : Activity() {
    data class Recipe(val name:String,val type:String,val vegetables:List<String>,val score:Int,val ingredients:String,val steps:String)
    private lateinit var listBox:LinearLayout
    private lateinit var scoreView:TextView
    private lateinit var heroImage:ImageView
    private lateinit var heroTitle:TextView
    private val random=Random(System.currentTimeMillis())
    private val recipes=listOf(
        Recipe("红烧肉","动物蛋白",listOf("五花肉","葱"),92,"五花肉、冰糖、生抽、老抽、姜、葱、料酒","五花肉切块焯水；锅中炒糖色，下肉块翻匀；加入姜葱、生抽、老抽和热水，小火炖40分钟，最后收汁。"),
        Recipe("青椒肉丝","动物蛋白",listOf("猪肉","青椒"),90,"猪里脊、青椒、生抽、淀粉、姜、蒜","里脊切丝，加生抽和淀粉腌10分钟；热锅滑油炒肉丝；加入青椒和蒜片，大火翻炒至断生。"),
        Recipe("洋葱肥牛","动物蛋白",listOf("牛肉","洋葱"),91,"肥牛、洋葱、生抽、蚝油、黑胡椒","肥牛焯至变色捞出；洋葱炒香；加入肥牛、生抽、蚝油和黑胡椒，大火翻炒均匀。"),
        Recipe("黑椒牛柳","动物蛋白",listOf("牛肉","彩椒"),93,"牛里脊、彩椒、黑胡椒、生抽、淀粉","牛肉切条加淀粉腌制；大火快炒至变色；加入彩椒和黑椒汁翻炒均匀。"),
        Recipe("照烧鸡腿","动物蛋白",listOf("鸡腿","西兰花"),94,"鸡腿、料酒、生抽、蜂蜜、西兰花","鸡腿去骨腌制；煎至两面金黄；加入生抽、料酒和蜂蜜收汁，配焯熟西兰花。"),
        Recipe("清蒸鲈鱼","动物蛋白",listOf("鲈鱼","葱"),95,"鲈鱼、姜、葱、蒸鱼豉油","鲈鱼处理干净放姜片；水开后蒸8—10分钟；倒掉汤汁，铺葱丝，淋热油和蒸鱼豉油。"),
        Recipe("蒜蓉虾","动物蛋白",listOf("虾","蒜"),94,"鲜虾、蒜、生抽、蚝油","鲜虾开背；蒜末炒香后铺在虾上；上锅蒸8分钟，淋少量生抽即可。"),
        Recipe("麻婆豆腐","植物蛋白",listOf("豆腐","青蒜"),93,"嫩豆腐、豆瓣酱、花椒、青蒜、肉末","豆腐焯水；肉末炒香加入豆瓣酱；加水和豆腐烧5分钟，勾薄芡，撒花椒和青蒜。"),
        Recipe("家常豆腐","植物蛋白",listOf("豆腐","木耳","青椒"),92,"老豆腐、木耳、青椒、豆瓣酱、生抽","豆腐煎至两面金黄；加入木耳和青椒；加豆瓣酱、生抽和少量水烧入味。"),
        Recipe("香菇烧豆腐","植物蛋白",listOf("豆腐","香菇"),91,"豆腐、鲜香菇、生抽、蚝油、葱","豆腐煎香；香菇炒软；加入豆腐、生抽、蚝油和少量水，小火烧8分钟。"),
        Recipe("蒜蓉空心菜","绿叶蔬菜",listOf("空心菜","蒜"),91,"空心菜、蒜、盐","空心菜洗净沥干；蒜末爆香；加入空心菜大火快速翻炒至断生，加盐出锅。"),
        Recipe("蒜蓉苋菜","绿叶蔬菜",listOf("苋菜","蒜"),90,"苋菜、蒜、盐","苋菜洗净沥干；蒜末爆香；加入苋菜快速翻炒至变软，加盐调味。"),
        Recipe("香菇炒上海青","绿叶蔬菜",listOf("上海青","香菇"),90,"上海青、鲜香菇、蒜、盐","上海青洗净沥干；香菇切片炒香；加入上海青大火快速翻炒，加盐出锅。"),
        Recipe("蚝油生菜","绿叶蔬菜",listOf("生菜","蒜"),89,"生菜、蒜、蚝油、生抽","生菜焯水30秒捞出；蒜末炒香，加入蚝油、生抽和少量水煮开，淋在生菜上。"),
        Recipe("丝瓜炒蛋","蔬菜",listOf("丝瓜","鸡蛋"),92,"丝瓜、鸡蛋、蒜、盐","鸡蛋炒熟盛出；丝瓜去皮切块炒至断生；倒回鸡蛋，加盐快速翻匀。"),
        Recipe("番茄炒蛋","蔬菜",listOf("番茄","鸡蛋"),93,"番茄、鸡蛋、葱、盐","鸡蛋炒熟盛出；番茄炒出汁；倒回鸡蛋，加盐和葱翻匀。"),
        Recipe("清炒豇豆","蔬菜",listOf("豇豆","蒜"),88,"豇豆、蒜、盐","豇豆切段；热油爆香蒜末；加入豇豆炒熟，可加少量水焖至断生。"),
        Recipe("清炒冬瓜","蔬菜",listOf("冬瓜","葱"),89,"冬瓜、葱、蒜、盐","冬瓜去皮切片；蒜末爆香后下冬瓜翻炒；加少量水焖熟，撒葱花。"),
        Recipe("冬瓜虾皮汤","汤",listOf("冬瓜","虾皮"),91,"冬瓜、虾皮、葱、盐","冬瓜切片；锅中加水煮沸，放冬瓜和虾皮；煮10分钟，加盐和葱。"),
        Recipe("番茄蛋花汤","汤",listOf("番茄","鸡蛋"),92,"番茄、鸡蛋、葱、盐","番茄炒出汁加水煮开；蛋液缓慢倒入形成蛋花；加盐和葱即可。"),
        Recipe("紫菜蛋花汤","汤",listOf("紫菜","鸡蛋"),90,"紫菜、鸡蛋、葱、盐","水煮开后放紫菜；蛋液缓慢倒入并搅成蛋花；加盐和葱。"),
        Recipe("菌菇豆腐汤","汤",listOf("菌菇","豆腐"),93,"菌菇、豆腐、葱、盐","菌菇洗净下锅煮开；加入豆腐煮5分钟；加盐和葱调味。")
    )
    override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);buildUi();makeMeal()}
    private fun dp(v:Int)= (v*resources.displayMetrics.density+0.5f).toInt()
    private fun wrap()=LinearLayout.LayoutParams.WRAP_CONTENT
    private fun rounded(color:Int,radius:Int)=GradientDrawable().apply{setColor(color);cornerRadius=radius.toFloat()}

    private fun buildUi(){
        val scroll=ScrollView(this).apply{isFillViewport=true}
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(18),dp(18),dp(18),dp(30));setBackgroundColor(Color.rgb(246,250,247))}
        root.addView(TextView(this).apply{text="今天中午吃什么";textSize=28f;typeface=Typeface.DEFAULT_BOLD;setTextColor(Color.rgb(25,105,55))},LinearLayout.LayoutParams(-1,wrap()))
        root.addView(TextView(this).apply{text="河南家常 · 营养四菜一汤";textSize=15f;setTextColor(Color.rgb(90,100,94));setPadding(0,dp(3),0,dp(12))},LinearLayout.LayoutParams(-1,wrap()))
        val heroBox=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;background=rounded(Color.WHITE,dp(14));elevation=dp(2).toFloat()}
        heroImage=ImageView(this).apply{scaleType=ImageView.ScaleType.CENTER_CROP;setBackgroundColor(Color.rgb(225,235,227));contentDescription="今日菜品图片"}
        heroBox.addView(heroImage,LinearLayout.LayoutParams(-1,dp(190)))
        heroTitle=TextView(this).apply{textSize=20f;typeface=Typeface.DEFAULT_BOLD;setTextColor(Color.rgb(40,55,46));setPadding(dp(14),dp(12),dp(14),dp(14))}
        heroBox.addView(heroTitle,LinearLayout.LayoutParams(-1,wrap()))
        root.addView(heroBox,LinearLayout.LayoutParams(-1,wrap()).apply{setMargins(0,0,0,dp(14))})
        root.addView(Button(this).apply{text="随机一桌";textSize=19f;isAllCaps=false;setTextColor(Color.WHITE);background=rounded(Color.rgb(35,125,70),dp(12));setOnClickListener{makeMeal()}},LinearLayout.LayoutParams(-1,dp(54)).apply{setMargins(0,0,0,dp(10))})
        scoreView=TextView(this).apply{textSize=18f;typeface=Typeface.DEFAULT_BOLD;gravity=Gravity.CENTER;setTextColor(Color.rgb(30,120,62));setPadding(0,dp(4),0,dp(14))}
        root.addView(scoreView,LinearLayout.LayoutParams(-1,wrap()))
        listBox=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL}
        root.addView(listBox,LinearLayout.LayoutParams(-1,wrap()))
        scroll.addView(root);setContentView(scroll)
    }

    private fun makeMeal(){
        val animal=recipes.filter{it.type=="动物蛋白"};val plant=recipes.filter{it.type=="植物蛋白"};val green=recipes.filter{it.type=="绿叶蔬菜"};val veg=recipes.filter{it.type=="蔬菜"};val soup=recipes.filter{it.type=="汤"}
        val chosen=mutableListOf<Recipe>()
        chosen+=animal.random(random);chosen+=plant.random(random);chosen+=green.random(random)
        val used=chosen.flatMap{it.vegetables}.toSet();chosen+=veg.filter{r->r.vegetables.none{it in used}}.randomOrNull(random)?:veg.random(random)
        val usedAll=chosen.flatMap{it.vegetables}.toSet();chosen+=soup.filter{r->r.vegetables.none{it in usedAll}}.randomOrNull(random)?:soup.random(random)
        listBox.removeAllViews();chosen.forEachIndexed{index,r->addCard(r,index+1)}
        scoreView.text="营养搭配评分  ${chosen.map{it.score}.average().toInt()} / 100  ★★★★★";heroTitle.text="今日推荐：${chosen[0].name}";loadImage(heroImage,chosen[0].name)
    }

    private fun addCard(r:Recipe,number:Int){
        val card=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;gravity=Gravity.CENTER_VERTICAL;background=rounded(Color.WHITE,dp(14));elevation=dp(1).toFloat();setPadding(dp(10),dp(10),dp(10),dp(10));setOnClickListener{showDetail(r)}}
        val image=ImageView(this).apply{scaleType=ImageView.ScaleType.CENTER_CROP;setBackgroundColor(Color.rgb(232,240,234));contentDescription=r.name}
        card.addView(image,LinearLayout.LayoutParams(dp(88),dp(88)).apply{setMargins(0,0,dp(10),0)})
        val textBox=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL}
        textBox.addView(TextView(this).apply{text="$number  ${r.name}";textSize=18f;typeface=Typeface.DEFAULT_BOLD;setTextColor(Color.rgb(45,52,48));maxLines=1;ellipsize=android.text.TextUtils.TruncateAt.END},LinearLayout.LayoutParams(-1,wrap()))
        textBox.addView(TextView(this).apply{text="${r.type} · ${r.vegetables.joinToString("、")}";textSize=14f;setTextColor(Color.rgb(90,98,93));maxLines=2;ellipsize=android.text.TextUtils.TruncateAt.END;setPadding(0,dp(4),0,0)},LinearLayout.LayoutParams(-1,wrap()))
        textBox.addView(TextView(this).apply{text="营养评分 ${r.score}  ›";textSize=14f;setTextColor(Color.rgb(35,120,66));setPadding(0,dp(5),0,0)},LinearLayout.LayoutParams(-1,wrap()))
        card.addView(textBox,LinearLayout.LayoutParams(0,wrap(),1f));listBox.addView(card,LinearLayout.LayoutParams(-1,wrap()).apply{setMargins(0,0,0,dp(10))});loadImage(image,r.name)
    }

    private fun showDetail(r:Recipe){AlertDialog.Builder(this).setTitle(r.name).setMessage("食材：${r.ingredients}\n\n做法：${r.steps}").setPositiveButton("知道了",null).show()}

    private fun loadImage(view:ImageView,dish:String){
        thread{
            try{
                val q=URLEncoder.encode(dish,"UTF-8")
                val api=URL("https://commons.wikimedia.org/w/api.php?action=query&generator=search&gsrsearch=$q&gsrnamespace=6&gsrlimit=5&prop=imageinfo&iiprop=url&iiurlwidth=800&format=json")
                val conn=api.openConnection() as HttpURLConnection;conn.connectTimeout=7000;conn.readTimeout=7000;conn.requestMethod="GET"
                val json=conn.inputStream.bufferedReader().use{it.readText()};conn.disconnect()
                val urlText=Regex("\\\"thumburl\\\":\\\"([^\\\"]+)").find(json)?.groupValues?.get(1)?:Regex("\\\"url\\\":\\\"([^\\\"]+)").find(json)?.groupValues?.get(1)
                if(urlText.isNullOrBlank())return@thread
                val imageUrl=urlText.replace("\\/","/")
                val imgConn=URL(imageUrl).openConnection() as HttpURLConnection;imgConn.connectTimeout=7000;imgConn.readTimeout=7000
                val bitmap=imgConn.inputStream.use{BitmapFactory.decodeStream(it)};imgConn.disconnect()
                if(bitmap!=null)runOnUiThread{view.setImageBitmap(bitmap)}
            }catch(_:Exception){}
        }
    }
}
