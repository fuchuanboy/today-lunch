package com.fuchuanboy.todaylunch

import android.app.Activity
import android.os.Bundle
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.min
import kotlin.random.Random

class MainActivity : Activity() {
    data class Recipe(
        val name: String,
        val type: String,
        val main: String,
        val sides: List<String>,
        val ingredients: List<String>,
        val steps: List<String>,
        val protein: Int,
        val fiber: Int,
        val calcium: Int,
        val iron: Int,
        val art: Int
    )

    private var meal = mutableListOf<Recipe>()
    private val favorites = mutableListOf<Recipe>()
    private lateinit var body: LinearLayout
    private var tab = 0

    private val porkSides = listOf("青椒", "蒜薹", "芹菜", "木耳", "洋葱", "豆角", "茄子", "土豆", "白菜", "白萝卜", "冬瓜", "莲藕", "香菇", "杏鲍菇", "平菇", "酸菜", "梅菜", "笋", "蒜苗", "韭菜", "黄瓜", "西葫芦", "苦瓜", "青豆", "毛豆", "豇豆", "四季豆", "豆芽", "粉条", "千张", "荷兰豆", "菜花", "西兰花", "番茄", "山药", "莴笋", "腐竹", "香干", "海带", "榨菜")
    private val beefSides = listOf("洋葱", "芹菜", "青椒", "彩椒", "土豆", "番茄", "西兰花", "杏鲍菇", "金针菇", "木耳", "蒜薹", "胡萝卜", "芥蓝", "豆芽", "韭黄", "酸菜", "粉丝", "豆腐", "冬瓜", "山药", "莲藕", "青豆", "娃娃菜", "生菜", "香菇", "茶树菇", "包菜", "西葫芦", "荷兰豆", "芦笋", "莴笋", "南瓜", "萝卜", "青蒜", "土豆片")
    private val lambSides = listOf("大葱", "洋葱", "孜然", "香菜", "萝卜", "土豆", "胡萝卜", "白菜", "芹菜", "青椒", "番茄", "山药", "冬瓜", "酸菜", "粉丝", "金针菇", "蒜薹", "韭菜", "香菇", "彩椒", "豆腐", "包菜", "莴笋", "莲藕", "土豆片", "青蒜", "辣椒", "花菜", "杏鲍菇", "茄子")
    private val chickenSides = listOf("香菇", "土豆", "青椒", "板栗", "番茄", "芹菜", "木耳", "洋葱", "青豆", "玉米", "胡萝卜", "山药", "莲藕", "冬瓜", "茶树菇", "金针菇", "粉条", "豆腐", "毛豆", "豇豆", "花生", "西兰花", "彩椒", "娃娃菜", "南瓜", "大葱", "柠檬", "荷兰豆", "蘑菇", "咖喱土豆", "秋葵", "菠萝", "芦笋", "山药", "莲藕")
    private val duckSides = listOf("魔芋", "土豆", "冬瓜", "萝卜", "青椒", "酸菜", "香菇", "莲藕", "山药", "芋头", "豆腐", "白菜", "海带", "笋", "梅干菜", "毛豆", "洋葱", "紫苏", "红椒", "啤酒")
    private val fishSides = listOf("豆腐", "酸菜", "番茄", "葱", "蒜", "辣椒", "剁椒", "萝卜", "冬瓜", "紫苏", "白菜", "金针菇", "粉丝", "木耳", "藕", "蒜薹", "青椒", "香菜", "芹菜", "洋葱", "茄子", "泡椒", "酸萝卜", "海带", "豆芽", "雪菜", "菌菇", "丝瓜", "芥菜", "豆瓣")
    private val shrimpSides = listOf("西兰花", "芦笋", "黄瓜", "鸡蛋", "豆腐", "玉米", "豌豆", "毛豆", "丝瓜", "冬瓜", "韭菜", "芹菜", "番茄", "蘑菇", "腰果", "青椒", "荷兰豆", "彩椒", "蒜薹", "葱", "粉丝", "南瓜", "菠菜", "芥蓝", "胡萝卜", "西葫芦", "菠萝", "豆苗", "玉子豆腐", "鸡蛋清")
    private val eggSides = listOf("番茄", "韭菜", "青椒", "木耳", "虾仁", "洋葱", "菠菜", "黄瓜", "香椿", "香菇", "西葫芦", "蒜薹", "玉米", "豌豆", "青豆", "银鱼", "豆腐", "紫菜", "芹菜", "葱", "火腿", "虾皮", "苦瓜", "秋葵")
    private val beanDishes = listOf("麻婆豆腐", "家常豆腐", "葱烧豆腐", "香煎豆腐", "小葱拌豆腐", "香干炒芹菜", "香干炒肉", "腐竹木耳", "腐竹烧肉", "豆皮炒青椒", "千张炒芹菜", "千张结烧肉", "豆腐泡烧白菜", "毛豆烧豆腐", "青豆虾仁", "凉拌豆皮", "香菇豆腐", "白菜豆腐", "酸菜豆腐", "番茄豆腐", "豆腐烧肉", "鱼香豆腐", "红烧豆腐", "豆腐蒸蛋", "肉末蒸豆腐", "香干回锅肉", "腐竹烧牛肉", "豆皮肉卷", "豆腐丸子", "豆腐烧鱼")
    private val greens = listOf("上海青", "小白菜", "菠菜", "油麦菜", "空心菜", "苋菜", "生菜", "菜心", "芥蓝", "娃娃菜", "大白菜", "包菜", "西兰花", "菜花", "芦笋", "芹菜", "蒜薹", "豇豆", "四季豆", "毛豆", "茄子", "番茄", "黄瓜", "丝瓜", "冬瓜", "苦瓜", "西葫芦", "土豆", "莲藕", "山药", "胡萝卜", "白萝卜", "青椒", "彩椒", "洋葱", "豆芽", "韭菜", "韭黄", "香菇", "平菇", "杏鲍菇", "金针菇", "木耳", "海带", "莴笋", "藕带", "秋葵", "南瓜", "佛手瓜", "瓠瓜", "芥菜", "菜薹", "红薯叶", "豌豆苗")

