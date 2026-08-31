package com.fuchuanboy.todaylunch

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import kotlin.random.Random

class FinalActivityFixed : Activity() {
    data class Recipe(val name: String, val group: String, val vegetables: List<String>, val ingredients: String, val steps: String)
    data class Meal(val animal: Recipe, val plant: Recipe, val leaf: Recipe, val vitamin: Recipe, val soup: Recipe, val score: Int)

    private val animal = listOf(
        Recipe("鱼香肉丝", "动物蛋白", listOf("木耳","胡萝卜","青椒"), "猪里脊、木耳、胡萝卜、青椒、泡椒、葱姜蒜、醋、生抽、糖、淀粉", "里脊切丝腌10分钟；木耳、胡萝卜、青椒切丝并调鱼香汁；肉丝滑炒盛出；爆香葱姜蒜和泡椒，下蔬菜，回锅肉丝并倒入鱼香汁，大火收汁。"),
        Recipe("洋葱肥牛", "动物蛋白", listOf("洋葱","彩椒"), "肥牛片、洋葱、彩椒、蚝油、生抽、黑胡椒", "洋葱和彩椒切块；肥牛快速焯水；炒香洋葱；加入肥牛和彩椒，调味后大火翻匀。"),
        Recipe("照烧鸡腿", "动物蛋白", listOf("鸡腿","芝麻"), "去骨鸡腿、照烧汁、芝麻、盐", "鸡腿擦干腌制；鸡皮朝下煎金黄后翻面；加入照烧汁小火收浓；切块装盘撒芝麻。"),
        Recipe("孜然羊肉", "动物蛋白", listOf("洋葱","香菜"), "羊肉片、洋葱、香菜、孜然、辣椒面、生抽", "羊肉擦干；大火炒至变色；加入洋葱；撒孜然和辣椒面，最后放香菜。"),
        Recipe("番茄炖牛腩", "动物蛋白", listOf("番茄","洋葱"), "牛腩、番茄、洋葱、姜、八角、生抽", "牛腩焯水；番茄炒出汁；加入牛腩和香料，加水炖软；最后调味。"),
        Recipe("清蒸鲈鱼", "动物蛋白", listOf("葱","姜"), "鲈鱼、葱、姜、蒸鱼豉油、食用油", "鱼处理干净划刀；水开后蒸8至10分钟；倒掉蒸汁铺葱丝；淋蒸鱼豉油和热油。"),
        Recipe("蒜蓉虾", "动物蛋白", listOf("粉丝","小米椒"), "鲜虾、粉丝、大蒜、小米椒、生抽、蚝油", "粉丝泡软铺盘；虾开背去虾线；蒜末炒香铺在虾上；水开蒸8分钟，淋生抽和热油。"),
        Recipe("咖喱鸡肉饭", "动物蛋白", listOf("土豆","胡萝卜","洋葱"), "鸡腿肉、土豆、胡萝卜、洋葱、咖喱块、米饭", "鸡肉和蔬菜切块；炒香洋葱和鸡肉；加入土豆胡萝卜和水煮软；小火融化咖喱块，浇在米饭上。")
    )
    private val plant = listOf(
        Recipe("家常豆腐", "豆制品", listOf("木耳","青椒"), "老豆腐、木耳、青椒、葱姜蒜、生抽、蚝油", "豆腐切片煎至两面金黄；木耳泡发；爆香葱姜蒜，下木耳青椒；放豆腐和调味料，加少量水焖入味。"),
        Recipe("香煎豆腐", "豆制品", listOf("葱","芝麻"), "老豆腐、生抽、芝麻、葱花、蒜", "豆腐切厚片擦干；少油煎至两面金黄；加生抽和少量水焖1分钟；撒葱花芝麻。"),
        Recipe("芹菜香干", "豆制品", listOf("芹菜","红椒"), "香干、芹菜、红椒、蒜、生抽、盐", "香干切条，芹菜切段；香干煎香；加入芹菜红椒快速翻炒；调味至断生。"),
        Recipe("番茄豆腐", "豆制品", listOf("番茄","葱"), "嫩豆腐、番茄、葱、盐、生抽", "番茄切块，豆腐切块；番茄炒出汁；加入豆腐和少量水炖5分钟；调味撒葱花。"),
        Recipe("木耳炒豆皮", "豆制品", listOf("木耳","彩椒"), "豆腐皮、木耳、彩椒、蒜、生抽", "豆腐皮切条焯水；木耳泡发；蒜爆香后加入豆腐皮和木耳；最后放彩椒翻炒。")
    )
    private val leaf = listOf(
        Recipe("蒜蓉上海青", "绿叶青菜", listOf("上海青"), "上海青、大蒜、盐、食用油", "上海青洗净沥干；蒜末爆香；大火下青菜快速翻炒；调盐立即出锅。"),
        Recipe("蒜蓉菠菜", "绿叶青菜", listOf("菠菜"), "菠菜、大蒜、盐、食用油", "菠菜洗净切段；蒜末爆香；加入菠菜大火翻炒至断生；调盐出锅。"),
        Recipe("蒜蓉油麦菜", "绿叶青菜", listOf("油麦菜"), "油麦菜、大蒜、蚝油、盐", "油麦菜洗净切段；蒜末爆香；大火翻炒油麦菜；加入蚝油和少量盐。"),
        Recipe("清炒小白菜", "绿叶青菜", listOf("小白菜"), "小白菜、蒜、盐、食用油", "小白菜洗净切段；蒜片爆香；大火炒至断生；调盐翻匀。"),
        Recipe("蒜蓉空心菜", "绿叶青菜", listOf("空心菜"), "空心菜、大蒜、盐、食用油", "空心菜洗净切段；蒜末爆香；大火快速翻炒；加盐立即出锅。")
    )
    private val vitamin = listOf(
        Recipe("番茄炒蛋", "维生素轮换", listOf("番茄"), "番茄、鸡蛋、葱、盐、油", "鸡蛋炒熟盛出；番茄炒出汁；加入鸡蛋翻匀；调盐撒葱花。"),
        Recipe("彩椒炒玉米", "维生素轮换", listOf("彩椒","玉米"), "彩椒、甜玉米、胡萝卜、盐、食用油", "彩椒和胡萝卜切丁；玉米焯熟；热锅翻炒全部食材；调盐出锅。"),
        Recipe("蒜香西兰花", "维生素轮换", listOf("西兰花","胡萝卜"), "西兰花、胡萝卜、大蒜、盐、生抽", "西兰花和胡萝卜焯水；蒜末爆香；加入蔬菜快速翻炒；调味出锅。"),
        Recipe("醋溜土豆丝", "维生素轮换", listOf("土豆","青椒"), "土豆、青椒、醋、蒜、盐", "土豆切丝洗去淀粉；蒜爆香；大火炒土豆丝和青椒；沿锅边淋醋调味。"),
        Recipe("凉拌紫甘蓝", "维生素轮换", listOf("紫甘蓝","胡萝卜"), "紫甘蓝、胡萝卜、醋、生抽、芝麻油", "紫甘蓝和胡萝卜切丝；加少量盐抓匀；调入醋、生抽和芝麻油拌匀。")
    )
    private val soups = listOf(
        Recipe("冬瓜排骨汤", "汤", listOf("冬瓜"), "排骨、冬瓜、姜、葱、盐", "排骨焯水；加姜和水小火炖40分钟；加入冬瓜再炖15分钟；调盐撒葱。"),
        Recipe("番茄蛋花汤", "汤", listOf("番茄"), "番茄、鸡蛋、葱、盐、香油", "番茄炒软加水煮开；鸡蛋打散淋入；调盐，关火滴香油撒葱。"),
        Recipe("紫菜豆腐汤", "汤", listOf("紫菜","豆腐"), "紫菜、嫩豆腐、鸡蛋、葱、盐", "豆腐切块入沸水；放紫菜；淋蛋液；调盐撒葱。"),
        Recipe("菌菇鸡汤", "汤", listOf("菌菇"), "鸡腿、香菇、金针菇、姜、盐", "鸡肉焯水；加姜和水炖30分钟；加入菌菇再炖10分钟；调盐。")
    )

