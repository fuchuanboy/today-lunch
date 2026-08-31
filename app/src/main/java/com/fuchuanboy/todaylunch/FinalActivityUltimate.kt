package com.fuchuanboy.todaylunch

import android.app.Activity
import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread
import kotlin.random.Random

class FinalActivityUltimate : Activity() {
    data class Recipe(val name:String,val type:String,val veg:List<String>,val ingredients:String,val steps:String)
    data class Meal(val meat:Recipe,val bean:Recipe,val green:Recipe,val vitamin:Recipe,val soup:Recipe,val score:Int)

    private val meats=listOf(
        Recipe("鱼香肉丝","动物蛋白",listOf("木耳","胡萝卜","青椒"),"猪里脊、木耳、胡萝卜、青椒、泡椒、葱姜蒜、醋、生抽、糖、淀粉","里脊切丝，加少量盐、料酒、淀粉腌制；调鱼香汁；肉丝滑炒盛出；炒香葱姜蒜和泡椒，下木耳胡萝卜青椒，回锅肉丝，倒入鱼香汁大火收汁。"),
        Recipe("洋葱肥牛","动物蛋白",listOf("洋葱","彩椒"),"肥牛片、洋葱、彩椒、生抽、蚝油、黑胡椒","洋葱和彩椒切块；肥牛快速焯水；炒香洋葱，加入肥牛和彩椒；调入生抽蚝油和黑胡椒，大火翻匀。"),
        Recipe("照烧鸡腿","动物蛋白",listOf("鸡腿","芝麻"),"去骨鸡腿、酱油、味醂或蜂蜜、姜、芝麻","鸡腿擦干；鸡皮朝下煎至金黄；翻面煎熟；加入酱油和甜味汁小火收浓，切块撒芝麻。"),
        Recipe("孜然羊肉","动物蛋白",listOf("洋葱","香菜"),"羊肉片、洋葱、香菜、孜然、辣椒面、生抽","羊肉擦干；锅烧热后大火快炒；加入洋葱；撒孜然和辣椒面，调生抽，最后放香菜。"),
        Recipe("番茄炖牛腩","动物蛋白",listOf("番茄","洋葱"),"牛腩、番茄、洋葱、姜、八角、生抽","牛腩焯水；番茄炒出汁；加入牛腩、洋葱和香料，加水炖至软烂；最后调味。"),
        Recipe("清蒸鲈鱼","动物蛋白",listOf("葱","姜"),"鲈鱼、葱、姜、蒸鱼豉油、食用油","鱼处理干净；铺姜片，水开后蒸8至10分钟；倒掉蒸汁，铺葱丝，淋蒸鱼豉油和热油。"),
        Recipe("蒜蓉虾","动物蛋白",listOf("粉丝","小米椒"),"鲜虾、粉丝、大蒜、小米椒、生抽、蚝油","粉丝泡软铺盘；虾开背去虾线；蒜末小火炒香铺在虾上；水开后蒸8分钟，淋生抽和热油。"),
        Recipe("咖喱鸡肉饭","动物蛋白",listOf("土豆","胡萝卜","洋葱"),"鸡腿肉、土豆、胡萝卜、洋葱、咖喱块、米饭","鸡肉和蔬菜切块；炒香洋葱和鸡肉；加入土豆胡萝卜和水煮软；关小火融化咖喱块，浇在米饭上。"),
        Recipe("红烧排骨","动物蛋白",listOf("排骨"),"猪排骨、冰糖、葱姜、八角、生抽","排骨焯水；冰糖炒糖色；加入排骨和香料翻炒；加热水小火炖至软烂。"),
        Recipe("清炒虾仁","动物蛋白",listOf("黄瓜"),"虾仁、黄瓜、葱、盐、食用油","虾仁腌制；黄瓜切丁；虾仁滑炒变色；加入黄瓜快速翻炒调味。"),
        Recipe("宫保鸡丁","动物蛋白",listOf("黄瓜","花生"),"鸡胸肉、黄瓜、花生、干辣椒、花椒、生抽、醋、糖","鸡肉切丁腌制；调宫保汁；鸡丁滑炒；爆香辣椒花椒，加入黄瓜和鸡丁，倒汁收浓，撒花生。"),
        Recipe("青椒牛柳","动物蛋白",listOf("青椒"),"牛里脊、青椒、生抽、蚝油、淀粉","牛肉切条腌制；青椒切条；牛柳大火滑炒；加入青椒和调味汁快速翻匀。"),
        Recipe("可乐鸡翅","动物蛋白",listOf("鸡翅"),"鸡翅、可乐、生抽、姜","鸡翅焯水或煎香；加入可乐和生抽；小火煮至入味；大火收汁。"),
        Recipe("清炖羊肉","动物蛋白",listOf("白萝卜"),"羊肉、白萝卜、姜、葱、香菜、盐","羊肉焯水；加姜和水炖至八成熟；加入白萝卜炖软；调盐撒香菜。"),
        Recipe("红烧鱼块","动物蛋白",listOf("青蒜"),"草鱼块、青蒜、姜、蒜、生抽、醋","鱼块擦干煎黄；加入姜蒜、生抽和水；小火烧入味；淋醋，撒青蒜。")
    )
    private val beans=listOf(
        Recipe("家常豆腐","豆制品",listOf("木耳","青椒"),"老豆腐、木耳、青椒、葱姜蒜、生抽、蚝油","豆腐煎至两面金黄；木耳泡发；爆香葱姜蒜，下木耳青椒；放豆腐和调味料，加少量水焖入味。"),
        Recipe("麻婆豆腐","豆制品",listOf("青蒜","香菇"),"嫩豆腐、牛肉末、豆瓣酱、青蒜、香菇、花椒","豆腐焯水；炒香牛肉末和豆瓣酱；加入香菇和水；放豆腐小火煨入味，撒花椒和青蒜。"),
        Recipe("香煎豆腐","豆制品",listOf("芝麻"),"北豆腐、生抽、芝麻、葱花、蒜","豆腐切厚片擦干；少油煎至两面金黄；加生抽和少量水焖1分钟；撒葱花芝麻。"),
        Recipe("芹菜香干","豆制品",listOf("芹菜","红椒"),"香干、芹菜、红椒、蒜、生抽、盐","香干切条；芹菜切段；香干煎香；加入芹菜红椒快速翻炒调味。"),
        Recipe("番茄豆腐","豆制品",listOf("番茄"),"嫩豆腐、番茄、葱、盐、生抽","番茄切块炒出汁；加入豆腐和少量水炖5分钟；调味撒葱。"),
        Recipe("木耳炒豆皮","豆制品",listOf("木耳","彩椒"),"豆腐皮、木耳、彩椒、蒜、生抽","豆腐皮切条焯水；木耳泡发；蒜爆香后加入豆腐皮和木耳；最后放彩椒翻炒。"),
        Recipe("豆腐蒸蛋","豆制品",listOf("香菇"),"嫩豆腐、鸡蛋、香菇、青豆、生抽","豆腐铺碗；鸡蛋加温水打散；加入香菇青豆；水开后蒸8至10分钟。"),
        Recipe("毛豆烧豆腐","豆制品",listOf("毛豆","胡萝卜"),"豆腐、毛豆、胡萝卜、蒜、生抽","毛豆焯熟；豆腐煎黄；加入胡萝卜和毛豆翻炒；加少量水焖入味。"),
        Recipe("香菇豆腐煲","豆制品",listOf("香菇","胡萝卜"),"豆腐、香菇、胡萝卜、葱、蚝油","豆腐煎黄；香菇胡萝卜切片；加水和蚝油煲煮入味。"),
        Recipe("青椒豆腐干","豆制品",listOf("青椒"),"豆腐干、青椒、蒜、生抽、盐","豆腐干切片；青椒切块；蒜爆香，加入豆腐干和青椒，大火翻炒调味。")
    )
    private val greens=listOf(
        Recipe("蒜蓉上海青","绿叶青菜",listOf("上海青"),"上海青、大蒜、盐、油","上海青洗净沥干；蒜末爆香；大火下青菜快速翻炒；调盐立即出锅。"),
        Recipe("蒜蓉菠菜","绿叶青菜",listOf("菠菜"),"菠菜、大蒜、盐、油","菠菜洗净；蒜末爆香；加入菠菜大火翻炒至断生；调盐出锅。"),
        Recipe("蒜蓉油麦菜","绿叶青菜",listOf("油麦菜"),"油麦菜、大蒜、蚝油、盐","油麦菜洗净切段；蒜末爆香；大火翻炒；加入蚝油和少量盐。"),
        Recipe("清炒小白菜","绿叶青菜",listOf("小白菜"),"小白菜、蒜、盐、油","小白菜洗净切段；蒜片爆香；大火炒至断生；调盐翻匀。"),
        Recipe("蒜蓉空心菜","绿叶青菜",listOf("空心菜"),"空心菜、大蒜、盐、油","空心菜洗净切段；蒜末爆香；大火快速翻炒；加盐立即出锅。"),
        Recipe("蚝油生菜","绿叶青菜",listOf("生菜"),"生菜、蒜、蚝油、生抽","生菜洗净；沸水快速焯熟；蒜末炒香，加入蚝油生抽；淋在生菜上。"),
        Recipe("香菇炒芥蓝","绿叶青菜",listOf("芥蓝","香菇"),"芥蓝、鲜香菇、蒜、蚝油","芥蓝切段；香菇切片；蒜爆香；先炒香菇，再加入芥蓝快速翻炒。"),
        Recipe("蒜香苋菜","绿叶青菜",listOf("苋菜"),"苋菜、大蒜、盐、油","苋菜洗净；蒜末爆香；大火翻炒至变软；加盐出锅。"),
        Recipe("清炒芥菜","绿叶青菜",listOf("芥菜"),"芥菜、蒜、盐、油","芥菜洗净切段；蒜片爆香；大火翻炒至断生；调盐。"),
        Recipe("白灼菜心","绿叶青菜",listOf("菜心"),"菜心、生抽、蒜、食用油","菜心洗净；沸水加少量油焯熟；装盘淋生抽，浇热蒜油。")
    )
    private val vitamins=listOf(
        Recipe("番茄炒蛋","维生素C",listOf("番茄"),"番茄、鸡蛋、葱、盐、油","鸡蛋炒熟盛出；番茄炒出汁；加入鸡蛋翻匀；调盐撒葱。"),
        Recipe("彩椒炒玉米","维生素C",listOf("彩椒","玉米"),"彩椒、甜玉米、胡萝卜、盐、油","彩椒胡萝卜切丁；玉米焯熟；热锅翻炒全部食材；调盐。"),
        Recipe("蒜香西兰花","维生素C",listOf("西兰花"),"西兰花、胡萝卜、大蒜、盐、生抽","西兰花和胡萝卜焯水；蒜末爆香；加入蔬菜快速翻炒调味。"),
        Recipe("凉拌紫甘蓝","花青素",listOf("紫甘蓝","胡萝卜"),"紫甘蓝、胡萝卜、醋、生抽、芝麻油","紫甘蓝和胡萝卜切丝；加少量盐抓匀；调入醋、生抽和芝麻油拌匀。"),
        Recipe("香醋藕片","维生素C",listOf("莲藕"),"莲藕、香醋、蒜、葱、盐","莲藕切片焯水；蒜末爆香；加入藕片快速翻炒；淋香醋调味。"),
        Recipe("西红柿西葫芦","维生素C",listOf("西红柿","西葫芦"),"西红柿、西葫芦、蒜、盐","西红柿切块；西葫芦切片；先炒西葫芦，再加入西红柿炒出汁。"),
        Recipe("醋溜土豆丝","维生素C",listOf("土豆","青椒"),"土豆、青椒、醋、蒜、盐","土豆切丝洗去淀粉；蒜爆香；大火炒土豆丝和青椒；沿锅边淋醋。"),
        Recipe("清炒彩椒","维生素C",listOf("彩椒"),"红黄彩椒、蒜、盐、油","彩椒切条；蒜片爆香；大火翻炒至断生；调盐出锅。"),
        Recipe("凉拌黄瓜","维生素K",listOf("黄瓜"),"黄瓜、蒜、醋、生抽、香油","黄瓜拍碎切段；蒜末加入醋、生抽和香油；拌匀即可。"),
        Recipe("胡萝卜炒木耳","β胡萝卜素",listOf("胡萝卜","木耳"),"胡萝卜、木耳、蒜、生抽、盐","胡萝卜切片；木耳泡发；蒜爆香，加入胡萝卜和木耳炒熟调味。")
    )
    private val soups=listOf(
        Recipe("冬瓜排骨汤","汤",listOf("冬瓜"),"排骨、冬瓜、姜、葱、盐","排骨焯水；加姜和水小火炖40分钟；加入冬瓜再炖15分钟；调盐撒葱。"),
        Recipe("番茄蛋花汤","汤",listOf("番茄"),"番茄、鸡蛋、葱、盐、香油","番茄炒软加水煮开；鸡蛋打散淋入；调盐，关火滴香油撒葱。"),
        Recipe("紫菜豆腐汤","汤",listOf("紫菜","豆腐"),"紫菜、嫩豆腐、鸡蛋、葱、盐","豆腐切块入沸水；放紫菜；淋蛋液；调盐撒葱。"),
        Recipe("菌菇鸡汤","汤",listOf("菌菇"),"鸡腿、香菇、金针菇、姜、盐","鸡肉焯水；加姜和水炖30分钟；加入菌菇再炖10分钟；调盐。"),
        Recipe("萝卜牛肉汤","汤",listOf("白萝卜"),"牛腩、白萝卜、姜、葱、盐","牛腩焯水；加姜炖至半熟；加入萝卜继续炖软；调盐撒葱。"),
        Recipe("海带豆腐汤","汤",listOf("海带"),"海带、豆腐、姜、葱、盐","海带泡发切条；豆腐切块；加水煮开后炖10分钟；调盐。"),
        Recipe("玉米排骨汤","汤",listOf("玉米"),"排骨、玉米、胡萝卜、姜、盐","排骨焯水；玉米胡萝卜切块；加姜炖40分钟；调盐。"),
        Recipe("莲藕排骨汤","汤",listOf("莲藕"),"排骨、莲藕、姜、盐","排骨焯水；莲藕切块；加姜炖至排骨软烂；调盐。")
    )

