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
import java.net.URLEncoder
import java.net.URL
import kotlin.concurrent.thread
import kotlin.random.Random

class FinalActivity : Activity() {
    data class Recipe(val name:String,val group:String,val protein:String,val veg:List<String>,val ingredients:String,val steps:List<String>,val query:String)
    data class Meal(val animal:Recipe,val plant:Recipe,val leaf:Recipe,val vitamin:Recipe,val soup:Recipe,val score:Int)

    private val animal=listOf(
        Recipe("鱼香肉丝","animal","猪肉",listOf("木耳","胡萝卜","青椒"),"猪里脊、木耳、胡萝卜、青椒、葱姜蒜、泡椒、醋、生抽、糖、淀粉",listOf("里脊切丝，加生抽和淀粉腌10分钟。","木耳、胡萝卜、青椒切丝，调鱼香汁。","热锅滑炒肉丝盛出，爆香葱姜蒜和泡椒。","下蔬菜，回锅肉丝，倒鱼香汁，大火收汁。"),"鱼香肉丝 中国菜"),
        Recipe("红烧排骨","animal","猪肉",listOf("排骨","葱"),"猪肋排、葱姜、冰糖、生抽、老抽、料酒、八角",listOf("排骨冷水下锅焯水。","冰糖炒糖色，加入排骨翻匀。","加入葱姜、料酒和酱油，加热水没过排骨。","小火炖40分钟，大火收汁。"),"红烧排骨 中国菜"),
        Recipe("洋葱肥牛","animal","牛肉",listOf("洋葱","青椒"),"肥牛片、洋葱、青椒、生抽、蚝油、黑胡椒",listOf("洋葱青椒切块。","肥牛快速焯水。","炒香洋葱，加入肥牛和青椒。","加入生抽、蚝油、黑胡椒，大火翻匀。"),"洋葱肥牛 中国菜"),
        Recipe("土豆炖牛肉","animal","牛肉",listOf("土豆","胡萝卜"),"牛腩、土豆、胡萝卜、葱姜、八角、生抽",listOf("牛腩焯水切块。","葱姜香料炒香，加入牛肉。","加水和生抽炖至八成熟。","加入土豆胡萝卜炖至软烂。"),"土豆炖牛肉 中国菜"),
        Recipe("孜然羊肉","animal","羊肉",listOf("洋葱","香菜"),"羊肉片、洋葱、香菜、孜然、辣椒面、生抽",listOf("羊肉擦干水分。","大火炒羊肉至变色。","加入洋葱快速翻炒。","撒孜然辣椒面，关火放香菜。"),"孜然羊肉 中国菜"),
        Recipe("葱爆羊肉","animal","羊肉",listOf("大葱"),"羊肉片、大葱、生抽、料酒、白胡椒",listOf("羊肉用料酒白胡椒腌5分钟。","大葱切滚刀块。","大火炒羊肉至变色。","加入大葱和生抽快速翻炒。"),"葱爆羊肉 中国菜"),
        Recipe("照烧鸡腿","animal","鸡肉",listOf("鸡腿","芝麻"),"去骨鸡腿、照烧汁、芝麻、盐",listOf("鸡腿擦干，少量盐腌制。","鸡皮朝下煎至金黄，翻面煎熟。","倒入照烧汁小火收浓。","切块装盘，撒芝麻。"),"照烧鸡腿 中国菜"),
        Recipe("宫保鸡丁","animal","鸡肉",listOf("黄瓜","花生"),"鸡胸肉、黄瓜、花生、干辣椒、花椒、葱姜蒜",listOf("鸡肉切丁，加淀粉生抽腌制。","调宫保汁。","鸡丁滑炒盛出。","爆香辣椒花椒，下黄瓜鸡丁和宫保汁，最后放花生。"),"宫保鸡丁 中国菜"),
        Recipe("咖喱鸡肉饭","animal","鸡肉",listOf("土豆","胡萝卜","洋葱"),"鸡腿肉、土豆、胡萝卜、洋葱、咖喱块、米饭",listOf("鸡肉和蔬菜切块。","炒香洋葱和鸡肉。","加土豆胡萝卜和水煮软。","关小火融化咖喱块，浇饭食用。"),"咖喱鸡肉饭 中国菜"),
        Recipe("啤酒鸭","animal","鸭肉",listOf("青椒","土豆"),"鸭块、啤酒、青椒、土豆、姜蒜、豆瓣酱",listOf("鸭块焯水。","姜蒜豆瓣酱炒香，下鸭块。","倒啤酒炖至鸭肉软熟。","加入土豆炖透，最后放青椒。"),"啤酒鸭 中国菜"),
        Recipe("清蒸鲈鱼","animal","鱼",listOf("葱","姜"),"鲈鱼、葱、姜、蒸鱼豉油、食用油",listOf("鱼处理干净，两面划刀，放姜片。","水开上锅蒸8至10分钟。","倒掉蒸汁，铺葱丝。","淋蒸鱼豉油和热油。"),"清蒸鲈鱼 中国菜"),
        Recipe("西兰花炒虾仁","animal","虾",listOf("西兰花","胡萝卜"),"虾仁、西兰花、胡萝卜、蒜、生抽、淀粉",listOf("虾仁加盐和淀粉腌制。","西兰花胡萝卜焯水。","蒜末爆香，炒虾仁至变色。","加入蔬菜和生抽，大火翻匀。"),"西兰花炒虾仁 中国菜")
    )
    private val plant=listOf(
        Recipe("家常麻婆豆腐","plant","豆制品",listOf("豆腐"),"嫩豆腐、少量猪肉末、郫县豆瓣酱、花椒、葱蒜",listOf("豆腐切块焯水。","炒香肉末和豆瓣酱。","加水放豆腐，小火烧入味。","勾薄芡，撒花椒粉和葱花。"),"麻婆豆腐 中国菜"),
        Recipe("葱烧豆腐","plant","豆制品",listOf("大葱"),"老豆腐、大葱、生抽、老抽、糖、淀粉",listOf("豆腐切块煎至两面金黄。","大葱切段炒香。","加入豆腐和调味料，加少量水烧3分钟。","勾薄芡收汁。"),"葱烧豆腐 中国菜"),
        Recipe("香煎豆腐","plant","豆制品",listOf("葱","芝麻"),"老豆腐、生抽、芝麻、葱花、蒜",listOf("豆腐切厚片擦干。","平底锅少油煎至两面金黄。","加入生抽和少量水焖1分钟。","撒葱花芝麻出锅。"),"香煎豆腐 中国菜"),
        Recipe("木耳炒豆干","plant","豆制品",listOf("木耳","青椒"),"豆干、木耳、青椒、蒜、生抽、蚝油",listOf("豆干切片，木耳泡发。","蒜末爆香。","加入豆干木耳青椒翻炒。","加生抽蚝油炒匀。"),"木耳炒豆干 中国菜"),
        Recipe("芹菜香干","plant","豆制品",listOf("芹菜"),"香干、芹菜、蒜、生抽、盐",listOf("香干切条，芹菜切段。","香干煎香。","加入芹菜快速翻炒。","加生抽和盐炒至断生。"),"芹菜香干 中国菜"),
        Recipe("番茄豆腐","plant","豆制品",listOf("番茄"),"嫩豆腐、番茄、葱、盐、生抽",listOf("番茄切块，豆腐切块。","番茄炒出汁。","加入豆腐和少量水炖5分钟。","加盐生抽，撒葱花。"),"番茄豆腐 中国菜")
    )
    private val leaf=listOf(
        Recipe("蒜蓉上海青","leaf","蔬菜",listOf("上海青"),"上海青、大蒜、食用油、盐",listOf("上海青洗净沥干。","蒜末用热油爆香。","大火加入上海青快速翻炒。","加盐翻匀立即出锅。"),"蒜蓉上海青 中国菜"),
        Recipe("蒜蓉菠菜","leaf","蔬菜",listOf("菠菜"),"菠菜、大蒜、盐、食用油",listOf("菠菜洗净切段。","蒜末爆香。","加入菠菜大火翻炒至刚断生。","加盐快速翻匀。"),"蒜蓉菠菜 中国菜"),
        Recipe("蒜蓉油麦菜","leaf","蔬菜",listOf("油麦菜"),"油麦菜、大蒜、蚝油、盐",listOf("油麦菜洗净切段。","蒜末爆香。","加入油麦菜大火翻炒。","加蚝油和少量盐翻匀。"),"蒜蓉油麦菜 中国菜"),
        Recipe("蒜蓉空心菜","leaf","蔬菜",listOf("空心菜"),"空心菜、大蒜、盐、食用油",listOf("空心菜洗净切段。","蒜末爆香。","大火下空心菜快速翻炒。","加盐立即出锅。"),"蒜蓉空心菜 中国菜"),
        Recipe("清炒小白菜","leaf","蔬菜",listOf("小白菜"),"小白菜、蒜、盐、油",listOf("小白菜洗净切段。","蒜片爆香。","大火炒小白菜至断生。","加盐翻匀。"),"清炒小白菜 中国菜")
    )
    private val vitamin=listOf(
        Recipe("番茄炒蛋","vitamin","鸡蛋",listOf("番茄"),"番茄、鸡蛋、葱、盐、油",listOf("番茄切块，鸡蛋打散。","鸡蛋炒熟盛出。","番茄炒出汁。","回锅鸡蛋，加盐和葱花。"),"番茄炒蛋 中国菜"),
        Recipe("彩椒炒玉米","vitamin","蔬菜",listOf("彩椒","玉米"),"彩椒、甜玉米、蒜、盐、油",listOf("彩椒切丁。","玉米焯熟。","蒜末爆香，下彩椒和玉米。","加盐大火翻匀。"),"彩椒炒玉米 中国菜"),
        Recipe("蒜蓉西兰花","vitamin","蔬菜",listOf("西兰花"),"西兰花、大蒜、盐、蚝油",listOf("西兰花掰小朵焯水。","蒜末爆香。","加入西兰花大火翻炒。","加蚝油和盐翻匀。"),"蒜蓉西兰花 中国菜"),
        Recipe("胡萝卜炒木耳","vitamin","蔬菜",listOf("胡萝卜","木耳"),"胡萝卜、木耳、蒜、生抽、盐",listOf("胡萝卜切片，木耳泡发。","蒜末爆香。","加入胡萝卜和木耳翻炒至熟。","加生抽和盐调味。"),"胡萝卜炒木耳 中国菜"),
        Recipe("清炒南瓜","vitamin","蔬菜",listOf("南瓜"),"南瓜、蒜、盐、油",listOf("南瓜切薄片。","蒜片爆香。","加入南瓜翻炒，加少量水焖熟。","加盐收干水分。"),"清炒南瓜 中国菜")
    )
    private val soups=listOf(
        Recipe("紫菜蛋花汤","soup","鸡蛋",listOf("紫菜"),"紫菜、鸡蛋、葱、盐、香油",listOf("锅中加水烧开。","放紫菜煮1分钟。","鸡蛋打散慢慢淋入。","加盐和香油，撒葱花。"),"紫菜蛋花汤 中国菜"),
        Recipe("冬瓜排骨汤","soup","猪肉",listOf("冬瓜"),"排骨、冬瓜、姜、葱、盐",listOf("排骨焯水洗净。","加姜片炖40分钟。","加入冬瓜继续炖15分钟。","加盐和葱花。"),"冬瓜排骨汤 中国菜"),
        Recipe("番茄牛肉汤","soup","牛肉",listOf("番茄"),"牛肉、番茄、姜、葱、盐",listOf("牛肉切片腌好。","番茄炒出汁加水煮开。","下牛肉片煮至变色。","加盐和葱花。"),"番茄牛肉汤 中国菜"),
        Recipe("菌菇豆腐汤","soup","豆制品",listOf("菌菇","豆腐"),"豆腐、香菇、平菇、葱、盐",listOf("菌菇洗净切片，豆腐切块。","水烧开后先煮菌菇。","加入豆腐煮5分钟。","加盐和葱花。"),"菌菇豆腐汤 中国菜"),
        Recipe("玉米胡萝卜鸡汤","soup","鸡肉",listOf("玉米","胡萝卜"),"鸡腿、玉米、胡萝卜、姜、盐",listOf("鸡腿焯水。","加姜和水炖30分钟。","加入玉米胡萝卜。","继续炖20分钟，加盐。"),"玉米胡萝卜鸡汤 中国菜")
    )