    private fun generateMeal(): Meal {
        repeat(100) {
            val m = animal.random(); val p = plant.random(); val l = leaf.random(); val v = vitamin.random(); val s = soups.random()
            val vegetables = (m.vegetables + p.vegetables + l.vegetables + v.vegetables).filter { it != "葱" && it != "姜" }.map { it.trim() }
            if (vegetables.distinct().size == vegetables.size) return Meal(m,p,l,v,s,94)
        }
        return Meal(animal.random(), plant.random(), leaf.random(), vitamin.random(), soups.random(), 90)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showMeal(generateMeal())
    }

    private fun showMeal(meal: Meal) {
        val root = LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setBackgroundColor(Color.rgb(248,246,241)); setPadding(24,24,24,24) }
        val title = TextView(this).apply { text="今天中午吃什么"; textSize=28f; gravity=Gravity.CENTER; setTextColor(Color.rgb(55,45,35)) }
        root.addView(title, LinearLayout.LayoutParams(-1,70))
        val score = TextView(this).apply { text="营养搭配 ${meal.score} 分  ·  四菜一汤"; textSize=16f; gravity=Gravity.CENTER; setTextColor(Color.rgb(50,120,70)); setPadding(0,0,0,16) }
        root.addView(score)
        val scroll=ScrollView(this)
        val list=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL}
        val dishes=listOf(meal.animal,meal.plant,meal.leaf,meal.vitamin,meal.soup)
        dishes.forEachIndexed { i,d ->
            val card=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(18,16,18,16);setBackgroundColor(Color.WHITE)}
            val h=TextView(this).apply{text="${i+1}. ${d.name}";textSize=21f;setTextColor(Color.rgb(55,45,35))}
            card.addView(h)
            val g=TextView(this).apply{text=d.group; textSize=14f; setTextColor(Color.rgb(130,100,60)); setPadding(0,6,0,6)}
            card.addView(g)
            val ing=TextView(this).apply{text="食材：${d.ingredients}";textSize=14f;setTextColor(Color.DKGRAY);setPadding(0,4,0,4)}
            card.addView(ing)
            val step=TextView(this).apply{text="做法：${d.steps}";textSize=14f;setTextColor(Color.DKGRAY);setPadding(0,4,0,10)}
            card.addView(step)
            val lp=LinearLayout.LayoutParams(-1,-2);lp.setMargins(0,0,0,14);list.addView(card,lp)
        }
        scroll.addView(list);root.addView(scroll,LinearLayout.LayoutParams(-1,0,1f))
        val refresh=Button(this).apply{text="换一桌";textSize=18f;setOnClickListener{showMeal(generateMeal())}}
        root.addView(refresh,LinearLayout.LayoutParams(-1,60))
        setContentView(root)
    }
}
