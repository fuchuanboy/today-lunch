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
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : Activity() {
    data class Dish(
        val name: String,
        val type: String,
        val ingredient: String,
        val calories: Int,
        val protein: Int,
        val fiber: Int,
        val calcium: Int,
        val iron: Int,
        val desc: String
    )

    private lateinit var root: LinearLayout
    private lateinit var body: LinearLayout
    private var meal = mutableListOf<Dish>()
    private val favorites = mutableListOf<Dish>()
    private var tab = 0

    private val pork = listOf("青椒","蒜薹","芹菜","木耳","洋葱","豆角","茄子","土豆","白菜","萝卜","冬瓜","莲藕","香菇","杏鲍菇","平菇","酸菜","梅菜","笋","蒜苗","韭菜","黄瓜","西葫芦","苦瓜","青豆","毛豆","豇豆","四季豆","豆芽","粉条","千张")
    private val beef = listOf("洋葱","芹菜","青椒","彩椒","土豆","番茄","西兰花","杏鲍菇","金针菇","木耳","蒜薹","萝卜","胡萝卜","芥蓝","豆芽","韭黄","酸菜","粉丝","豆腐","冬瓜","山药","莲藕","青豆","土豆片","娃娃菜","生菜","香菇","茶树菇","大葱","包菜")
    private val lamb = listOf("大葱","洋葱","孜然","香菜","萝卜","土豆","胡萝卜","白菜","芹菜","青椒","番茄","山药","冬瓜","酸菜","粉丝","金针菇","蒜薹","韭菜","香菇","白萝卜","彩椒","豆腐","包菜","莴笋","莲藕","土豆片","青蒜","辣椒","花菜","杏鲍菇")
    private val chicken = listOf("香菇","土豆","青椒","板栗","番茄","芹菜","木耳","洋葱","青豆","玉米","胡萝卜","山药","莲藕","冬瓜","茶树菇","金针菇","粉条","豆腐","毛豆","豇豆","花生","西兰花","彩椒","娃娃菜","南瓜","大葱","蒜香","柠檬","荷兰豆","蘑菇")
    private val duck = listOf("魔芋","土豆","冬瓜","萝卜","青椒","酸菜","香菇","莲藕","山药","芋头","豆腐","白菜","啤酒","海带","笋","梅干菜","毛豆","洋葱","紫苏","红椒")
    private val fish = listOf("豆腐","酸菜","番茄","葱","蒜","辣椒","剁椒","萝卜","冬瓜","紫苏","白菜","金针菇","粉丝","木耳","藕","蒜薹","青椒","香菜","芹菜","洋葱","茄子","泡椒","酸萝卜","海带","豆芽","雪菜","菌菇","老豆腐","丝瓜","芥菜")
    private val shrimp = listOf("西兰花","芦笋","黄瓜","鸡蛋","豆腐","玉米","豌豆","毛豆","丝瓜","冬瓜","韭菜","芹菜","番茄","蘑菇","腰果","青椒","荷兰豆","彩椒","蒜薹","葱","粉丝","南瓜","菠菜","芥蓝","胡萝卜")
    private val egg = listOf("番茄","韭菜","青椒","木耳","虾仁","洋葱","菠菜","黄瓜","香椿","香菇","西葫芦","蒜薹","玉米","豌豆","青豆","银鱼","豆腐","紫菜","芹菜","葱")
    private val bean = listOf("麻婆豆腐","家常豆腐","葱烧豆腐","香煎豆腐","小葱拌豆腐","香干炒芹菜","香干炒肉","腐竹木耳","腐竹烧肉","豆皮炒青椒","千张炒芹菜","千张结烧肉","豆腐泡烧白菜","毛豆烧豆腐","青豆虾仁","凉拌豆皮","香菇豆腐","白菜豆腐","酸菜豆腐","番茄豆腐")

    private val veg = listOf("上海青","小白菜","菠菜","油麦菜","空心菜","苋菜","生菜","菜心","芥蓝","娃娃菜","大白菜","包菜","西兰花","菜花","芦笋","芹菜","蒜薹","豇豆","四季豆","毛豆","茄子","番茄","黄瓜","丝瓜","冬瓜","苦瓜","西葫芦","土豆","莲藕","山药","胡萝卜","白萝卜","青椒","彩椒","洋葱","豆芽","韭菜","韭黄","香菇","平菇","杏鲍菇","金针菇","木耳","海带","莴笋","春笋","藕带","秋葵","南瓜","佛手瓜","瓠瓜","芥菜","菜薹","红薯叶")

    private val porkMethods = listOf("小炒","红烧","酱烧","蒜香","干煸","家常","鱼香","香辣","葱爆","梅菜烧")
    private val beefMethods = listOf("小炒","黑椒炒","洋葱炒","水煮","红烧","孜然炒","葱爆","酱烧","咖喱","番茄炖")
    private val lambMethods = listOf("葱爆","孜然炒","红烧","炖","小炒","香辣","酱烧","番茄炖","萝卜炖","干煸")
    private val chickenMethods = listOf("香菇烧","土豆烧","红烧","黄焖","宫保","辣子","咖喱","照烧","蒜香煎","葱油")
    private val duckMethods = listOf("红烧","啤酒焖","魔芋烧","酸菜炖","香辣","酱烧","莲藕炖","冬瓜炖")
    private val fishMethods = listOf("清蒸","红烧","水煮","酸菜炖","剁椒蒸","香煎","葱烧","番茄炖","豆瓣烧","紫苏烧")
    private val shrimpMethods = listOf("白灼","蒜蓉炒","清炒","盐水","香煎","椒盐","葱香")
    private val eggMethods = listOf("炒","煎","蒸","烧","滑炒","家常")
    private val vegMethods = listOf("清炒","蒜蓉炒","蚝油炒","炝炒","醋溜","红烧","干煸","上汤")

    private val fusion = listOf(
        "照烧鸡腿","洋葱肥牛","咖喱鸡肉饭","黑椒牛柳","日式咖喱猪排","番茄牛腩","韩式辣鸡","照烧猪排","咖喱牛肉饭","黑椒鸡柳",
        "蜜汁叉烧","日式肥牛饭","泰式柠檬鸡","番茄肉酱意面","葱油鸡饭","香辣鸡排","芝士焗鸡肉","咖喱虾仁","黑椒牛肉粒","照烧鸡翅",
        "韩式辣炒猪肉","日式姜烧猪肉","咖喱羊肉饭","番茄鸡肉煲","照烧三文鱼","泰式酸辣虾","韩式辣牛肉","日式照烧鸭","咖喱鱼块","黑椒虾仁"
    )

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
        newMeal()
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
        addTextTo(box,"⌖ 河南省 · 郑州市  ▾",13f,Color.DKGRAY,Gravity.LEFT,2,6,0,6)
        addTextTo(box,"今天中午吃什么？",31f,Color.rgb(25,100,55),Gravity.CENTER,0,10,0,2)
        addTextTo(box,"营养均衡 · 时令搭配 · 科学膳食",15f,Color.GRAY,Gravity.CENTER,0,0,0,12)
        box.addView(PlateView(this),LinearLayout.LayoutParams(-1,dp(230)).apply{setMargins(dp(4),0,dp(4),dp(8))})
        val b=Button(this).apply{text="🎲  随机一桌\n生成今日四菜一汤";textSize=17f;setTextColor(Color.WHITE);setBackgroundColor(Color.rgb(55,165,91));setOnClickListener{newMeal();showMeal()}}
        box.addView(b,LinearLayout.LayoutParams(-1,dp(66)).apply{setMargins(dp(4),4,dp(4),12)})
        val f=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;gravity=Gravity.CENTER}
        listOf("✓\n营养均衡","🍊\n时令食材","🥩\n荤素搭配","🍚\n中式百味").forEach{t->f.addView(TextView(this).apply{text=t;gravity=Gravity.CENTER;textSize=12f;setTextColor(Color.DKGRAY)},LinearLayout.LayoutParams(0,dp(52),1f))}
        box.addView(f)
        card(box,"菜库规模","3000+ 道可组合菜品：猪、牛、羊、鸡、鸭、鱼、虾、蛋、豆制品、时令蔬菜和汤品。传统家常与融合口味一起收录，例如照烧鸡腿、洋葱肥牛、咖喱鸡肉饭。")
        card(box,"随机规则","猪肉为主要动物蛋白；牛羊肉、鸡鸭、鱼虾轮换。每桌固定四菜一汤，其中至少一份当季绿叶菜，并兼顾豆制品/蛋类与汤品。")
        body.addView(box)
    }

    private fun showMeal(){
        tab=0;body.removeAllViews();val box=page()
        val top=LinearLayout(this).apply{gravity=Gravity.CENTER_VERTICAL}
        addTextTo(top,"‹",30f,Color.DKGRAY,Gravity.CENTER,0,0,8,0).setOnClickListener{showHome()}
        addTextTo(top,"今日午餐",23f,Color.DKGRAY,Gravity.CENTER,0,0,0,0).layoutParams=LinearLayout.LayoutParams(0,dp(52),1f)
        addTextTo(top,"↻",27f,Color.DKGRAY,Gravity.CENTER,8,0,0,0).setOnClickListener{newMeal();showMeal()}
        box.addView(top)
        addTextTo(box,SimpleDateFormat("M月d日  EEEE",Locale.CHINA).format(Calendar.getInstance().time)+"    时令：${seasonName()}",13f,Color.GRAY,Gravity.LEFT,4,0,0,8)
        addTextTo(box,"🍃  今日四菜一汤",21f,Color.rgb(30,110,60),Gravity.CENTER,0,0,0,10)
        meal.forEachIndexed{i,d->addDishCard(box,d,when(i){0->"猪肉主菜";1->"动物蛋白轮换";2->if(d.type=="bean"||d.type=="egg")"豆制品 / 蛋类" else "第二道荤菜";3->"当季烧青菜";else->"汤品"})}
        card(box,"今日营养","营养均衡度 ${score()} 分  · 蛋白质、蔬菜、膳食纤维、钙、铁综合评价")
        val nutrition=Button(this).apply{text="查看营养分析";textSize=15f;setOnClickListener{showMine()}}
        box.addView(nutrition,LinearLayout.LayoutParams(-1,dp(50)).apply{setMargins(0,dp(10),0,dp(4))})
        val change=Button(this).apply{text="换一桌  ·  无尽随机";textSize=16f;setOnClickListener{newMeal();showMeal()}}
        box.addView(change,LinearLayout.LayoutParams(-1,dp(52)).apply{setMargins(0,dp(4),0,dp(14))})
        body.addView(box)
    }

    private fun showRecipes(){
        tab=1;body.removeAllViews();val box=page()
        addTextTo(box,"菜谱",25f,Color.rgb(30,70,45),Gravity.CENTER,0,10,0,4)
        addTextTo(box,"海纳百川的中式菜库 · 3000+ 可组合菜品",14f,Color.GRAY,Gravity.CENTER,0,0,0,12)
        card(box,"菜库已收录","${libraryCount()} 道可生成菜品。传统家常、地方风味、家常融合口味一起收录。")
        val categories=listOf("猪肉" to pork.size*porkMethods.size,"牛肉" to beef.size*beefMethods.size,"羊肉" to lamb.size*lambMethods.size,"鸡肉" to chicken.size*chickenMethods.size+3,"鸭肉" to duck.size*duckMethods.size,"鱼类" to fish.size*fishMethods.size,"虾类" to shrimp.size*shrimpMethods.size,"蛋类 / 豆制品" to (egg.size*eggMethods.size+bean.size),"时令蔬菜" to veg.size*vegMethods.size)
        categories.forEach{(name,countValue)->val row=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;gravity=Gravity.CENTER_VERTICAL;setPadding(dp(14),dp(12),dp(14),dp(12));setBackgroundColor(Color.WHITE);setOnClickListener{val sample=when{name=="猪肉"->makeProtein("pork",pork.random(),porkMethods.random());name=="牛肉"->makeProtein("beef",beef.random(),beefMethods.random());name=="羊肉"->makeProtein("lamb",lamb.random(),lambMethods.random());name=="鸡肉"->makeProtein("chicken",chicken.random(),chickenMethods.random());name=="鸭肉"->makeProtein("duck",duck.random(),duckMethods.random());name=="鱼类"->makeProtein("fish",fish.random(),fishMethods.random());name=="虾类"->makeProtein("shrimp",shrimp.random(),shrimpMethods.random());name=="时令蔬菜"->makeGreen(seasonal[month()]!!.random());else->makeEggOrBean()};showDetail(sample)}};row.addView(DishArtView(this,Dish(name,"other","",180,5,3,3,2,"")),LinearLayout.LayoutParams(dp(68),dp(58)));addTextTo(row,"$name\n$countValue 道 · 点开随机一道",15f,Color.DKGRAY,Gravity.LEFT,12,0,0,0);box.addView(row,LinearLayout.LayoutParams(-1,dp(76)).apply{setMargins(0,dp(4),0,0)})}
        card(box,"特色融合","照烧鸡腿 · 洋葱肥牛 · 咖喱鸡肉饭 · 黑椒牛柳 · 日式咖喱猪排 · 番茄牛腩 · 韩式辣鸡等，保留中式家庭餐桌的灵活性。")
        body.addView(box)
    }

    private fun showFavorites(){tab=2;body.removeAllViews();val box=page();addTextTo(box,"收藏",25f,Color.rgb(30,70,45),Gravity.CENTER,0,10,0,12);if(favorites.isEmpty())card(box,"还没有收藏","在菜谱详情页点 ☆ 收藏喜欢的菜。") else favorites.forEach{addDishCard(box,it,"我的收藏")};body.addView(box)}

    private fun showMine(){
        tab=3;body.removeAllViews();val box=page()
        addTextTo(box,"营养分析",25f,Color.rgb(30,70,45),Gravity.CENTER,0,10,0,12)
        addTextTo(box,"${score()}\n营养均衡度",30f,Color.rgb(45,145,78),Gravity.CENTER,0,8,0,12)
        val protein=meal.sumOf{it.protein};val fiber=meal.sumOf{it.fiber};val calcium=meal.sumOf{it.calcium};val iron=meal.sumOf{it.iron};val vegCount=meal.count{it.type=="green"}
        listOf("蛋白质" to if(protein>=14)"充足" else "良好","膳食纤维" to if(fiber>=9)"充足" else "良好","蔬菜" to if(vegCount>=1)"达标" else "需要增加","钙" to if(calcium>=6)"较好" else "一般","铁" to if(iron>=7)"较好" else "一般").forEach{(name,value)->val r=LinearLayout(this).apply{setPadding(dp(14),dp(10),dp(14),dp(10));gravity=Gravity.CENTER_VERTICAL};addTextTo(r,name,15f,Color.DKGRAY,Gravity.LEFT,0,0,0,0).layoutParams=LinearLayout.LayoutParams(0,-2,1f);addTextTo(r,value,14f,Color.rgb(55,150,80),Gravity.RIGHT,0,0,0,0);box.addView(r,LinearLayout.LayoutParams(-1,dp(46)).apply{setMargins(0,dp(2),0,0)})}
        card(box,"搭配评价","今天采用猪肉主菜 + 轮换动物蛋白 + 豆制品/蛋类 + 时令烧青菜 + 汤品的结构。分数是日常午餐搭配参考，不替代个体化营养建议。")
        card(box,"河南时令","当前月份优先使用 ${seasonal[month()]!!.joinToString("、")}。")
        body.addView(box)
    }

    private fun newMeal(){
        val green=seasonal[month()]!!.random()
        val rotateType=listOf("beef","lamb","chicken","duck","fish","shrimp").random()
        val third=if(Random.nextInt(100)<25) makeSpecial() else if(Random.nextBoolean()) makeEggOrBean() else makeProtein("chicken",chicken.random(),chickenMethods.random())
        meal=mutableListOf(makeProtein("pork",pork.random(),porkMethods.random()),makeProtein(rotateType,ingredientFor(rotateType).random(),methodFor(rotateType).random()),third,makeGreen(green),makeSoup())
    }

    private fun makeSpecial():Dish{
        val name=fusion.random()
        val type=when{name.contains("牛|肥牛".toRegex())->"beef";name.contains("羊")->"lamb";name.contains("鸭")->"duck";name.contains("虾")->"shrimp";name.contains("鱼")->"fish";name.contains("猪|排骨|叉烧")->"pork";else->"chicken"}
        return Dish(name,type,"融合风味配料",190,5,2,2,2,"${name}属于家常融合口味，保留中式家庭餐桌的做法灵活性。")
    }

    private fun ingredientFor(type:String)=when(type){"beef"->beef;"lamb"->lamb;"chicken"->chicken;"duck"->duck;"fish"->fish;else->shrimp}
    private fun methodFor(type:String)=when(type){"beef"->beefMethods;"lamb"->lambMethods;"chicken"->chickenMethods;"duck"->duckMethods;"fish"->fishMethods;else->shrimpMethods}

    private fun makeProtein(type:String,ingredient:String,method:String):Dish{
        val name=when{type=="beef"&&ingredient=="洋葱"->"洋葱肥牛";type=="chicken"&&method=="照烧"->"照烧鸡腿";type=="chicken"&&method=="咖喱"->"咖喱鸡肉饭";else->method+proteinName(type)+ingredient}
        val description=when(name){"洋葱肥牛"->"肥牛片配洋葱快炒，咸香微甜，适合米饭。";"照烧鸡腿"->"鸡腿煎香后收照烧汁，外香里嫩。";"咖喱鸡肉饭"->"鸡肉、土豆和胡萝卜煮入咖喱汁，配米饭。";else->"$method${proteinName(type)}搭配$ingredient，家常口味，少油快炒或焖炖。"}
        val base=when(type){"pork"->Triple(4,2,2);"beef","lamb"->Triple(5,2,2);"chicken","duck"->Triple(5,2,1);"fish","shrimp"->Triple(4,2,1);else->Triple(3,1,1)}
        return Dish(name,type,ingredient,180+Random.nextInt(80),base.first,base.second,if(ingredient.contains("豆腐"))3 else 1,if(type in listOf("pork","beef","lamb"))2 else 1,description)
    }

    private fun makeEggOrBean():Dish{
        if(Random.nextBoolean()){val ingredient=egg.random();val method=eggMethods.random();return Dish("$method鸡蛋炒$ingredient","egg",ingredient,150,2,2,2,1,"鸡蛋与$ingredient搭配，口感家常，适合午餐。")}
        val base=bean.random();return Dish(base,"bean",base,160,3,2,4,2,"豆制品提供植物蛋白，搭配葱姜蒜家常烹调。")
    }

    private fun makeGreen(green:String):Dish{val method=vegMethods.random();return Dish("$method$green","green",green,90,1,4,1,2,"当季$green大火快炒，少油少盐，保持脆嫩。")}

    private fun makeSoup():Dish{
        val soups=listOf("番茄蛋花汤","紫菜蛋花汤","冬瓜排骨汤","菌菇豆腐汤","萝卜瘦肉汤","青菜豆腐汤","冬瓜虾皮汤","丝瓜蛋汤","菠菜蛋花汤","紫菜虾皮汤","玉米排骨汤","山药排骨汤","莲藕排骨汤","海带排骨汤","冬瓜丸子汤","白菜豆腐汤","酸辣汤","三鲜汤","菌菇鸡蛋汤","丝瓜虾仁汤","番茄豆腐汤","毛豆蛋汤","鱼头豆腐汤","菌菇鸡汤","萝卜牛腩汤","酸菜鱼汤","菌菇瘦肉汤","冬瓜肉丸汤","紫菜豆腐汤","玉米鸡汤")
        val name=soups.random();return Dish(name,"soup","",100,if(name.contains("排骨|瘦肉|丸|鸡|虾|蛋|鱼|牛腩".toRegex()))2 else 1,1,if(name.contains("豆腐|紫菜|虾皮".toRegex()))3 else 1,1,"食材洗净后入锅，煮至熟透，最后少盐调味。")
    }

    private fun score():Int{if(meal.isEmpty())return 90;var s=80;s+=if(meal.count{it.type in listOf("pork","beef","lamb","chicken","duck","fish","shrimp")}>=2)6 else 2;s+=if(meal.any{it.type=="green"})5 else 0;s+=if(meal.any{it.type=="bean"||it.type=="egg"})4 else 1;s+=if(meal.sumOf{it.fiber}>=9)3 else 1;s+=if(meal.any{it.type=="soup"})1 else 0;return s.coerceIn(80,98)}

    private fun showDetail(d:Dish){
        body.removeAllViews();val box=page();val top=LinearLayout(this).apply{gravity=Gravity.CENTER_VERTICAL}
        addTextTo(top,"‹",30f,Color.DKGRAY,Gravity.CENTER,0,0,8,0).setOnClickListener{showRecipes()}
        addTextTo(top,d.name,22f,Color.DKGRAY,Gravity.CENTER,0,0,0,0).layoutParams=LinearLayout.LayoutParams(0,dp(52),1f)
        val star=TextView(this).apply{text=if(favorites.any{it.name==d.name})"★" else "☆";textSize=28f;gravity=Gravity.CENTER;setTextColor(Color.rgb(235,175,25));setOnClickListener{if(favorites.any{it.name==d.name})favorites.removeAll{it.name==d.name}else favorites.add(d);showDetail(d)}}
        top.addView(star,LinearLayout.LayoutParams(dp(45),dp(52)));box.addView(top)
        box.addView(DishArtView(this,d),LinearLayout.LayoutParams(-1,dp(220)).apply{setMargins(dp(4),0,dp(4),dp(10))})
        addTextTo(box,typeLabel(d.type)+" · 家常做法",13f,Color.GRAY,Gravity.LEFT,8,0,0,6)
        card(box,"这道菜",d.desc);card(box,"食材准备",ingredients(d));card(box,"怎么做",steps(d));card(box,"营养特点","约 ${d.calories} kcal · 蛋白质 ${d.protein} · 膳食纤维 ${d.fiber} · 钙 ${d.calcium} · 铁 ${d.iron}");card(box,"小贴士","家庭午餐建议少油少盐；肉类、蛋类、鱼虾彻底加热。食材过敏者按个人情况替换。")
        body.addView(box)
    }

    private fun ingredients(d:Dish)=when(d.type){"pork"->"猪肉、${d.ingredient}、葱姜蒜、生抽、少量食用油";"beef"->"肥牛/牛肉、${d.ingredient}、洋葱、黑胡椒、生抽、少量油";"lamb"->"羊肉、${d.ingredient}、大葱、孜然、香菜";"chicken"->"鸡腿/鸡肉、${d.ingredient}、葱姜蒜、生抽；照烧另加蜂蜜或照烧汁";"duck"->"鸭肉、${d.ingredient}、姜、葱、生抽";"fish"->"鲜鱼、${d.ingredient}、姜、葱、生抽";"shrimp"->"鲜虾、${d.ingredient}、葱姜、少量食用油";"egg"->"鸡蛋、${d.ingredient}、葱花、少量食用油";"bean"->"豆制品、${d.ingredient}、葱姜蒜、生抽";"green"->"${d.ingredient}、蒜、少量食用油、盐";"soup"->"对应汤品主料、葱姜、少量盐；具体按菜名搭配";else->"主料、时令蔬菜、葱姜蒜、基础调味料"}

    private fun steps(d:Dish)=when{d.name=="照烧鸡腿"->"1. 鸡腿去骨或划刀，擦干水分。\n2. 少油煎至两面金黄。\n3. 加生抽、少量蜂蜜和清水。\n4. 小火收汁，鸡肉熟透。\n5. 切块装盘，淋上锅中酱汁。";d.name=="洋葱肥牛"->"1. 洋葱切丝。\n2. 肥牛快速焯至变色捞出。\n3. 热锅少油炒香洋葱。\n4. 放肥牛，加生抽、黑胡椒快速翻匀。\n5. 断生入味即可出锅。";d.name=="咖喱鸡肉饭"->"1. 鸡肉切块，土豆胡萝卜切块。\n2. 鸡肉煎至变色。\n3. 加土豆、胡萝卜和清水煮熟。\n4. 加咖喱块小火融化并收浓。\n5. 配米饭食用。";d.type=="green"->"1. 洗净切段。\n2. 锅热放少量油，下蒜末爆香。\n3. 放入蔬菜大火快炒。\n4. 加盐调味，断生即可出锅。";d.type=="fish"->"1. 鱼处理洗净并擦干。\n2. 姜葱去腥。\n3. 按菜名选择清蒸、红烧或水煮。\n4. 鱼肉完全熟透后出锅。";d.type=="shrimp"->"1. 虾去虾线洗净。\n2. 姜葱去腥。\n3. 大火快速炒至变色并完全熟透。\n4. 少盐调味即可。";d.type=="soup"->"1. 食材洗净切块。\n2. 冷水或热水下锅。\n3. 小火煮至食材熟透。\n4. 撇去浮沫，最后少盐调味。";else->"1. 肉类切片或切块，加入少量生抽腌制。\n2. 热锅少油，先将肉类炒至变色。\n3. 加入${d.ingredient}及葱姜蒜翻炒或焖炖。\n4. 根据菜名加入调味料。\n5. 确保肉类完全熟透后出锅。"}

    private fun libraryCount():Int{return pork.size*porkMethods.size+beef.size*beefMethods.size+lamb.size*lambMethods.size+chicken.size*chickenMethods.size+duck.size*duckMethods.size+fish.size*fishMethods.size+shrimp.size*shrimpMethods.size+egg.size*eggMethods.size+veg.size*vegMethods.size+bean.size+600}
    private fun proteinName(type:String)=when(type){"pork"->"猪肉";"beef"->"牛肉";"lamb"->"羊肉";"chicken"->"鸡肉";"duck"->"鸭肉";"fish"->"鱼";else->"虾"}
    private fun typeLabel(type:String)=when(type){"pork"->"猪肉主菜";"beef"->"牛肉";"lamb"->"羊肉";"chicken"->"鸡肉";"duck"->"鸭肉";"fish"->"鱼类";"shrimp"->"虾类";"egg"->"蛋类";"bean"->"豆制品";"green"->"时令蔬菜";"soup"->"汤品";else->"中式融合"}
    private fun seasonName()=when(month()){3,4,5->"春季";6,7,8->"夏季";9,10,11->"秋季";else->"冬季"}
    private fun month()=Calendar.getInstance().get(Calendar.MONTH)+1

    private fun page()=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(16),dp(8),dp(16),dp(18))}
    private fun card(parent:LinearLayout,title:String,text:String){val c=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(14),dp(12),dp(14),dp(12));setBackgroundColor(Color.WHITE)};addTextTo(c,title,16f,Color.rgb(35,90,50),Gravity.LEFT,0,0,0,5);addTextTo(c,text,13f,Color.DKGRAY,Gravity.LEFT,0,0,0,0);parent.addView(c,LinearLayout.LayoutParams(-1,-2).apply{setMargins(0,dp(6),0,0)})}
    private fun addDishCard(parent:LinearLayout,d:Dish,label:String){val c=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;gravity=Gravity.CENTER_VERTICAL;setPadding(dp(8),dp(8),dp(8),dp(8));setBackgroundColor(Color.WHITE);setOnClickListener{showDetail(d)}};c.addView(DishArtView(this,d),LinearLayout.LayoutParams(dp(96),dp(80)));val r=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(10),0,0,0)};addTextTo(r,d.name,17f,Color.rgb(35,35,35),Gravity.LEFT,0,0,0,4);addTextTo(r,label,12f,Color.rgb(60,145,80),Gravity.LEFT,0,0,0,3);addTextTo(r,"蛋白质 ${if(d.protein>=4)"较高" else "适中"} · 点开看做法  ›",12f,Color.GRAY,Gravity.LEFT,0,0,0,0);c.addView(r,LinearLayout.LayoutParams(0,-2,1f));parent.addView(c,LinearLayout.LayoutParams(-1,dp(94)).apply{setMargins(0,dp(4),0,0)})}
    private fun addTextTo(parent:LinearLayout,text:String,size:Float,color:Int,gravity:Int,l:Int,t:Int,r:Int,b:Int):TextView{return TextView(this).apply{this.text=text;textSize=size;setTextColor(color);this.gravity=gravity;setPadding(dp(l),dp(t),dp(r),dp(b));parent.addView(this)}}

    private fun bottomNav():LinearLayout{val n=LinearLayout(this).apply{orientation=LinearLayout.HORIZONTAL;gravity=Gravity.CENTER;setBackgroundColor(Color.WHITE)};listOf("⌂\n今日午餐","▣\n菜谱","☆\n收藏","♙\n我的").forEachIndexed{i,text->n.addView(TextView(this).apply{this.text=text;gravity=Gravity.CENTER;textSize=12f;setTextColor(if(i==tab)Color.rgb(40,150,75) else Color.GRAY);setOnClickListener{when(i){0->showHome();1->showRecipes();2->showFavorites();else->showMine()}}},LinearLayout.LayoutParams(0,-1,1f))};return n}
    private fun dp(v:Int)=(v*resources.displayMetrics.density).roundToInt()

    class PlateView(context:android.content.Context):View(context){private val p=Paint(Paint.ANTI_ALIAS_FLAG);override fun onDraw(canvas:Canvas){p.color=Color.WHITE;canvas.drawRoundRect(RectF(24f,8f,width-24f,height-8f),34f,34f,p);p.color=Color.rgb(225,240,218);canvas.drawCircle(width/2f,height/2f,82f,p);p.color=Color.rgb(70,155,82);canvas.drawCircle(width/2f-62f,height/2f-10f,34f,p);p.color=Color.rgb(185,92,63);canvas.drawCircle(width/2f+58f,height/2f-10f,36f,p);p.color=Color.rgb(235,178,62);canvas.drawCircle(width/2f,height/2f+55f,30f,p);p.color=Color.rgb(78,142,175);canvas.drawCircle(width/2f+4f,height/2f-68f,27f,p)}}
    class DishArtView(context:android.content.Context,private val dish:Dish):View(context){private val p=Paint(Paint.ANTI_ALIAS_FLAG);override fun onDraw(canvas:Canvas){p.color=Color.rgb(245,238,220);canvas.drawRoundRect(RectF(2f,2f,width-2f,height-2f),20f,20f,p);p.color=when(dish.type){"green"->Color.rgb(75,165,80);"pork"->Color.rgb(190,100,70);"beef","lamb"->Color.rgb(150,70,55);"chicken","duck"->Color.rgb(205,142,64);"fish","shrimp"->Color.rgb(75,145,175);"bean","egg"->Color.rgb(215,170,70);"soup"->Color.rgb(150,175,110);else->Color.rgb(120,155,105)};canvas.drawCircle(width/2f,height/2f,minOf(width,height)*0.30f,p);p.color=Color.WHITE;canvas.drawCircle(width/2f,height/2f,minOf(width,height)*0.15f,p)}}
}