    override fun onCreate(b:Bundle?){super.onCreate(b); showMeal()}
    private fun showMeal(){
        val meal=makeMeal(); val root=LinearLayout(this); root.orientation=LinearLayout.VERTICAL; root.setPadding(20,18,20,18); root.setBackgroundColor(Color.rgb(250,248,244))
        val title=TextView(this); title.text="今天中午吃什么"; title.textSize=27f; title.setTextColor(Color.rgb(45,45,45)); title.setTypeface(null,1); title.gravity=Gravity.CENTER
        root.addView(title,LinearLayout.LayoutParams(-1,70))
        val sub=TextView(this); sub.text="四菜一汤 · 荤素搭配 · 营养评分 ${meal.score}/100"; sub.textSize=15f; sub.gravity=Gravity.CENTER; root.addView(sub,LinearLayout.LayoutParams(-1,48))
        val scroll=ScrollView(this); val list=LinearLayout(this); list.orientation=LinearLayout.VERTICAL
        card(list,"① 动物蛋白 · ${meal.animal.name}",meal.animal,"动物蛋白")
        card(list,"② 植物蛋白 · ${meal.plant.name}",meal.plant,"豆制品")
        card(list,"③ 绿叶青菜 · ${meal.leaf.name}",meal.leaf,"绿叶菜")
        card(list,"④ 维生素轮换 · ${meal.vitamin.name}",meal.vitamin,"维生素")
        card(list,"⑤ 汤 · ${meal.soup.name}",meal.soup,"汤")
        scroll.addView(list); root.addView(scroll,LinearLayout.LayoutParams(-1,0,1f))
        val refresh=Button(this); refresh.text="换一桌"; refresh.setOnClickListener{showMeal()}; root.addView(refresh,LinearLayout.LayoutParams(-1,58)); setContentView(root)
    }
    private fun card(parent:LinearLayout,title:String,r:Recipe,tag:String){
        val box=LinearLayout(this); box.orientation=LinearLayout.VERTICAL; box.setPadding(0,8,0,14)
        val iv=ImageView(this); iv.scaleType=ImageView.ScaleType.CENTER_CROP; iv.setBackgroundColor(Color.LTGRAY); box.addView(iv,LinearLayout.LayoutParams(-1,220)); loadImage(iv,r.query)
        val t=TextView(this); t.text=title; t.textSize=19f; t.setTypeface(null,1); t.setPadding(4,10,4,4); t.maxLines=3; t.setTextColor(Color.rgb(35,35,35)); box.addView(t,LinearLayout.LayoutParams(-1,-2))
        val detail=TextView(this); detail.text="${tag}  ·  ${r.veg.joinToString("、")}"; detail.textSize=14f; detail.setTextColor(Color.DKGRAY); detail.setPadding(4,3,4,6); box.addView(detail,LinearLayout.LayoutParams(-1,-2))
        val btn=Button(this); btn.text="查看食材与做法"; btn.setOnClickListener{detail(r)}; box.addView(btn,LinearLayout.LayoutParams(-1,52)); parent.addView(box)
    }
    private fun detail(r:Recipe){
        val s=ScrollView(this); val t=TextView(this); t.setPadding(28,20,28,20); t.textSize=16f; t.text="${r.name}\n\n【食材】\n${r.ingredients}\n\n【做法】\n${r.steps.mapIndexed{i,x->"${i+1}. $x"}.joinToString("\n")}\n\n【营养定位】\n${r.group}"; s.addView(t); AlertDialog.Builder(this).setTitle(r.name).setView(s).setPositiveButton("知道了",null).show()
    }
    private fun makeMeal():Meal{
        val a=animal.random(); val p=plant.random(); val l=leaf.random(); val used=mutableSetOf<String>(); used.addAll(a.veg); used.addAll(p.veg); used.addAll(l.veg)
        val v=vitamin.shuffled().firstOrNull{it.veg.none(used::contains)} ?: vitamin.random(); used.addAll(v.veg)
        val soup=soups.shuffled().firstOrNull{it.veg.none(used::contains)} ?: soups.random()
        var score=90; if(a.protein=="猪肉")score+=4; if(a.protein!="猪肉")score+=2; if(v.name.contains("番茄")||v.name.contains("西兰花")||v.name.contains("彩椒"))score+=2; if(used.size>=4)score+=2
        return Meal(a,p,l,v,soup,score.coerceAtMost(100))
    }
    private fun loadImage(iv:ImageView,query:String){
        thread { try { val q=URLEncoder.encode(query,"UTF-8"); val api="https://commons.wikimedia.org/w/api.php?action=query&generator=search&gsrsearch=$q&gsrnamespace=6&gsrlimit=1&prop=imageinfo&iiprop=url&iiurlwidth=900&format=json"; val c=URL(api).openConnection() as HttpURLConnection; c.connectTimeout=8000; c.readTimeout=10000; val json=JSONObject(c.inputStream.bufferedReader().use{it.readText()}); val pages=json.getJSONObject("query").getJSONObject("pages"); val key=pages.keys().next(); val image=pages.getJSONObject(key).getJSONObject("imageinfo").getJSONArray(0).getString("thumburl"); val bmp=BitmapFactory.decodeStream(URL(image).openStream()); runOnUiThread{if(bmp!=null)iv.setImageBitmap(bmp)}
        }catch(_:Exception){} }
    }
}