    private fun meal():Meal{
        repeat(300){
            val a=meats.random();val b=beans.random();val g=greens.random();val v=vitamins.random();val s=soups.random()
            val all=(a.veg+b.veg+g.veg+v.veg).filter{it!="葱"&&it!="姜"}
            if(all.distinct().size==all.size)return Meal(a,b,g,v,s,95)
        }
        return Meal(meats.random(),beans.random(),greens.random(),vitamins.random(),soups.random(),90)
    }

    override fun onCreate(state:Bundle?){super.onCreate(state);render(meal())}

    private fun render(m:Meal){
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(18,18,18,18);setBackgroundColor(Color.rgb(247,245,240))}
        val title=TextView(this).apply{text="今天中午吃什么";textSize=27f;gravity=Gravity.CENTER;setTextColor(Color.rgb(55,45,35));setTypeface(null,1)}
        root.addView(title,LinearLayout.LayoutParams(-1,64))
        val score=TextView(this).apply{text="营养搭配  ${m.score} 分   ·   四菜一汤";textSize=16f;gravity=Gravity.CENTER;setTextColor(Color.rgb(45,125,70));setPadding(0,4,0,12)}
        root.addView(score)
        val scroll=ScrollView(this);val list=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL}
        listOf(m.meat,m.bean,m.green,m.vitamin,m.soup).forEachIndexed{i,r->
            val card=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(15,12,15,12);setBackgroundColor(Color.WHITE)}
            val image=ImageView(this).apply{scaleType=ImageView.ScaleType.CENTER_CROP;setBackgroundColor(Color.rgb(235,232,225))}
            card.addView(image,LinearLayout.LayoutParams(-1,170))
            val h=TextView(this).apply{text="${i+1}. ${r.name}";textSize=21f;setTypeface(null,1);setTextColor(Color.rgb(55,45,35));setPadding(0,10,0,4)};card.addView(h)
            card.addView(TextView(this).apply{text=r.type+"   ·   搭配："+r.veg.joinToString("、");textSize=14f;setTextColor(Color.rgb(55,110,75))})
            card.addView(TextView(this).apply{text="食材：${r.ingredients}";textSize=14f;setTextColor(Color.DKGRAY);setPadding(0,6,0,4)})
            val detail=Button(this).apply{text="查看详细做法";setOnClickListener{AlertDialog.Builder(this@FinalActivityUltimate).setTitle(r.name).setMessage("【食材】\n${r.ingredients}\n\n【做法】\n${r.steps}").setPositiveButton("知道了",null).show()}};card.addView(detail)
            val lp=LinearLayout.LayoutParams(-1,-2);lp.setMargins(0,0,0,12);list.addView(card,lp)
            loadImage(r.name,image)
        }
        scroll.addView(list);root.addView(scroll,LinearLayout.LayoutParams(-1,0,1f))
        root.addView(Button(this).apply{text="换一桌午餐";textSize=18f;setOnClickListener{render(meal())}},LinearLayout.LayoutParams(-1,58))
        setContentView(root)
    }

    private fun loadImage(name:String,view:ImageView){
        thread{
            try{
                val q=URLEncoder.encode(name,"UTF-8")
                val api=URL("https://commons.wikimedia.org/w/api.php?action=query&generator=search&gsrsearch=$q&gsrnamespace=6&gsrlimit=1&prop=imageinfo&iiprop=url&iiurlwidth=900&format=json")
                val c=api.openConnection() as HttpURLConnection;c.connectTimeout=7000;c.readTimeout=7000
                val obj=JSONObject(c.inputStream.bufferedReader().readText());val pages=obj.optJSONObject("query")?.optJSONObject("pages")
                var url="";pages?.keys()?.forEach{key->url=pages.getJSONObject(key).optJSONArray("imageinfo")?.optJSONObject(0)?.optString("thumburl","") ?: ""}
                if(url.isNotEmpty()){
                    val bmp=BitmapFactory.decodeStream(URL(url).openStream());runOnUiThread{if(bmp!=null)view.setImageBitmap(bmp)}
                }
                c.disconnect()
            }catch(_:Exception){}
        }
    }
}