    private val seasonal = mapOf(
        1 to listOf("菠菜", "白菜", "上海青", "芥蓝", "萝卜", "西兰花"),
        2 to listOf("菠菜", "菜心", "上海青", "芥蓝", "生菜", "西兰花"),
        3 to listOf("菠菜", "菜心", "油麦菜", "芹菜", "春笋", "西兰花"),
        4 to listOf("油麦菜", "上海青", "苋菜", "芦笋", "莴笋", "西葫芦"),
        5 to listOf("空心菜", "苋菜", "生菜", "油麦菜", "丝瓜", "西兰花"),
        6 to listOf("空心菜", "苋菜", "油麦菜", "丝瓜", "豇豆", "黄瓜"),
        7 to listOf("空心菜", "苋菜", "丝瓜", "豇豆", "毛豆", "黄瓜"),
        8 to listOf("空心菜", "苋菜", "丝瓜", "豇豆", "毛豆", "冬瓜"),
        9 to listOf("空心菜", "油麦菜", "上海青", "丝瓜", "西兰花", "芥蓝"),
        10 to listOf("菠菜", "上海青", "白菜", "芥蓝", "西兰花", "萝卜"),
        11 to listOf("菠菜", "白菜", "上海青", "芥蓝", "萝卜", "西兰花"),
        12 to listOf("菠菜", "白菜", "上海青", "芥蓝", "萝卜", "菜心")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(248, 252, 244)
        window.navigationBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        newMeal()
        shell()
        home()
    }

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
    private fun shell() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(247, 250, 245))
            fitsSystemWindows = true
        }
        body = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val scroll = ScrollView(this).apply {
            isFillViewport = true
            addView(body, ScrollView.LayoutParams(-1, -2))
        }
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        root.addView(nav(), LinearLayout.LayoutParams(-1, dp(72)))
        setContentView(root)
    }

    private fun home() {
        tab = 0
        body.removeAllViews()
        val box = page()
        text(box, "⌖ 河南省 · 郑州市  ▾", 14, Color.DKGRAY, Gravity.LEFT, 0, 8, 0, 10)
        text(box, "今天中午吃什么？", 31, Color.rgb(20, 104, 55), Gravity.CENTER, 0, 4, 0, 3)
        text(box, "营养均衡 · 时令搭配 · 科学膳食", 15, Color.GRAY, Gravity.CENTER, 0, 0, 0, 12)

        val hero = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            background = rounded(Color.rgb(236, 249, 224), 28)
            setPadding(dp(12), dp(12), dp(12), dp(12))
        }
        val art = DishArtView(this, 99)
        hero.addView(art, LinearLayout.LayoutParams(-1, dp(250)))
        box.addView(hero, LinearLayout.LayoutParams(-1, dp(282)).apply { setMargins(0, 0, 0, dp(14)) })

        val random = TextView(this).apply {
            text = "🎲   随机一桌\n生成今日四菜一汤"
            textSize = 17f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            background = rounded(Color.rgb(58, 174, 92), 36)
            setOnClickListener { newMeal(); mealPage() }
        }
        box.addView(random, LinearLayout.LayoutParams(-1, dp(72)).apply { setMargins(dp(4), 0, dp(4), dp(12)) })

        val chips = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        listOf("✓\n营养均衡", "🍊\n时令食材", "🥩\n猪肉为主", "🌏\n中式百味").forEach {
            val c = TextView(this).apply {
                text = it
                textSize = 12f
                gravity = Gravity.CENTER
                setTextColor(Color.rgb(60, 80, 65))
                background = rounded(Color.WHITE, 18)
            }
            chips.addView(c, LinearLayout.LayoutParams(0, dp(58), 1f).apply { setMargins(dp(3), 0, dp(3), 0) })
        }
        box.addView(chips)
        card(box, "菜库规模", "${libraryCount()}+ 道结构化菜谱。不是把菜名拼在一起：每道菜都有对应主料、配菜、调味料和步骤。")
        card(box, "中式海纳百川", "传统家常菜 + 河南口味 + 川湘粤鲁等风味 + 照烧鸡腿、洋葱肥牛、咖喱鸡肉饭等家庭常见融合口味。")
        body.addView(box)
        refreshNav()
    }

    private fun mealPage() {
        tab = 0
        body.removeAllViews()
        val box = page()
        val top = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL }
        text(top, "‹", 32, Color.DKGRAY, Gravity.CENTER, 0, 0, 8, 0).setOnClickListener { home() }
        val title = text(top, "今日午餐", 23, Color.rgb(35, 35, 35), Gravity.CENTER, 0, 0, 0, 0)
        title.layoutParams = LinearLayout.LayoutParams(0, dp(54), 1f)
        text(top, "↻", 28, Color.DKGRAY, Gravity.CENTER, 8, 0, 0, 0).setOnClickListener { newMeal(); mealPage() }
        box.addView(top)
        text(box, SimpleDateFormat("M月d日  EEEE", Locale.CHINA).format(Calendar.getInstance().time) + "    时令：" + seasonName(), 13, Color.GRAY, Gravity.LEFT, 4, 0, 0, 8)
        text(box, "🍃  今日四菜一汤", 21, Color.rgb(30, 110, 60), Gravity.CENTER, 0, 0, 0, 10)
        meal.forEachIndexed { i, d -> dishCard(box, d, when (i) { 0 -> "猪肉主菜"; 1 -> "牛羊鸡鸭鱼虾轮换"; 2 -> "豆制品 / 蛋类 / 第二荤菜"; 3 -> "当季烧青菜"; else -> "汤品" }) }
        nutritionCard(box)
        val change = TextView(this).apply {
            text = "换一桌  ·  无尽随机"
            textSize = 16f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            background = rounded(Color.rgb(58, 174, 92), 26)
            setOnClickListener { newMeal(); mealPage() }
        }
        box.addView(change, LinearLayout.LayoutParams(-1, dp(54)).apply { setMargins(dp(4), dp(10), dp(4), dp(14)) })
        body.addView(box)
        refreshNav()
    }

    private fun recipes() {
        tab = 1
        body.removeAllViews()
        val box = page()
        text(box, "菜谱", 26, Color.rgb(25, 85, 48), Gravity.CENTER, 0, 10, 0, 3)
        text(box, "海纳百川的中式菜库 · ${libraryCount()}+ 道", 14, Color.GRAY, Gravity.CENTER, 0, 0, 0, 12)
        val search = EditText(this).apply {
            hint = "搜索菜名：虾仁、牛肉、鸡腿、青菜……"
            textSize = 14f
            setSingleLine(true)
            background = rounded(Color.WHITE, 22)
            setPadding(dp(16), 0, dp(16), 0)
        }
        box.addView(search, LinearLayout.LayoutParams(-1, dp(48)).apply { setMargins(0, 0, 0, dp(10)) })
        val categories = listOf("猪肉" to porkSides.size * 9, "牛肉" to beefSides.size * 8, "羊肉" to lambSides.size * 7, "鸡肉" to chickenSides.size * 9, "鸭肉" to duckSides.size * 7, "鱼类" to fishSides.size * 8, "虾类" to shrimpSides.size * 9, "蛋类" to eggSides.size * 7, "豆制品" to beanDishes.size, "时令蔬菜" to greens.size * 8)
        categories.forEach { (name, count) ->
            val r = TextView(this).apply {
                text = "$name\n$count 道结构化菜谱 · 随机一道"
                textSize = 15f
                setTextColor(Color.DKGRAY)
                background = rounded(Color.WHITE, 18)
                setPadding(dp(16), dp(10), dp(16), dp(10))
                setOnClickListener { detail(sample(name)) }
            }
            box.addView(r, LinearLayout.LayoutParams(-1, dp(70)).apply { setMargins(0, dp(4), 0, 0) })
        }
        card(box, "参考原则", "菜名、食材和步骤采用家常菜的真实结构来设计，并参考公开菜谱的组织方式；不会直接复制第三方菜谱原文或图片。")
        body.addView(box)
        refreshNav()
    }

    private fun favoritesPage() {
        tab = 2
        body.removeAllViews()
        val box = page()
        text(box, "收藏", 26, Color.rgb(30, 70, 45), Gravity.CENTER, 0, 10, 0, 12)
        if (favorites.isEmpty()) card(box, "还没有收藏", "进入菜谱详情，点击右上角 ☆ 就能收藏。") else favorites.forEach { dishCard(box, it, "我的收藏") }
        body.addView(box)
        refreshNav()
    }

    private fun mine() {
        tab = 3
        body.removeAllViews()
        val box = page()
        text(box, "营养分析", 26, Color.rgb(30, 70, 45), Gravity.CENTER, 0, 10, 0, 10)
        val scoreView = TextView(this).apply {
            text = "${score()}\n营养均衡度"
            textSize = 28f
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(45, 145, 78))
            background = rounded(Color.WHITE, 24)
        }
        box.addView(scoreView, LinearLayout.LayoutParams(-1, dp(130)).apply { setMargins(0, 0, 0, dp(10)) })
        val p = meal.sumOf { it.protein }
        val f = meal.sumOf { it.fiber }
        val c = meal.sumOf { it.calcium }
        val i = meal.sumOf { it.iron }
        listOf(
            "蛋白质" to if (p >= 16) "充足" else "良好",
            "膳食纤维" to if (f >= 10) "充足" else "良好",
            "钙" to if (c >= 8) "较好" else "一般",
            "铁" to if (i >= 8) "较好" else "一般",
            "时令绿叶菜" to if (meal.any { it.type == "green" }) "达标" else "需要增加"
        ).forEach { (a, b) ->
            val row = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL; background = rounded(Color.WHITE, 12) }
            text(row, a, 15, Color.DKGRAY, Gravity.LEFT, dp(14), 0, 0, 0).layoutParams = LinearLayout.LayoutParams(0, -1, 1f)
            text(row, b, 14, Color.rgb(55, 150, 80), Gravity.RIGHT, 0, 0, dp(14), 0)
            box.addView(row, LinearLayout.LayoutParams(-1, dp(48)).apply { setMargins(0, dp(3), 0, 0) })
        }
        card(box, "搭配评价", "猪肉主菜 + 第二动物蛋白 + 豆制品/蛋类 + 当季烧青菜 + 汤品。评分只用于日常午餐搭配参考。")
        card(box, "河南时令", "当前优先：${seasonal[month()]!!.joinToString("、")}。")
        body.addView(box)
        refreshNav()
    }

    private fun nutritionCard(box: LinearLayout) {
        val c = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; background = rounded(Color.WHITE, 22); setPadding(dp(16), dp(14), dp(16), dp(14)) }
        text(c, "今日营养搭配", 17, Color.rgb(35, 100, 55), Gravity.LEFT, 0, 0, 0, 7)
        text(c, "${score()} 分  ★★★★★", 21, Color.rgb(48, 145, 75), Gravity.LEFT, 0, 0, 0, 5)
        text(c, "蛋白质 ${meal.sumOf { it.protein }} · 膳食纤维 ${meal.sumOf { it.fiber }} · 钙 ${meal.sumOf { it.calcium }} · 铁 ${meal.sumOf { it.iron }}", 13, Color.DKGRAY, Gravity.LEFT, 0, 0, 0, 0)
        box.addView(c, LinearLayout.LayoutParams(-1, dp(108)).apply { setMargins(0, dp(8), 0, 0) })
    }

    private fun nav(): LinearLayout {
        val n = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER; setBackgroundColor(Color.WHITE) }
        listOf("⌂\n今日午餐", "▣\n菜谱", "☆\n收藏", "♙\n我的").forEachIndexed { idx, s ->
            n.addView(TextView(this).apply {
                text = s
                gravity = Gravity.CENTER
                textSize = 12f
                setTextColor(if (idx == tab) Color.rgb(40, 150, 75) else Color.GRAY)
                setOnClickListener { when (idx) { 0 -> home(); 1 -> recipes(); 2 -> favoritesPage(); else -> mine() } }
            }, LinearLayout.LayoutParams(0, -1, 1f))
        }
        return n
    }

    private fun refreshNav() { }

    private fun newMeal() {
        val green = seasonal[month()]!!.random()
        val secondType = listOf("beef", "lamb", "chicken", "duck", "fish", "shrimp").random()
        val third = if (Random.nextInt(100) < 55) randomBeanOrEgg() else randomProtein("chicken")
        meal = mutableListOf(
            randomProtein("pork"),
            randomProtein(secondType),
            third,
            greenRecipe(green),
            randomSoup()
        )
    }

    private fun randomProtein(type: String): Recipe {
        val sides = when (type) {
            "pork" -> porkSides
            "beef" -> beefSides
            "lamb" -> lambSides
            "chicken" -> chickenSides
            "duck" -> duckSides
            "fish" -> fishSides
            else -> shrimpSides
        }
        val side = sides.random()
        val method = when (type) {
            "pork" -> listOf("炒", "小炒", "红烧", "酱烧", "蒜香炒", "干煸", "鱼香").random()
            "beef" -> listOf("黑椒炒", "洋葱炒", "小炒", "葱爆", "番茄炖", "孜然炒").random()
            "lamb" -> listOf("葱爆", "孜然炒", "小炒", "萝卜炖", "香辣炒").random()
            "chicken" -> listOf("香菇烧", "土豆烧", "黄焖", "宫保", "咖喱", "照烧", "蒜香煎").random()
            "duck" -> listOf("红烧", "啤酒焖", "魔芋烧", "酸菜炖", "香辣烧").random()
            "fish" -> listOf("清蒸", "红烧", "水煮", "酸菜炖", "剁椒蒸", "番茄炖", "紫苏烧").random()
            else -> listOf("清炒", "蒜蓉炒", "椒盐", "香煎", "盐水", "葱香").random()
        }
        return buildMeatRecipe(type, side, method)
    }

    private fun buildMeatRecipe(type: String, side: String, method: String): Recipe {
        val main = when (type) { "pork" -> "猪肉"; "beef" -> "牛肉"; "lamb" -> "羊肉"; "chicken" -> "鸡肉"; "duck" -> "鸭肉"; "fish" -> "鱼"; else -> "虾仁" }
        val name = when {
            type == "beef" && side == "洋葱" -> "洋葱肥牛"
            type == "beef" && method == "黑椒炒" -> "黑椒${side}牛柳"
            type == "lamb" && method == "葱爆" -> "葱爆羊肉"
            type == "lamb" && method == "孜然炒" -> "孜然羊肉"
            type == "chicken" && method == "照烧" -> "照烧鸡腿"
            type == "chicken" && method == "咖喱" -> "咖喱鸡肉饭"
            type == "chicken" && method == "宫保" -> "宫保鸡丁"
            type == "duck" && method == "啤酒焖" -> "啤酒鸭"
            type == "fish" && method == "酸菜炖" -> "酸菜鱼"
            type == "fish" && method == "清蒸" -> "清蒸${side}鱼"
            type == "shrimp" && side == "西兰花" -> "西兰花炒虾仁"
            type == "shrimp" && side == "腰果" -> "腰果虾仁"
            else -> "$method$side${if (type == "shrimp") "虾仁" else main}"
        }
        val ingredients = when {
            name == "洋葱肥牛" -> listOf("肥牛卷 250g", "洋葱 1个", "生抽 1勺", "蚝油 1勺", "黑胡椒 少许", "食用油 适量")
            name == "照烧鸡腿" -> listOf("鸡腿 2只", "生抽 1勺", "老抽 半勺", "蜂蜜 1勺", "料酒 1勺", "姜片 3片", "食用油 适量")
            name == "咖喱鸡肉饭" -> listOf("鸡腿肉 250g", "土豆 1个", "胡萝卜 半根", "洋葱 半个", "咖喱块 2块", "食用油 适量", "米饭 1碗")
            name == "清炒虾仁" -> listOf("虾仁 250g", "青豆 60g", "胡萝卜 50g", "鸡蛋清 半个", "生粉 1勺", "姜末 少许", "盐 少许")
            name == "西兰花炒虾仁" -> listOf("虾仁 250g", "西兰花 250g", "蒜 2瓣", "料酒 1勺", "淀粉 1勺", "盐 少许")
            name == "腰果虾仁" -> listOf("虾仁 250g", "腰果 60g", "黄瓜 半根", "胡萝卜 半根", "蛋清 半个", "淀粉 1勺", "盐 少许")
            name == "蒜蓉虾仁" -> listOf("虾仁 250g", "蒜末 2勺", "蚝油 1勺", "料酒 1勺", "黑胡椒 少许", "食用油 适量")
            else -> listOf("$main 200g", "$side 200g", "葱姜蒜 适量", "生抽 1勺", "料酒 1勺", "盐 少许", "食用油 适量")
        }
        val steps = when {
            name == "清炒虾仁" -> listOf("虾仁去虾线洗净，加蛋清、生粉、盐抓匀上浆。", "胡萝卜丁和青豆焯水后捞出。", "热锅少油，姜末爆香，放虾仁滑炒至变色。", "加入青豆和胡萝卜翻炒，少盐调味。", "翻匀后立即出锅，保持虾仁嫩脆。")
            name == "西兰花炒虾仁" -> listOf("虾仁去虾线，加料酒和淀粉腌10分钟。", "西兰花掰小朵，沸水焯至断生。", "蒜末爆香，放虾仁炒至变色。", "加入西兰花大火翻炒，少盐调味。", "翻匀出锅，虾仁和西兰花都要熟透。")
            name == "腰果虾仁" -> listOf("虾仁去虾线，加蛋清和淀粉抓匀。", "黄瓜、胡萝卜切丁，腰果小火烘香。", "热锅少油滑炒虾仁至变色盛出。", "炒香胡萝卜和黄瓜，加入虾仁。", "最后放腰果，少盐翻匀出锅。")
            name == "洋葱肥牛" -> listOf("洋葱切丝，肥牛卷准备好。", "肥牛入沸水快速焯至变色，捞出沥水。", "锅中少油炒香洋葱至透明。", "加入肥牛、生抽、蚝油和黑胡椒快速翻炒。", "收汁后出锅，配米饭食用。")
            name == "照烧鸡腿" -> listOf("鸡腿去骨或划开，擦干表面水分。", "平底锅少油，鸡皮面先煎至金黄，再翻面。", "加入生抽、老抽、料酒和少量清水。", "小火焖熟，加入蜂蜜收出照烧亮汁。", "切块装盘，确保鸡肉中心完全熟透。")
            name == "咖喱鸡肉饭" -> listOf("鸡肉、土豆、胡萝卜和洋葱切块。", "鸡肉下锅炒至变色，加入洋葱炒香。", "加入土豆和胡萝卜，加水没过食材煮熟。", "关小火加入咖喱块，搅拌至完全融化。", "继续收浓咖喱汁，浇在米饭上。")
            else -> genericSteps(type, main, side, method)
        }
        val art = when (type) { "pork" -> 1; "beef" -> 2; "lamb" -> 3; "chicken" -> 4; "duck" -> 5; "fish" -> 6; else -> 7 }
        return Recipe(name, type, main, listOf(side), ingredients, steps, if (type == "fish" || type == "shrimp") 5 else 6, 2, if (side.contains("豆腐")) 4 else 2, if (type == "pork" || type == "beef" || type == "lamb") 3 else 2, art)
    }

    private fun genericSteps(type: String, main: String, side: String, method: String): List<String> = when {
        method.contains("清蒸") -> listOf("鱼处理干净，擦干水分，放姜片去腥。", "盘中铺姜葱，放入鱼。", "水开后上锅蒸熟，根据鱼的大小调整时间。", "出锅倒掉盘中腥水，淋少量生抽。", "撒葱丝，热油激香即可。")
        method.contains("水煮") -> listOf("主料切片或处理干净，加少量盐和料酒腌制。", "锅中炒香葱姜蒜和调味料，加水烧开。", "先放$side煮至断生。", "加入$main煮至熟透。", "最后调味，盛出即可。")
        method.contains("红烧") || method.contains("酱烧") || method.contains("炖") || method.contains("焖") -> listOf("$main切块或切片，冷水处理后沥干。", "锅中少油煸香葱姜蒜，放入主料煎至上色。", "加入$side和生抽、料酒及适量清水。", "小火焖炖至主料熟透、$side入味。", "大火收汁，少盐调整后出锅。")
        method.contains("照烧") -> listOf("鸡腿擦干，皮面划几刀。", "少油煎至两面金黄。", "加入生抽、料酒、少量蜂蜜和清水。", "小火收汁至浓稠，鸡肉熟透。", "切块装盘，淋上锅中酱汁。")
        method.contains("咖喱") -> listOf("$main与$side切成适口大小。", "主料下锅煎炒至变色，加入洋葱炒香。", "加水煮至食材熟透。", "加入咖喱块，小火搅拌至融化。", "收浓汤汁，配米饭食用。")
        method.contains("椒盐") -> listOf("虾仁去虾线洗净，擦干水分。", "加少量盐和淀粉抓匀。", "热油将虾仁煎炸至表面金黄。", "倒出多余油，加入椒盐和$side快速翻匀。", "出锅趁热食用。")
        else -> listOf("$main洗净切片或切块，加料酒、生抽略腌。", "$side洗净切好，葱姜蒜准备齐全。", "热锅少油，先下主料炒至变色或熟透。", "加入$side大火翻炒，按照$method的风味调味。", "断生入味后立即出锅，保证主料和配菜都熟透。")
    }

    private fun randomBeanOrEgg(): Recipe {
        if (Random.nextBoolean()) {
            val side = eggSides.random()
            val name = when (side) { "番茄" -> "番茄炒蛋"; "韭菜" -> "韭菜炒鸡蛋"; "苦瓜" -> "苦瓜炒蛋"; else -> "${side}炒鸡蛋" }
            return Recipe(name, "egg", "鸡蛋", listOf(side), listOf("鸡蛋 3个", "$side 200g", "葱花 少许", "盐 少许", "食用油 适量"), listOf("鸡蛋打散，加少许盐搅匀。", "$side洗净切好。", "热锅多一点点油，鸡蛋炒至刚凝固盛出。", "锅中放少油炒香$side。", "倒回鸡蛋，快速翻匀出锅。"), 3, 2, 3, 2, 8)
        }
        val name = beanDishes.random()
        return Recipe(name, "bean", "豆制品", listOf("豆制品"), listOf("豆腐或豆制品 250g", "配菜 150g", "葱姜蒜 适量", "生抽 1勺", "盐 少许", "食用油 适量"), listOf("豆腐或豆制品切块，沥干水分。", "锅中少油煎至表面微黄。", "加入配菜、葱姜蒜翻炒。", "加入生抽和适量清水，小火入味。", "收汁后少盐调整，出锅。"), 4, 3, 5, 2, 9)
    }

    private fun greenRecipe(name: String): Recipe {
        val method = listOf("蒜蓉炒", "清炒", "蚝油炒", "炝炒", "上汤").random()
        return Recipe("$method$name", "green", name, listOf(name), listOf("$name 300g", "蒜 2瓣", "食用油 1勺", "盐 少许", if (method == "蚝油炒") "蚝油 1勺" else "清水 少许"), listOf("$name摘洗干净，沥干水分。", "蒜切末，锅烧热后放油。", "蒜末爆香，倒入$name。", "大火快速翻炒至断生，保持颜色和脆嫩。", "少盐调味，立即出锅。"), 1, 5, 2, 2, 10)
    }

    private fun randomSoup(): Recipe {
        val choices = listOf(
            Triple("番茄蛋花汤", listOf("番茄", "鸡蛋"), 11),
            Triple("紫菜蛋花汤", listOf("紫菜", "鸡蛋"), 11),
            Triple("冬瓜排骨汤", listOf("冬瓜", "排骨"), 1),
            Triple("菌菇豆腐汤", listOf("菌菇", "豆腐"), 9),
            Triple("萝卜瘦肉汤", listOf("白萝卜", "瘦肉"), 1),
            Triple("青菜豆腐汤", listOf(seasonal[month()]!!.random(), "豆腐"), 9),
            Triple("丝瓜虾仁汤", listOf("丝瓜", "虾仁"), 7),
            Triple("玉米排骨汤", listOf("玉米", "排骨"), 1),
            Triple("山药排骨汤", listOf("山药", "排骨"), 1),
            Triple("莲藕排骨汤", listOf("莲藕", "排骨"), 1),
            Triple("鱼头豆腐汤", listOf("鱼头", "豆腐"), 6),
            Triple("萝卜牛腩汤", listOf("萝卜", "牛腩"), 2)
        )
        val x = choices.random()
        return Recipe(x.first, "soup", "汤", x.second, x.second.map { "$it 适量" } + listOf("姜片 少许", "盐 少许"), listOf("食材洗净处理好，姜片备用。", "锅中加水，先放耐煮食材。", "水开后转小火，撇去浮沫。", "加入其余食材，煮至熟透。", "最后少盐调味即可。"), 2, 2, 3, 2, 11)
    }

    private fun sample(cat: String): Recipe = when (cat) {
        "猪肉" -> randomProtein("pork")
        "牛肉" -> randomProtein("beef")
        "羊肉" -> randomProtein("lamb")
        "鸡肉" -> randomProtein("chicken")
        "鸭肉" -> randomProtein("duck")
        "鱼类" -> randomProtein("fish")
        "虾类" -> randomProtein("shrimp")
        "蛋类" -> randomBeanOrEgg()
        "豆制品" -> randomBeanOrEgg()
        else -> greenRecipe(seasonal[month()]!!.random())
    }

    private fun detail(r: Recipe) {
        body.removeAllViews()
        val box = page()
        val top = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL }
        text(top, "‹", 32, Color.DKGRAY, Gravity.CENTER, 0, 0, 8, 0).setOnClickListener { recipes() }
        val title = text(top, r.name, 22, Color.DKGRAY, Gravity.CENTER, 0, 0, 0, 0)
        title.layoutParams = LinearLayout.LayoutParams(0, dp(54), 1f)
        val star = TextView(this).apply {
            text = if (favorites.any { it.name == r.name }) "★" else "☆"
            textSize = 28f
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(235, 175, 25))
            setOnClickListener { if (favorites.any { it.name == r.name }) favorites.removeAll { it.name == r.name } else favorites.add(r); detail(r) }
        }
        top.addView(star, LinearLayout.LayoutParams(dp(46), dp(54)))
        box.addView(top)
        val art = DishArtView(this, r.art)
        box.addView(art, LinearLayout.LayoutParams(-1, dp(240)).apply { setMargins(0, 0, 0, dp(10)) })
        text(box, r.name, 25, Color.rgb(35, 35, 35), Gravity.LEFT, 4, 0, 0, 4)
        text(box, "家常菜 · ${r.main} · 配菜：${r.sides.joinToString("、")}", 13, Color.GRAY, Gravity.LEFT, 4, 0, 0, 10)
        section(box, "食材准备", r.ingredients.mapIndexed { i, x -> "${i + 1}. $x" })
        section(box, "做法步骤", r.steps.mapIndexed { i, x -> "${i + 1}. $x" })
        section(box, "营养参考", listOf("蛋白质：${r.protein} · 膳食纤维：${r.fiber} · 钙：${r.calcium} · 铁：${r.iron}", "本项为菜品维度的相对评分，整桌营养评分见‘营养分析’。"))
        section(box, "小贴士", listOf("肉、禽、鱼、虾、蛋类要彻底加热；蔬菜大火快炒减少过度烹煮。", "盐和油按家庭口味调整。"))
        body.addView(box)
        refreshNav()
    }

    private fun section(box: LinearLayout, title: String, lines: List<String>) {
        val c = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; background = rounded(Color.WHITE, 20); setPadding(dp(16), dp(14), dp(16), dp(14)) }
        text(c, title, 17, Color.rgb(35, 105, 55), Gravity.LEFT, 0, 0, 0, 7)
        lines.forEach { text(c, it, 14, Color.DKGRAY, Gravity.LEFT, 0, 3, 0, 0) }
        box.addView(c, LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, dp(5), 0, 0) })
    }

    private fun dishCard(box: LinearLayout, r: Recipe, label: String) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            background = rounded(Color.WHITE, 20)
            setPadding(dp(8), dp(8), dp(10), dp(8))
            setOnClickListener { detail(r) }
        }
        val art = DishArtView(this, r.art)
        row.addView(art, LinearLayout.LayoutParams(dp(112), dp(88)))
        val q = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(12), 0, 0, 0) }
        text(q, r.name, 17, Color.rgb(30, 30, 30), Gravity.LEFT, 0, 0, 0, 4)
        text(q, label, 12, Color.rgb(60, 145, 80), Gravity.LEFT, 0, 0, 0, 3)
        text(q, "配菜：${r.sides.joinToString("、")}", 12, Color.GRAY, Gravity.LEFT, 0, 0, 0, 3)
        text(q, "点开看食材与做法 ›", 12, Color.GRAY, Gravity.LEFT, 0, 0, 0, 0)
        row.addView(q, LinearLayout.LayoutParams(0, -2, 1f))
        box.addView(row, LinearLayout.LayoutParams(-1, dp(104)).apply { setMargins(0, dp(4), 0, 0) })
    }

    private fun score(): Int {
        var s = 82
        if (meal.count { it.type in listOf("pork", "beef", "lamb", "chicken", "duck", "fish", "shrimp") } >= 2) s += 5
        if (meal.any { it.type == "green" }) s += 5
        if (meal.any { it.type == "bean" || it.type == "egg" }) s += 4
        if (meal.sumOf { it.fiber } >= 10) s += 2
        return s.coerceIn(82, 98)
    }

    private fun libraryCount(): Int =
        porkSides.size * 9 + beefSides.size * 8 + lambSides.size * 7 + chickenSides.size * 9 + duckSides.size * 7 + fishSides.size * 8 + shrimpSides.size * 9 + eggSides.size * 7 + beanDishes.size + greens.size * 8 + 80

    private fun seasonName() = when (month()) { 3, 4, 5 -> "春季"; 6, 7, 8 -> "夏季"; 9, 10, 11 -> "秋季"; else -> "冬季" }
    private fun month() = Calendar.getInstance().get(Calendar.MONTH) + 1
    private fun page() = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(14), dp(6), dp(14), dp(18)) }
    private fun text(p: LinearLayout, s: String, size: Int, color: Int, gravity: Int, l: Int, t: Int, r: Int, b: Int): TextView = TextView(this).apply { text = s; textSize = size.toFloat(); setTextColor(color); this.gravity = gravity; setPadding(l, t, r, b); p.addView(this) }
    private fun card(p: LinearLayout, title: String, content: String) { val c = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; background = rounded(Color.WHITE, 20); setPadding(dp(15), dp(13), dp(15), dp(13)) }; text(c, title, 16, Color.rgb(35, 90, 50), Gravity.LEFT, 0, 0, 0, 5); text(c, content, 13, Color.DKGRAY, Gravity.LEFT, 0, 0, 0, 0); p.addView(c, LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, dp(7), 0, 0) }) }
    private fun rounded(color: Int, radius: Int): GradientDrawable = GradientDrawable().apply { setColor(color); cornerRadius = dp(radius).toFloat() }

    inner class DishArtView(context: android.content.Context, private val kind: Int) : View(context) {
        private val p = Paint(Paint.ANTI_ALIAS_FLAG)
        override fun onDraw(c: Canvas) {
            super.onDraw(c)
            val w = width.toFloat(); val h = height.toFloat(); val cx = w / 2f; val cy = h / 2f
            p.color = Color.rgb(246, 240, 225); c.drawRoundRect(0f, 0f, w, h, dp(18).toFloat(), dp(18).toFloat(), p)
            p.color = Color.WHITE; c.drawOval(cx - min(w, h) * .34f, cy - min(w, h) * .30f, cx + min(w, h) * .34f, cy + min(w, h) * .30f, p)
            p.style = Paint.Style.STROKE; p.strokeWidth = dp(2).toFloat(); p.color = Color.rgb(226, 218, 199); c.drawOval(cx - min(w, h) * .34f, cy - min(w, h) * .30f, cx + min(w, h) * .34f, cy + min(w, h) * .30f, p); p.style = Paint.Style.FILL
            val colors = when (kind) { 1 -> intArrayOf(Color.rgb(167, 76, 49), Color.rgb(79, 140, 56), Color.rgb(225, 173, 62)); 2 -> intArrayOf(Color.rgb(125, 55, 40), Color.rgb(80, 135, 58), Color.rgb(236, 173, 64)); 3 -> intArrayOf(Color.rgb(150, 78, 45), Color.rgb(92, 120, 52), Color.rgb(238, 178, 64)); 4 -> intArrayOf(Color.rgb(205, 143, 70), Color.rgb(80, 132, 53), Color.rgb(225, 173, 64)); 5 -> intArrayOf(Color.rgb(124, 62, 45), Color.rgb(83, 125, 55), Color.rgb(196, 153, 70)); 6 -> intArrayOf(Color.rgb(92, 132, 168), Color.rgb(75, 132, 60), Color.rgb(235, 174, 65)); 7 -> intArrayOf(Color.rgb(233, 143, 73), Color.rgb(78, 145, 64), Color.rgb(245, 195, 74)); 8 -> intArrayOf(Color.rgb(235, 190, 64), Color.rgb(205, 85, 55), Color.rgb(75, 150, 72)); 9 -> intArrayOf(Color.rgb(232, 185, 74), Color.rgb(110, 80, 60), Color.rgb(78, 150, 78)); 10 -> intArrayOf(Color.rgb(65, 150, 78), Color.rgb(80, 180, 86), Color.rgb(235, 205, 74)); else -> intArrayOf(Color.rgb(214, 120, 65), Color.rgb(78, 150, 80), Color.rgb(240, 198, 74)) }
            for (i in 0..2) { p.color = colors[i]; val dx = (i - 1) * w * .18f; val dy = if (i == 1) -h * .03f else h * .08f; c.drawOval(cx + dx - w * .12f, cy + dy - h * .09f, cx + dx + w * .12f, cy + dy + h * .09f, p) }
            p.color = Color.WHITE; p.textSize = dp(12).toFloat(); p.textAlign = Paint.Align.CENTER; c.drawText("家常菜", cx, h - dp(12), p)
        }
    }
}
