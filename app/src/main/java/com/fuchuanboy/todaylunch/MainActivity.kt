package com.fuchuanboy.todaylunch

import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class MainActivity : Activity() {
    data class Dish(
        val name: String,
        val type: String,
        val ingredient: String,
        val protein: Int,
        val veg: Int,
        val fiber: Int,
        val calcium: Int,
        val iron: Int
    )

    private lateinit var root: LinearLayout
    private lateinit var body: LinearLayout
    private var meal = mutableListOf<Dish>()
    private var tab = 0

    private val porkBase = listOf("青椒","蒜薹","芹菜","木耳","洋葱","豆角","茄子","土豆","白菜","萝卜","冬瓜","莲藕","香菇","杏鲍菇","平菇","酸菜","梅菜","笋","蒜苗","韭菜","黄瓜","西葫芦","苦瓜","青豆","毛豆","豇豆","四季豆","豆芽","粉条","粉皮","豆腐","鸡蛋","榨菜","咸菜","荠菜","荸荠","山药","芋头","玉米","板栗")
    private val beefBase = listOf("芹菜","洋葱","青椒","彩椒","土豆","番茄","西兰花","杏鲍菇","金针菇","木耳","蒜薹","葱","萝卜","胡萝卜","芥蓝","豆芽","韭黄","香菜","酸菜","粉丝","豆腐","冬瓜","山药","莲藕","青豆")
    private val lambBase = listOf("大葱","洋葱","孜然","香菜","萝卜","土豆","胡萝卜","白菜","芹菜","青椒","番茄","山药","冬瓜","酸菜","粉丝","金针菇")
    private val chickenBase = listOf("香菇","土豆","青椒","栗子","板栗","番茄","芹菜","木耳","洋葱","青豆","玉米","胡萝卜","山药","莲藕","冬瓜","茶树菇","金针菇","粉条","豆腐","毛豆","豇豆","花生")
    private val duckBase = listOf("魔芋","土豆","冬瓜","萝卜","青椒","酸菜","香菇","啤酒","莲藕","山药","芋头","豆腐","白菜")
    private val fishBase = listOf("豆腐","酸菜","番茄","葱","蒜","辣椒","剁椒","萝卜","冬瓜","紫苏","白菜","金针菇","粉丝","木耳","藕","蒜薹","青椒","香菜")
    private val shrimpBase = listOf("西兰花","芦笋","黄瓜","鸡蛋","豆腐","玉米","豌豆","毛豆","丝瓜","冬瓜","韭菜","芹菜","番茄","蘑菇","腰果")
    private val vegBase = listOf("上海青","小白菜","菠菜","油麦菜","空心菜","苋菜","生菜","菜心","芥蓝","娃娃菜","大白菜","包菜","西兰花","菜花","芦笋","芹菜","蒜薹","豇豆","四季豆","毛豆","茄子","番茄","黄瓜","丝瓜","冬瓜","苦瓜","西葫芦","土豆","莲藕","山药","胡萝卜","白萝卜","青椒","彩椒","洋葱","豆芽","韭菜","韭黄","香菇","平菇","杏鲍菇","金针菇","木耳","海带","玉米","莴笋","春笋","藕带","秋葵","南瓜","佛手瓜","瓠瓜","芥菜","菜薹","红薯叶")
    private val beanBase = listOf("豆腐","嫩豆腐","老豆腐","豆干","香干","腐竹","豆皮","千张","百叶","毛豆","黄豆芽","绿豆芽","豌豆","青豆","鸡蛋","鸭蛋","鹌鹑蛋")

    private val porkMethods = listOf("炒","烧","炖","蒸","煎","焖","干煸","酱烧","红烧")
    private val beefMethods = listOf("炒","烧","炖","煎","焖","水煮","孜然炒")
    private val lambMethods = listOf("炒","爆炒","烧","炖","焖","孜然炒")
    private val chickenMethods = listOf("炒","烧","炖","焖","蒸","煎","红烧")
    private val duckMethods = listOf("烧","炖","焖","炒")
    private val fishMethods = listOf("清蒸","红烧","香煎","水煮","酸菜炖","剁椒蒸","葱烧")
    private val shrimpMethods = listOf("炒","白灼","蒜蓉","盐水","香煎")
    private val vegMethods = listOf("清炒","蒜蓉炒","蚝油炒","炝炒","醋溜","红烧","干煸","上汤")
    private val beanMethods = listOf("炒","烧","煎","焖","蒸","炖","凉拌")

    private val seasonal = mapOf(
        1 to listOf("菠菜","白菜","上海青","芥蓝","萝卜","西兰花"),
        2 to listOf("菠菜","菜心","上海青","芥蓝","生菜","西兰花"),
        3 to listOf("菠菜","菜心","油麦菜","芹菜","春笋","西兰花"),
        4 to listOf("油麦菜","上海青","苋菜","芦笋","莴笋","西葫芦"),
        5 to listOf("空心菜","苋菜","生菜","油麦菜","丝瓜","西兰花"),
        6 to listOf("空心菜","苋菜","油麦菜","丝瓜","豇豆","黄瓜"),
        7 to listOf("空心菜","苋菜","丝瓜","豇豆","毛豆","黄瓜"),
        8 to listOf("空心菜","苋菜","丝瓜","豇豆","毛豆","冬瓜"),
        9 to listOf("空心菜","油麦菜","上海青","丝瓜","西兰花","芥蓝"),
        10 to listOf("菠菜","上海青","白菜","芥蓝","西兰花","萝卜"),
        11 to listOf("菠菜","白菜","上海青","芥蓝","萝卜","西兰花"),
        12 to listOf("菠菜","白菜","上海青","芥蓝","萝卜","菜心")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildShell()
        showHome()
    }

    private fun buildShell() {
        root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(Color.rgb(247,249,246)) }
        body = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val scroll = ScrollView(this).apply { addView(body); isFillViewport = true }
        root.addView(scroll, LinearLayout.LayoutParams(-1,0,1f))
        root.addView(bottomNav(), LinearLayout.LayoutParams(-1,dp(66)))
        setContentView(root)
    }

    private fun showHome() {
        tab=0; body.removeAllViews(); val box=page()
        addTextTo(box,"⌖ 河南省 · 郑州市  ▾",13f,Color.DKGRAY,Gravity.LEFT,4,4,0,8)
        addTextTo(box,"今天中午吃什么？",31f,Color.rgb(25,100,55),Gravity.CENTER,0,8,0,2)
        addTextTo(box,"营养均衡 · 时令搭配 · 科学膳食",15f,Color.GRAY,Gravity.CENTER,0,0,0,10)
        box.addView(PlateView(this),LinearLayout.LayoutParams(-1,dp(245)).apply{setMargins(dp(10),0,dp(10),dp(8))})
        val b=Button(this).apply{ text="🎲  随机一桌\n生成今日四菜一汤"; textSize=17f; setTextColor(Color.WHITE); setBackgroundColor(Color.rgb(55,165,91)); setOnClickListener{newMeal();showMeal()} }
        box.addView(b,LinearLayout.LayoutParams(-1,dp(64)).apply{setMargins(dp(4),6,dp(4),12)})
        val f=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;gravity=Gravity.CENTER}
        listOf("✓\n营养均衡","🍊\n时令食材","🐖\n猪肉为主","♧\n科学搭配").forEach{t->f.addView(TextView(this).apply{text=t;gravity=Gravity.CENTER;textSize=12f;setTextColor(Color.DKGRAY)},LinearLayout.LayoutParams(0,dp(54),1f))}
        box.addView(f)
        info(box,"菜库规模","当前内置超过 1000 道可随机菜品，覆盖猪肉、牛肉、羊肉、鸡、鸭、鱼、虾、蛋类、豆制品、蔬菜和汤品；后续可继续扩到 2000+。")
        info(box,"随机规则","猪肉为主要动物蛋白；牛羊、鸡鸭、鱼虾轮换；每天至少一份当季绿叶菜，并搭配豆制品/蛋类和汤。")
        body.addView(box)
    }

    private fun showMeal(){
        tab=0; body.removeAllViews(); if(meal.isEmpty())newMeal(); val box=page()
        val top=LinearLayout(this).apply{gravity=Gravity.CENTER_VERTICAL}
        addTextTo(top,"‹",30f,Color.DKGRAY,Gravity.CENTER,0,0,16,0).setOnClickListener{showHome()}
        addTextTo(top,"今日午餐",23f,Color.DKGRAY,Gravity.CENTER,0,0,0,0).layoutParams=LinearLayout.LayoutParams(0,52.dp(),1f)
        addTextTo(top,"↻",27f,Color.DKGRAY,Gravity.CENTER,16,0,0,0).setOnClickListener{newMeal();showMeal()}
        box.addView(top)
        addTextTo(box,SimpleDateFormat("M月d日  EEEE",Locale.CHINA).format(Calendar.getInstance().time)+"    时令：${seasonName()}",13f,Color.GRAY,Gravity.LEFT,4,0,0,8)
        addTextTo(box,"🍃  今日四菜一汤",21f,Color.rgb(30,110,60),Gravity.CENTER,0,0,0,10)
        meal.forEachIndexed{i,d->addDishCard(box,d,when(i){0->"猪肉主菜";1->"动物蛋白轮换";2->"豆制品 / 蛋类";3->"当季烧青菜";else->"汤品"})}
        info(box,"营养均衡度","${score()} 分  · 蛋白质、蔬菜、膳食纤维、钙和铁综合评价")
        val change=Button(this).apply{text="换一桌  ·  无尽随机";textSize=16f;setOnClickListener{newMeal();showMeal()}}
        box.addView(change,LinearLayout.LayoutParams(-1,52.dp()).apply{setMargins(0,14,0,14)})
        body.addView(box)
    }

    private fun showNutrition(){
        tab=1;body.removeAllViews();if(meal.isEmpty())newMeal();val box=page()
        addTextTo(box,"营养分析",25f,Color.rgb(30,70,45),Gravity.CENTER,0,10,0,12)
        addTextTo(box,"${score()}\n营养均衡度",30f,Color.rgb(45,145,78),Gravity.CENTER,0,8,0,12)
        val rows=listOf("蛋白质" to if(meal.sumOf{it.protein}>=14)"充足" else "良好","膳食纤维" to if(meal.sumOf{it.fiber}>=9)"充足" else "良好","维生素" to if(meal.sumOf{it.veg}>=10)"充足" else "良好","钙" to if(meal.sumOf{it.calcium}>=6)"充足" else "良好","铁" to if(meal.sumOf{it.iron}>=7)"充足" else "良好")
        rows.forEach{(a,b)->val r=LinearLayout(this).apply{setPadding(14,9,14,9);gravity=Gravity.CENTER_VERTICAL};addTextTo(r,a,15f,Color.DKGRAY,Gravity.LEFT,0,0,0,0).layoutParams=LinearLayout.LayoutParams(0,-2,1f);addTextTo(r,b,14f,Color.rgb(55,150,80),Gravity.RIGHT,0,0,0,0);box.addView(r)}
        info(box,"搭配评价","猪肉主菜 + 牛羊鸡鸭鱼虾轮换 + 豆制品/蛋类 + 当季绿叶菜 + 汤，避免连续多天单一肉类。")
        info(box,"说明","分数用于午餐搭配参考，不代替个体化营养建议。")
        body.addView(box)
    }

    private fun showSeasonal(){
        tab=2;body.removeAllViews();val box=page();addTextTo(box,"时令蔬菜",25f,Color.rgb(30,70,45),Gravity.CENTER,0,10,0,6);addTextTo(box,"河南当前时令：${seasonName()}",14f,Color.GRAY,Gravity.CENTER,0,0,0,14)
        seasonal[month()]!!.forEach{n->val c=LinearLayout(this).apply{gravity=Gravity.CENTER_VERTICAL;setPadding(12,8,12,8);setBackgroundColor(Color.WHITE)};c.addView(VegView(this,n),LinearLayout.LayoutParams(78.dp(),70.dp()));addTextTo(c,"$n\n适合：清炒、蒜蓉、上汤、搭配肉类",15f,Color.DKGRAY,Gravity.LEFT,10,0,0,0);box.addView(c,LinearLayout.LayoutParams(-1,86.dp()).apply{setMargins(0,4,0,0)})}
        info(box,"时令原则","优先当季蔬菜，每餐保证绿色蔬菜，并尽量避免连续多餐重复同一种烧青菜。")
        body.addView(box)
    }

    private fun showDetail(d:Dish){body.removeAllViews();val box=page();val top=LinearLayout(this).apply{gravity=Gravity.CENTER_VERTICAL};addTextTo(top,"‹",30f,Color.DKGRAY,Gravity.CENTER,0,0,16,0).setOnClickListener{showMeal()};addTextTo(top,d.name,22f,Color.DKGRAY,Gravity.CENTER,0,0,0,0).layoutParams=LinearLayout.LayoutParams(0,52.dp(),1f);addTextTo(top,"☆",28f,Color.rgb(230,170,30),Gravity.CENTER,16,0,0,0);box.addView(top);box.addView(DishArtView(this,d),LinearLayout.LayoutParams(-1,230.dp()).apply{setMargins(6,0,6,8)});addTextTo(box,"${typeLabel(d.type)} · 家常做法",13f,Color.GRAY,Gravity.LEFT,8,0,0,6);info(box,"食材准备",ingredients(d));info(box,"怎么做",steps(d));info(box,"营养特点","蛋白质 ${if(d.protein>=3)"较高" else "适中"} · 蔬菜贡献 ${d.veg} · 膳食纤维 ${d.fiber} · 钙 ${d.calcium} · 铁 ${d.iron}");info(box,"小贴士","少油少盐即可；肉类、鱼虾、蛋类彻底加热。") ;body.addView(box)}

    private fun newMeal(){
        val green=seasonal[month()]!!.random();val main=porkDish();val rotate=rotatingDish();val bean=beanDish();val soup=soupDish();meal=mutableListOf(main,rotate,bean,greenDish(green),soup)
    }

    private fun allNames(base:List<String>,methods:List<String>,prefix:String):List<String>{
        val out=ArrayList<String>();for(m in methods)for(x in base)out.add("$m$prefix$x");return out.distinct()
    }

    private fun porkDish():Dish{val n=allNames(porkBase,porkMethods,"猪肉").random();return make(n,"pork",n.removePrefix("炒猪肉").removePrefix("烧猪肉"))}
    private fun rotatingDish():Dish{
        val choices=listOf("beef","lamb","chicken","duck","fish","shrimp");val t=choices.random();val data=when(t){"beef"->beefBase to beefMethods;"lamb"->lambBase to lambMethods;"chicken"->chickenBase to chickenMethods;"duck"->duckBase to duckMethods;"fish"->fishBase to fishMethods;else->shrimpBase to shrimpMethods};val n=allNames(data.first,data.second,proteinPrefix(t)).random();return make(n,t,n)
    }
    private fun beanDish():Dish{val n=allNames(beanBase,beanMethods,"").random();return make(n,if(n.contains("鸡蛋|鸭蛋|鹌鹑蛋".toRegex()))"egg" else "bean",n)}
    private fun greenDish(g:String):Dish{val n=vegMethods.random()+g;return make(n,"green",g)}
    private fun soupDish():Dish{val soups=listOf("番茄蛋花汤","紫菜蛋花汤","冬瓜排骨汤","菌菇豆腐汤","萝卜瘦肉汤","青菜豆腐汤","冬瓜虾皮汤","丝瓜蛋汤","菠菜蛋花汤","紫菜虾皮汤","玉米排骨汤","山药排骨汤","莲藕排骨汤","海带排骨汤","冬瓜丸子汤","白菜豆腐汤","酸辣汤","三鲜汤","菌菇鸡蛋汤","丝瓜虾仁汤","番茄豆腐汤","毛豆蛋汤","鱼头豆腐汤","菌菇鸡汤","萝卜牛腩汤");return make(soups.random(),"soup","")}

    private fun proteinPrefix(t:String)=when(t){"beef"->"牛肉";"lamb"->"羊肉";"chicken"->"鸡肉";"duck"->"鸭肉";"fish"->"鱼";else->"虾"}

    private fun make(name:String,type:String,ingredient:String):Dish{
        val protein=when(type){"pork","beef","lamb","chicken","duck","fish","shrimp"->4;"bean","egg"->2;"soup"->if(name.contains("排骨|鸡|瘦肉|虾|蛋|牛腩|鱼头".toRegex()))2 else 1;else->0}
        val veg=when(type){"green"->4;"bean","egg"->3;else->if(name.contains("青椒|番茄|芹菜|土豆|白菜|冬瓜|丝瓜|西兰花|洋葱|豆角|木耳|蘑菇|萝卜|山药|莲藕|黄瓜".toRegex()))2 else 0}
        val fiber=if(type=="green")4 else if(type=="bean"||type=="egg")2 else veg
        val calcium=if(name.contains("豆腐|豆干|腐竹|豆皮|千张|百叶|虾皮|紫菜".toRegex()))3 else if(type=="green")1 else 0
        val iron=if(type in listOf("pork","beef","lamb"))2 else if(type=="green"||type=="bean")2 else 1
        return Dish(name,type,ingredient,protein,veg,fiber,calcium,iron)
    }

    private fun score():Int{if(meal.isEmpty())return 90;var s=82;s+=if(meal.count{it.type in listOf("pork","beef","lamb","chicken","duck","fish","shrimp")}==2)6 else 3;s+=if(meal.any{it.type=="green"})4 else 0;s+=if(meal.any{it.type=="bean"||it.type=="egg"})3 else 0;s+=if(meal.sumOf{it.fiber}>=9)3 else 0;return s.coerceIn(78,98)}

    private fun ingredients(d:Dish)=when(d.type){"pork"->"猪肉、${d.ingredient}、葱姜蒜、生抽、少量食用油";"beef"->"牛肉、${d.ingredient}、葱姜蒜、生抽、黑胡椒";"lamb"->"羊肉、${d.ingredient}、大葱、孜然、香菜";"chicken"->"鸡肉、${d.ingredient}、葱姜蒜、生抽";"duck"->"鸭肉、${d.ingredient}、姜、葱、生抽";"fish"->"鲜鱼、${d.ingredient}、姜、葱、生抽";"shrimp"->"鲜虾、${d.ingredient}、葱姜、少量食用油";"bean"->"豆制品、${d.ingredient}、葱姜蒜、生抽";"egg"->"鸡蛋/鸭蛋、${d.ingredient}、葱花、少量食用油";"green"->"${d.ingredient}、蒜、少量食用油、盐";else->"当季蔬菜、蛋/肉、菌菇、葱姜"}

    private fun steps(d:Dish)=when(d.type){"green"->"1. 洗净切段。\n2. 锅热放少量油，下蒜末爆香。\n3. 放入蔬菜大火快炒。\n4. 加盐调味，断生即可出锅。";"fish"->"1. 鱼处理洗净并擦干。\n2. 姜葱去腥。\n3. 按菜名选择清蒸、红烧或水煮。\n4. 鱼肉完全熟透后出锅。";"shrimp"->"1. 虾去虾线洗净。\n2. 姜葱去腥。\n3. 大火快速炒至变色并完全熟透。\n4. 少盐调味。";"soup"->"1. 食材洗净切块。\n2. 冷水或热水下锅。\n3. 小火煮至食材熟透。\n4. 最后少盐调味。";else->"1. 肉类切片或切块，加入少量生抽腌制。\n2. 热锅少油，先将肉类炒至变色。\n3. 加入${d.ingredient}及葱姜蒜翻炒或焖炖。\n4. 调味后确保肉类完全熟透即可出锅。"}

    private fun typeLabel(t:String)=when(t){"pork"->"猪肉主菜";"beef"->"牛肉";"lamb"->"羊肉";"chicken"->"鸡肉";"duck"->"鸭肉";"fish"->"鱼类";"shrimp"->"虾类";"bean"->"豆制品";"egg"->"蛋类";"green"->"时令蔬菜";else->"汤品"}
    private fun seasonName()=when(month()){3,4,5->"春季";6,7,8->"夏季";9,10,11->"秋季";else->"冬季"}
    private fun month()=Calendar.getInstance().get(Calendar.MONTH)+1

    private fun page()=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(16),dp(8),dp(16),dp(18))}
    private fun info(parent:LinearLayout,title:String,text:String){val c=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(14),dp(10),dp(14),dp(10));setBackgroundColor(Color.WHITE)};addTextTo(c,title,16f,Color.rgb(35,90,50),Gravity.LEFT,0,0,0,5);addTextTo(c,text,13f,Color.DKGRAY,Gravity.LEFT,0,0,0,0);parent.addView(c,LinearLayout.LayoutParams(-1,-2).apply{setMargins(0,dp(6),0,0)})}
    private fun addDishCard(parent:LinearLayout,d:Dish,label:String){val c=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;gravity=Gravity.CENTER_VERTICAL;setPadding(dp(8),dp(8),dp(8),dp(8));setBackgroundColor(Color.WHITE);setOnClickListener{showDetail(d)}};c.addView(DishArtView(this,d),LinearLayout.LayoutParams(dp(92),dp(78)));val r=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(10),0,0,0)};addTextTo(r,d.name,17f,Color.rgb(35,35,35),Gravity.LEFT,0,0,0,4);addTextTo(r,label,12f,Color.rgb(60,145,80),Gravity.LEFT,0,0,0,3);addTextTo(r,"蛋白质 ${if(d.protein>=3)"较高" else "适中"} · 点击查看做法  ›",12f,Color.GRAY,Gravity.LEFT,0,0,0,0);c.addView(r,LinearLayout.LayoutParams(0,-2,1f));parent.addView(c,LinearLayout.LayoutParams(-1,dp(92)).apply{setMargins(0,dp(4),0,0)})}
    private fun addTextTo(p:LinearLayout,text:String,size:Float,color:Int,gravity:Int,l:Int,t:Int,r:Int,b:Int):TextView{val v=TextView(this).apply{this.text=text;textSize=size;setTextColor(color);this.gravity=gravity;setPadding(dp(l),dp(t),dp(r),dp(b))};p.addView(v);return v}

    private fun bottomNav():LinearLayout{val n=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;gravity=Gravity.CENTER;setBackgroundColor(Color.WHITE)};listOf("⌂\n今日午餐","▣\n营养分析","♧\n时令蔬菜","☆\n收藏").forEachIndexed{i,s->n.addView(TextView(this).apply{text=s;gravity=Gravity.CENTER;textSize=12f;setTextColor(if(i==tab)Color.rgb(40,150,75) else Color.GRAY);setOnClickListener{when(i){0->showHome();1->showNutrition();2->showSeasonal();else->showMeal()}}},LinearLayout.LayoutParams(0,-1,1f))};return n}
    private fun dp(v:Int)= (v*resources.displayMetrics.density).toInt()

    class PlateView(c:android.content.Context):View(c){private val p=Paint(1);override fun onDraw(x:Canvas){super.onDraw(x);p.color=Color.WHITE;x.drawRoundRect(RectF(40f,18f,width-40f,height-10f),32f,32f,p);p.color=Color.rgb(218,236,210);x.drawCircle(width/2f,height/2f,82f,p);p.color=Color.rgb(85,165,82);x.drawCircle(width/2f-55,height/2f-5,32f,p);p.color=Color.rgb(210,145,70);x.drawCircle(width/2f+55,height/2f-8,35f,p);p.color=Color.rgb(235,190,80);x.drawCircle(width/2f,height/2f+55,30f,p);p.color=Color.rgb(90,165,110);x.drawCircle(width/2f+5,height/2f-65,26f,p)}}
    class VegView(c:android.content.Context,val n:String):View(c){private val p=Paint(1);override fun onDraw(x:Canvas){p.color=Color.rgb(232,246,226);x.drawRoundRect(RectF(2f,2f,width-2f,height-2f),20f,20f,p);p.color=Color.rgb(70,160,80);x.drawCircle(width/2f,height/2f,22f,p)}}
    class DishArtView(c:android.content.Context,val d:Dish):View(c){private val p=Paint(1);override fun onDraw(x:Canvas){p.color=Color.rgb(245,238,220);x.drawRoundRect(RectF(2f,2f,width-2f,height-2f),20f,20f,p);p.color=when(d.type){"green"->Color.rgb(75,165,80);"pork"->Color.rgb(190,100,70);"beef","lamb"->Color.rgb(150,70,55);"fish","shrimp"->Color.rgb(75,145,175);"bean","egg"->Color.rgb(215,170,70);else->Color.rgb(150,175,110)};x.drawCircle(width/2f,height/2f,27f,p);p.color=Color.WHITE;x.drawCircle(width/2f,height/2f,14f,p)}}
}
