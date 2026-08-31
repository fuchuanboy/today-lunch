package com.fuchuanboy.todaylunch

import android.app.Activity
import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread
import kotlin.random.Random

class MainActivity : Activity() {
    data class Dish(val name:String,val group:String,val vegetables:List<String>,val ingredients:String,val steps:List<String>,val query:String)
    private val meat = listOf(
        Dish("鱼香肉丝","动物蛋白",listOf("木耳","胡萝卜","青椒"),"猪里脊、木耳、胡萝卜、青椒、泡椒、葱姜蒜、醋、生抽、糖、淀粉","猪肉切丝腌10分钟；蔬菜切丝并调鱼香汁；肉丝滑炒变色盛出；爆香葱姜蒜和泡椒，下蔬菜，回锅肉丝并倒入鱼香汁，大火收汁。","鱼香肉丝"),
        Dish("洋葱肥牛","动物蛋白",listOf("洋葱","彩椒"),"肥牛片、洋葱、彩椒、蚝油、生抽、黑胡椒","洋葱和彩椒切块；肥牛快速焯水；少油炒香洋葱；加入肥牛和彩椒，调味后大火翻匀。","洋葱肥牛"),
        Dish("红烧排骨","动物蛋白",listOf("土豆","胡萝卜"),"猪排骨、土豆、胡萝卜、葱姜、八角、生抽、冰糖","排骨焯水；冰糖炒糖色后下排骨；加入葱姜八角和调味料，加水炖至软烂；加入土豆胡萝卜再炖熟。","红烧排骨"),
        Dish("照烧鸡腿","动物蛋白",listOf("西兰花","胡萝卜"),"去骨鸡腿、西兰花、胡萝卜、照烧汁、芝麻","鸡腿擦干腌制；鸡皮朝下煎金黄再翻面；加入照烧汁收浓；西兰花和胡萝卜焯熟，与鸡腿一起装盘。","照烧鸡腿"),
        Dish("咖喱鸡肉","动物蛋白",listOf("土豆","胡萝卜","洋葱"),"鸡腿肉、土豆、胡萝卜、洋葱、咖喱块","鸡肉和蔬菜切块；炒香洋葱和鸡肉；加入土豆胡萝卜和水煮软；关小火融化咖喱块并收浓。","咖喱鸡肉"),
        Dish("孜然羊肉","动物蛋白",listOf("洋葱","香菜"),"羊肉片、洋葱、香菜、孜然、辣椒面、生抽","羊肉擦干；热锅大火炒至变色；加入洋葱翻炒；撒孜然和辣椒面，最后加入香菜翻匀。","孜然羊肉"),
        Dish("葱爆羊肉","动物蛋白",listOf("大葱","香菜"),"羊肉片、大葱、香菜、料酒、生抽、白胡椒","羊肉加料酒和白胡椒腌5分钟；大葱切段；大火炒羊肉；加入大葱和生抽快速翻炒，最后放香菜。","葱爆羊肉"),
        Dish("啤酒鸭","动物蛋白",listOf("青椒","土豆"),"鸭块、啤酒、青椒、土豆、姜蒜、豆瓣酱","鸭块焯水；姜蒜和豆瓣酱炒香；下鸭块和啤酒炖至软熟；加入土豆炖透，最后放青椒。","啤酒鸭"),
        Dish("蒜蓉虾","动物蛋白",listOf("粉丝","小米椒"),"鲜虾、粉丝、大蒜、小米椒、生抽、蚝油","粉丝泡软铺盘；虾开背去虾线；蒜末和小米椒炒香；铺在虾上蒸8分钟，淋生抽和热油。","蒜蓉虾"),
        Dish("清蒸鲈鱼","动物蛋白",listOf("葱","姜"),"鲈鱼、葱、姜、蒸鱼豉油、食用油","鱼处理干净划刀；铺姜片，水开后蒸8至10分钟；倒掉蒸汁，铺葱丝；淋蒸鱼豉油和热油。","清蒸鲈鱼"),
        Dish("番茄炖牛腩","动物蛋白",listOf("番茄","土豆"),"牛腩、番茄、土豆、洋葱、姜、八角","牛腩焯水；番茄炒出汁；加入牛腩和香料，加水炖软；加入土豆继续炖熟。","番茄炖牛腩"),
        Dish("糖醋里脊","动物蛋白",listOf("青椒","洋葱"),"里脊肉、淀粉、番茄酱、糖、醋、白芝麻","里脊切条挂糊；炸至金黄；调番茄酱、糖、醋汁；倒入里脊快速裹汁，撒芝麻。","糖醋里脊")
    )
    private val bean = listOf(
        Dish("家常豆腐","豆制品",listOf("木耳","青椒"),"老豆腐、木耳、青椒、葱姜蒜、生抽、蚝油","豆腐切片煎至两面金黄；木耳泡发；爆香葱姜蒜，下木耳青椒；放豆腐和调味料，加少量水焖入味。","家常豆腐"),
        Dish("麻婆豆腐","豆制品",listOf("青蒜","香菇"),"嫩豆腐、牛肉末、豆瓣酱、青蒜、香菇、花椒","豆腐切块焯水；炒香肉末和豆瓣酱；加入香菇和水；放豆腐小火煨入味，最后撒花椒和青蒜。","麻婆豆腐"),
        Dish("香煎豆腐","豆制品",listOf("香菇","小白菜"),"北豆腐、香菇、小白菜、生抽、蒜","豆腐切厚片擦干；煎至两面金黄；香菇切片炒香；加入小白菜和豆腐，调味快速翻匀。","香煎豆腐"),
        Dish("豆干炒芹菜","豆制品",listOf("芹菜","红椒"),"五香豆干、芹菜、红椒、蒜、生抽","豆干切条；芹菜切段；蒜爆香，先炒豆干；加入芹菜红椒，生抽调味，大火炒至断生。","豆干炒芹菜"),
        Dish("木耳炒豆腐皮","豆制品",listOf("木耳","彩椒"),"豆腐皮、木耳、彩椒、蒜、生抽","豆腐皮切条焯水；木耳泡发；蒜爆香，加入豆腐皮和木耳；最后放彩椒快速翻炒。","木耳炒豆腐皮"),
        Dish("香菇烧豆腐","豆制品",listOf("香菇","胡萝卜"),"豆腐、鲜香菇、胡萝卜、葱姜、生抽","豆腐煎黄；香菇胡萝卜切片；葱姜炒香后下香菇；加入豆腐和少量水焖煮入味。","香菇烧豆腐"),
        Dish("青椒炒豆皮","豆制品",listOf("青椒","胡萝卜"),"豆皮、青椒、胡萝卜、蒜、生抽","豆皮切宽条焯水；青椒胡萝卜切丝；蒜爆香；所有食材大火翻炒并调味。","青椒炒豆皮"),
        Dish("番茄烧豆腐","豆制品",listOf("番茄","香葱"),"豆腐、番茄、香葱、蒜、生抽","豆腐煎黄；番茄切块炒出汁；加入豆腐和少量水焖煮；撒香葱出锅。","番茄烧豆腐"),
        Dish("毛豆烧豆腐","豆制品",listOf("毛豆","胡萝卜"),"豆腐、毛豆、胡萝卜、蒜、生抽","毛豆焯熟；豆腐煎黄；加入胡萝卜和毛豆翻炒；加少量水焖入味。","毛豆烧豆腐"),
        Dish("豆腐蒸蛋","豆制品",listOf("香菇","青豆"),"嫩豆腐、鸡蛋、香菇、青豆、生抽","豆腐切小块铺碗；鸡蛋加温水打散；加入香菇青豆；蒸8至10分钟，淋生抽。","豆腐蒸蛋")
    )
    private val green = listOf(
        Dish("蒜蓉上海青","绿叶青菜",listOf("上海青","蒜"),"上海青、大蒜、盐、食用油","上海青洗净沥干；蒜末爆香；大火下青菜快速翻炒；调盐后立即出锅。","蒜蓉上海青"),
        Dish("香菇炒油麦菜","绿叶青菜",listOf("油麦菜","香菇"),"油麦菜、鲜香菇、蒜、生抽","油麦菜切段；香菇切片；蒜爆香后炒香菇；加入油麦菜大火翻炒断生。","香菇炒油麦菜"),
        Dish("蒜蓉菠菜","绿叶青菜",listOf("菠菜","蒜"),"菠菜、大蒜、盐、香油","菠菜焯水去涩；蒜末爆香；加入菠菜快速翻炒；调盐和少量香油。","蒜蓉菠菜"),
        Dish("蚝油生菜","绿叶青菜",listOf("生菜","蒜"),"生菜、蒜、蚝油、生抽","生菜洗净；水开后快速焯10秒；蒜末炒香，加入蚝油生抽；淋在生菜上。","蚝油生菜"),
        Dish("蒜炒芥蓝","绿叶青菜",listOf("芥蓝","蒜"),"芥蓝、大蒜、盐、蚝油","芥蓝洗净切段；蒜爆香；先炒梗后下叶；加蚝油快速翻炒。","蒜炒芥蓝"),
        Dish("清炒小白菜","绿叶青菜",listOf("小白菜","蒜"),"小白菜、大蒜、盐、食用油","小白菜洗净切段；蒜爆香；大火快速翻炒；调盐出锅。","清炒小白菜"),
        Dish("腐乳空心菜","绿叶青菜",listOf("空心菜","腐乳"),"空心菜、红腐乳、蒜、生抽","空心菜切段；腐乳压碎调少量水；蒜爆香；下空心菜大火炒软，倒腐乳汁翻匀。","腐乳空心菜"),
        Dish("蒜蓉苋菜","绿叶青菜",listOf("苋菜","蒜"),"苋菜、大蒜、盐、食用油","苋菜洗净；蒜爆香；加入苋菜大火翻炒；调盐至刚熟即可。","蒜蓉苋菜")
    )
    private val vitamin = listOf(
        Dish("番茄炒蛋","维生素轮换",listOf("番茄","鸡蛋"),"番茄、鸡蛋、葱、盐、食用油","鸡蛋炒熟盛出；番茄切块炒出汁；加入鸡蛋翻匀；调盐撒葱。","番茄炒蛋"),
        Dish("彩椒炒木耳","维生素轮换",listOf("彩椒","木耳"),"彩椒、木耳、蒜、生抽","彩椒切块；木耳泡发；蒜爆香；加入木耳和彩椒大火翻炒断生，调味出锅。","彩椒炒木耳"),
        Dish("西兰花胡萝卜","维生素轮换",listOf("西兰花","胡萝卜"),"西兰花、胡萝卜、蒜、生抽","西兰花掰小朵，胡萝卜切片；焯水；蒜爆香；加入两种蔬菜快速翻炒调味。","西兰花胡萝卜"),
        Dish("醋溜白菜","维生素轮换",listOf("白菜","彩椒"),"大白菜、彩椒、蒜、醋、生抽","白菜切片；彩椒切丝；蒜爆香；大火炒白菜和彩椒，沿锅边烹醋和生抽。","醋溜白菜"),
        Dish("凉拌黄瓜","维生素轮换",listOf("黄瓜","木耳"),"黄瓜、木耳、蒜、醋、生抽、芝麻油","黄瓜拍碎切段；木耳焯熟；蒜末、醋、生抽和芝麻油调汁；拌匀即可。","凉拌黄瓜"),
        Dish("蒜蓉南瓜","维生素轮换",listOf("南瓜","蒜"),"南瓜、大蒜、盐、食用油","南瓜切块蒸至七成熟；蒜末炒香；加入南瓜翻炒，少量加水焖熟。","蒜蓉南瓜"),
        Dish("胡萝卜炒玉米","维生素轮换",listOf("胡萝卜","玉米"),"胡萝卜、玉米粒、豌豆、蒜、生抽","胡萝卜切丁；玉米豌豆焯熟；蒜爆香；加入全部食材翻炒调味。","胡萝卜炒玉米"),
        Dish("蒜香西葫芦","维生素轮换",listOf("西葫芦","红椒"),"西葫芦、红椒、蒜、生抽","西葫芦切片；红椒切丝；蒜爆香；大火炒至刚熟，调生抽出锅。","蒜香西葫芦"),
        Dish("清炒芦笋","维生素轮换",listOf("芦笋","彩椒"),"芦笋、彩椒、蒜、盐","芦笋切段焯水；彩椒切条；蒜爆香；两种蔬菜大火翻炒至脆嫩。","清炒芦笋")
    )
    private val soups = listOf(
        Dish("冬瓜虾皮汤","汤",listOf("冬瓜","虾皮"),"冬瓜、虾皮、姜、葱、盐","冬瓜切片；姜片加水烧开；加入冬瓜和虾皮煮至透明；调盐撒葱。","冬瓜虾皮汤"),
        Dish("番茄豆腐汤","汤",listOf("番茄","豆腐"),"番茄、豆腐、鸡蛋、葱、盐","番茄炒出汁后加水；加入豆腐煮开；淋入蛋液；调盐撒葱。","番茄豆腐汤"),
        Dish("紫菜蛋花汤","汤",listOf("紫菜","鸡蛋"),"紫菜、鸡蛋、葱、香油、盐","水烧开；加入紫菜；蛋液沿锅边慢慢倒入；调盐和香油，撒葱。","紫菜蛋花汤"),
        Dish("菌菇鸡蛋汤","汤",listOf("菌菇","鸡蛋"),"香菇、平菇、鸡蛋、葱、盐","菌菇切片煮熟；蛋液慢慢倒入；调盐；撒葱即可。","菌菇鸡蛋汤"),
        Dish("玉米排骨汤","汤",listOf("玉米","胡萝卜"),"排骨、玉米、胡萝卜、姜、盐","排骨焯水；加姜和水炖40分钟；加入玉米胡萝卜再炖20分钟；调盐。","玉米排骨汤"),
        Dish("海带豆腐汤","汤",listOf("海带","豆腐"),"海带、豆腐、姜、葱、盐","海带洗净切段；豆腐切块；加姜煮开；小火煮15分钟，调盐撒葱。","海带豆腐汤")
    )

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); showMeal() }

    private fun showMeal() {
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setBackgroundColor(Color.rgb(248,246,241));setPadding(18,18,18,18)}
        val scroll=ScrollView(this).apply{addView(root)}
        val title=TextView(this).apply{text="今天中午吃什么";textSize=28f;setTextColor(Color.rgb(45,38,31));typeface=Typeface.DEFAULT_BOLD;gravity=Gravity.CENTER;padding=0}
        root.addView(title,LinearLayout.LayoutParams(-1,70))
        val subtitle=TextView(this).apply{text="四菜一汤 · 荤素均衡 · 今天不重复主蔬菜";textSize=14f;setTextColor(Color.DKGRAY);gravity=Gravity.CENTER}
        root.addView(subtitle,LinearLayout.LayoutParams(-1,42))
        val meal=generateMeal()
        meal.forEachIndexed{index,dish->root.addView(dishCard(dish,index))}
        val refresh=Button(this).apply{text="换一桌";textSize=18f;setOnClickListener{showMeal()}}
        root.addView(refresh,LinearLayout.LayoutParams(-1,60))
        setContentView(scroll)
    }

    private fun generateMeal():List<Dish>{
        repeat(80){
            val m=meat.random(); val b=bean.random(); val g=green.random(); val v=vitamin.random();
            val all=(m.vegetables+b.vegetables+g.vegetables+v.vegetables).map{it.trim()}
            if(all.distinct().size==all.size) return listOf(m,b,g,v,soups.random())
        }
        return listOf(meat.random(),bean.random(),green.random(),vitamin.random(),soups.random())
    }

    private fun dishCard(d:Dish,index:Int):LinearLayout{
        val card=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(14,14,14,14);setBackgroundColor(Color.WHITE)}
        val head=TextView(this).apply{text="${index+1}. ${d.name}";textSize=20f;typeface=Typeface.DEFAULT_BOLD;setTextColor(Color.rgb(55,45,35));setPadding(0,6,0,4)}
        card.addView(head)
        val tag=TextView(this).apply{text=d.group;textSize=13f;setTextColor(Color.rgb(120,95,55));setPadding(0,0,0,6)}
        card.addView(tag)
        val image=ImageView(this).apply{scaleType=ImageView.ScaleType.CENTER_CROP;setBackgroundColor(Color.rgb(235,232,225))}
        card.addView(image,LinearLayout.LayoutParams(-1,210))
        loadImage(d.query,image)
        val veg=TextView(this).apply{text="配菜：${d.vegetables.joinToString("、")}";textSize=14f;setTextColor(Color.DKGRAY);setPadding(0,8,0,4)}
        card.addView(veg)
        val score=TextView(this).apply{text="营养搭配：${score(d)}分";textSize=15f;setTextColor(Color.rgb(45,120,70));typeface=Typeface.DEFAULT_BOLD}
        card.addView(score)
        val detail=Button(this).apply{text="查看食材与做法";setOnClickListener{showDetail(d)}}
        card.addView(detail,LinearLayout.LayoutParams(-1,52))
        val lp=LinearLayout.LayoutParams(-1,ViewGroup.LayoutParams.WRAP_CONTENT);lp.setMargins(0,0,0,14);card.layoutParams=lp
        return card
    }

    private fun score(d:Dish):Int=when(d.group){"动物蛋白"->94;"豆制品"->92;"绿叶青菜"->96;"维生素轮换"->95;else->90}

    private fun showDetail(d:Dish){
        val text="食材\n${d.ingredients}\n\n制作步骤\n${d.steps.mapIndexed{i,s->"${i+1}. $s"}.joinToString("\n")}\n\n本餐定位：${d.group}"
        AlertDialog.Builder(this).setTitle(d.name).setMessage(text).setPositiveButton("知道了",null).show()
    }

    private fun loadImage(query:String,view:ImageView){
        thread{
            try{
                val q=URLEncoder.encode(query+" food","UTF-8")
                val api="https://commons.wikimedia.org/w/api.php?action=query&format=json&generator=search&gsrsearch=$q&gsrnamespace=6&gsrlimit=1&prop=imageinfo&iiprop=url&iiurlwidth=900&origin=*"
                val conn=URL(api).openConnection() as HttpURLConnection;conn.connectTimeout=7000;conn.readTimeout=7000
                val json=conn.inputStream.bufferedReader().use{it.readText()};conn.disconnect()
                val pages=JSONObject(json).optJSONObject("query")?.optJSONObject("pages")
                var url:String?=null
                if(pages!=null){val keys=pages.keys();if(keys.hasNext()){val p=pages.getJSONObject(keys.next());val info=p.optJSONArray("imageinfo");if(info!=null&&info.length()>0)url=info.getJSONObject(0).optString("thumburl",info.getJSONObject(0).optString("url"))}}
                if(url!=null){val bytes=URL(url).openStream().use{it.readBytes()};val bmp=BitmapFactory.decodeByteArray(bytes,0,bytes.size);runOnUiThread{if(bmp!=null)view.setImageBitmap(bmp)}}
            }catch(_:Exception){}
        }
    }
}
