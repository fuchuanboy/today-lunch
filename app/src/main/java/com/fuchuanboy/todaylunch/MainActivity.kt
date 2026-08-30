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
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class MainActivity : Activity() {

    data class Dish(
        val name: String,
        val type: String,
        val ingredients: String,
        val protein: Int = 0,
        val vegetables: Int = 0,
        val fiber: Int = 0,
        val calcium: Int = 0,
        val iron: Int = 0
    )

    private val porkNames = listOf(
        "红烧肉", "青椒肉丝", "木须肉", "土豆烧肉", "蒜薹炒肉", "鱼香肉丝", "回锅肉", "农家小炒肉",
        "京酱肉丝", "糖醋里脊", "锅包肉", "咕咾肉", "梅菜扣肉", "粉蒸肉", "小酥肉", "香菇滑肉",
        "榨菜肉丝", "芹菜炒肉", "蒜苗炒肉", "洋葱炒肉", "豆角炒肉", "茭白炒肉", "苦瓜炒肉",
        "青豆炒肉丁", "肉末茄子", "肉末豆角", "肉末粉条", "肉末蒸蛋", "白菜炖肉", "萝卜炖肉",
        "冬瓜烧肉", "莲藕炖排骨", "排骨烧土豆", "糖醋排骨", "椒盐排骨", "红烧排骨"
    )

    private val rotatingNames = listOf(
        "番茄炖牛腩|beef", "黑椒牛柳|beef", "芹菜炒牛肉|beef", "洋葱炒牛肉|beef", "土豆烧牛肉|beef",
        "水煮牛肉|beef", "孜然羊肉|lamb", "葱爆羊肉|lamb", "洋葱炒羊肉|lamb", "红焖羊肉|lamb",
        "姜爆鸭|duck", "魔芋烧鸭|duck", "啤酒鸭|duck", "土豆烧鸭|duck", "青椒炒鸭片|duck",
        "宫保鸡丁|chicken", "香菇滑鸡|chicken", "青椒炒鸡丁|chicken", "黄焖鸡|chicken", "栗子烧鸡|chicken",
        "板栗鸡块|chicken", "辣子鸡丁|chicken", "土豆鸡块|chicken", "葱油鸡|chicken", "红烧鸡翅|chicken",
        "清蒸鲈鱼|fish", "红烧鲫鱼|fish", "糖醋鲤鱼|fish", "剁椒鱼头|fish", "香煎带鱼|fish",
        "葱烧带鱼|fish", "酸菜鱼|fish", "水煮鱼片|fish", "清蒸多宝鱼|fish", "红烧黄花鱼|fish",
        "虾仁炒蛋|shrimp", "蒜蓉大虾|shrimp", "白灼虾|shrimp", "椒盐虾|shrimp", "西兰花炒虾仁|shrimp"
    )

    private val beanEggNames = listOf(
        "麻婆豆腐", "家常豆腐", "葱烧豆腐", "香煎豆腐", "锅塌豆腐", "红烧豆腐", "豆腐烧白菜", "豆腐烧蘑菇",
        "肉末豆腐", "虾仁豆腐", "鸡蛋豆腐羹", "番茄豆腐", "小葱拌豆腐", "凉拌豆腐", "豆干炒芹菜",
        "香干炒肉丝", "芹菜香干", "青椒炒豆干", "腐竹木耳", "腐竹烧肉", "黄豆芽炒粉条", "毛豆炒鸡蛋",
        "韭菜炒鸡蛋", "番茄炒鸡蛋", "木耳炒鸡蛋", "黄瓜炒鸡蛋", "西葫芦炒鸡蛋", "青椒炒鸡蛋"
    )

    private val vegetableNames = listOf(
        "蒜蓉空心菜", "清炒苋菜", "蒜蓉油麦菜", "清炒上海青", "香菇上海青", "蒜蓉生菜", "蚝油生菜", "清炒菜心",
        "蒜蓉菠菜", "香菇青菜", "清炒白菜", "醋溜白菜", "蒜蓉西兰花", "清炒西兰花", "干煸四季豆", "蒜蓉四季豆",
        "肉末豇豆", "清炒豇豆", "蒜泥茄子", "鱼香茄子", "红烧茄子", "清炒丝瓜", "蒜蓉丝瓜", "番茄烧丝瓜",
        "清炒冬瓜", "虾皮冬瓜", "红烧冬瓜", "蒜蓉西葫芦", "清炒西葫芦", "酸辣土豆丝", "青椒土豆丝", "醋溜土豆丝",
        "清炒藕片", "酸辣藕片", "荷塘小炒", "蒜蓉娃娃菜", "上汤娃娃菜", "清炒芥蓝", "蚝油芥蓝", "蒜蓉芥蓝",
        "清炒芦笋", "蒜香芦笋", "香菇炒青菜", "木耳炒山药", "清炒山药", "蒜蓉菜花", "干锅菜花", "清炒豆芽",
        "蒜苗炒豆芽", "炝炒莲白", "手撕包菜", "干锅包菜", "蒜蓉西葫芦", "清炒苦瓜", "苦瓜炒木耳", "凉拌黄瓜"
    )

    private val soupNames = listOf(
        "番茄蛋花汤", "紫菜蛋花汤", "冬瓜排骨汤", "菌菇豆腐汤", "萝卜瘦肉汤", "青菜豆腐汤", "冬瓜虾皮汤", "丝瓜蛋汤",
        "菠菜蛋花汤", "紫菜虾皮汤", "玉米排骨汤", "山药排骨汤", "莲藕排骨汤", "海带排骨汤", "冬瓜丸子汤", "白菜豆腐汤",
        "酸辣汤", "三鲜汤", "菌菇鸡蛋汤", "丝瓜虾仁汤", "番茄豆腐汤", "毛豆蛋汤"
    )

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

    private var meal = mutableListOf<Dish>()
    private var selected: Dish? = null
    private lateinit var root: LinearLayout
    private lateinit var body: LinearLayout
    private var currentTab = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildShell()
        showHome()
    }

    private fun buildShell() {
        root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(Color.rgb(247, 249, 246)) }
        body = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val scroll = ScrollView(this).apply { addView(body); isFillViewport = true }
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        root.addView(bottomNav(), LinearLayout.LayoutParams(-1, dp(68)))
        setContentView(root)
    }

    private fun showHome() {
        currentTab = 0
        body.removeAllViews()
        val box = page()
        val location = TextView(this).apply { text = "⌖ 河南省 · 郑州市  ▾"; textSize = 13f; setTextColor(Color.DKGRAY); setPadding(0, dp(4), 0, dp(8)) }
        box.addView(location)
        val title = TextView(this).apply { text = "今天中午吃什么？"; textSize = 31f; setTypeface(null, 1); setTextColor(Color.rgb(25, 90, 50)); gravity = Gravity.CENTER; setPadding(0, dp(14), 0, dp(2)) }
        box.addView(title)
        addTextTo(box, "营养均衡 · 时令搭配 · 科学膳食", 15f, Color.GRAY, Gravity.CENTER, 0, 0, 0, 14)
        box.addView(PlateView(this), LinearLayout.LayoutParams(-1, dp(245)).apply { setMargins(dp(10), dp(4), dp(10), dp(8)) })
        val btn = Button(this).apply { text = "🎲  随机一桌\n生成今日四菜一汤"; textSize = 17f; setTextColor(Color.WHITE); setBackgroundColor(Color.rgb(55, 165, 91)); setOnClickListener { newMeal(); showMeal() } }
        box.addView(btn, LinearLayout.LayoutParams(-1, dp(64)).apply { setMargins(dp(4), dp(6), dp(4), dp(12)) })
        val features = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER }
        listOf("✓\n营养均衡", "🍊\n时令食材", "🐖\n猪肉为主", "♧\n科学搭配").forEach { s ->
            val t = TextView(this).apply { text = s; gravity = Gravity.CENTER; textSize = 12f; setTextColor(Color.DKGRAY); setPadding(dp(8), dp(8), dp(8), dp(8)) }
            features.addView(t, LinearLayout.LayoutParams(0, dp(58), 1f))
        }
        box.addView(features)
        addInfoCard(box, "今天的规则", "猪肉作为主要动物蛋白；牛、羊、鸭、鸡、鱼、虾轮换。每天固定一份当季烧青菜 + 一份豆制品/蛋类 + 一汤。")
        body.addView(box)
    }

    private fun showMeal() {
        currentTab = 0
        body.removeAllViews()
        if (meal.isEmpty()) newMeal()
        val box = page()
        val top = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL }
        addTextTo(top, "‹", 30f, Color.DKGRAY, Gravity.CENTER, 0, 0, 16, 0).setOnClickListener { showHome() }
        val title = TextView(this).apply { text = "今日午餐"; textSize = 23f; setTypeface(null, 1); gravity = Gravity.CENTER }
        top.addView(title, LinearLayout.LayoutParams(0, dp(50), 1f))
        addTextTo(top, "↻", 27f, Color.DKGRAY, Gravity.CENTER, 16, 0, 0, 0).setOnClickListener { newMeal(); showMeal() }
        box.addView(top)
        val date = SimpleDateFormat("M月d日  EEEE", Locale.CHINA).format(Calendar.getInstance().time)
        addTextTo(box, "$date    时令：${seasonName()}", 13f, Color.GRAY, Gravity.LEFT, 4, 0, 0, 8)
        addTextTo(box, "🍃  今日四菜一汤", 21f, Color.rgb(30, 110, 60), Gravity.CENTER, 0, 0, 0, 10)
        meal.forEachIndexed { i, dish ->
            val label = when (i) { 0 -> "猪肉主菜"; 1 -> "动物蛋白轮换"; 2 -> "豆制品 / 蛋类"; 3 -> "当季烧青菜"; else -> "汤品" }
            addDishCard(box, dish, label)
        }
        val score = nutritionScore()
        addScoreCard(box, score)
        val change = Button(this).apply { text = "换一桌  ·  无尽随机"; textSize = 16f; setOnClickListener { newMeal(); showMeal() } }
        box.addView(change, LinearLayout.LayoutParams(-1, dp(52)).apply { setMargins(0, dp(14), 0, dp(14)) })
        body.addView(box)
    }

    private fun showNutrition() {
        currentTab = 1
        body.removeAllViews()
        val box = page()
        addTextTo(box, "营养分析", 25f, Color.rgb(30, 70, 45), Gravity.CENTER, 0, 10, 0, 12)
        if (meal.isEmpty()) newMeal()
        val score = nutritionScore()
        val gauge = TextView(this).apply { text = "$score\n营养均衡度"; textSize = 28f; setTypeface(null, 1); gravity = Gravity.CENTER; setTextColor(Color.rgb(45, 145, 78)); setPadding(0, dp(18), 0, dp(18)) }
        box.addView(gauge, LinearLayout.LayoutParams(-1, dp(125)))
        listOf("蛋白质" to if (meal.any { it.protein >= 3 }) "充足" else "良好", "膳食纤维" to if (meal.sumOf { it.fiber } >= 8) "充足" else "良好", "维生素" to if (meal.sumOf { it.vegetables } >= 8) "充足" else "良好", "钙" to if (meal.sumOf { it.calcium } >= 6) "充足" else "良好", "铁" to if (meal.sumOf { it.iron } >= 6) "良好" else "适中").forEach { (a,b) ->
            val row = LinearLayout(this).apply { setPadding(dp(14), dp(9), dp(14), dp(9)); gravity = Gravity.CENTER_VERTICAL) }
            addTextTo(row, a, 15f, Color.DKGRAY, Gravity.LEFT, 0, 0, 0, 0).layoutParams = LinearLayout.LayoutParams(0, -2, 1f)
            addTextTo(row, b, 14f, Color.rgb(55, 150, 80), Gravity.RIGHT, 0, 0, 0, 0)
            box.addView(row)
        }
        addInfoCard(box, "搭配评价", "今天的午餐采用“猪肉主菜 + 轮换动物蛋白 + 豆制品/蛋类 + 当季绿叶菜 + 汤”的结构。分数用于帮助搭配，不代替个体化营养建议。")
        addInfoCard(box, "来源结构", "猪肉、牛羊肉、鸡鸭、鱼虾、蛋类、豆制品、时令蔬菜和菌菇轮换，避免连续多天只吃同一种肉。")
        body.addView(box)
    }

    private fun showSeasonal() {
        currentTab = 2
        body.removeAllViews()
        val box = page()
        addTextTo(box, "时令蔬菜", 25f, Color.rgb(30, 70, 45), Gravity.CENTER, 0, 10, 0, 6)
        addTextTo(box, "河南当前时令：${seasonName()}", 14f, Color.GRAY, Gravity.CENTER, 0, 0, 0, 14)
        val names = seasonal[Calendar.getInstance().get(Calendar.MONTH)+1] ?: seasonal[8]!!
        val grid = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        names.forEach { n ->
            val card = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL; setPadding(dp(12), dp(8), dp(12), dp(8)); setBackgroundColor(Color.WHITE) }
            card.addView(VegView(this, n), LinearLayout.LayoutParams(dp(78), dp(70)))
            addTextTo(card, "$n\n适合：清炒、蒜蓉、上汤、搭配肉类", 15f, Color.DKGRAY, Gravity.LEFT, 10, 0, 0, 0)
            grid.addView(card, LinearLayout.LayoutParams(-1, dp(86)).apply { setMargins(0, dp(4), 0, 0) })
        }
        box.addView(grid)
        addInfoCard(box, "时令原则", "优先选择本季蔬菜，保证每天至少一份绿色蔬菜；同一周尽量不重复同一种烧青菜。")
        body.addView(box)
    }

    private fun showDetail(dish: Dish) {
        selected = dish
        body.removeAllViews()
        val box = page()
        val top = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL }
        addTextTo(top, "‹", 30f, Color.DKGRAY, Gravity.CENTER, 0, 0, 16, 0).setOnClickListener { showMeal() }
        addTextTo(top, dish.name, 22f, Color.rgb(35, 35, 35), Gravity.CENTER, 0, 0, 0, 0).layoutParams = LinearLayout.LayoutParams(0, dp(52), 1f)
        addTextTo(top, "☆", 28f, Color.rgb(230, 170, 30), Gravity.CENTER, 16, 0, 0, 0)
        box.addView(top)
        box.addView(DishArtView(this, dish), LinearLayout.LayoutParams(-1, dp(230)).apply { setMargins(dp(6), 0, dp(6), dp(8)) })
        addTextTo(box, "${typeLabel(dish.type)}   ·   家常做法", 13f, Color.GRAY, Gravity.LEFT, 8, 0, 0, 6)
        addInfoCard(box, "食材准备", dish.ingredients)
        addInfoCard(box, "怎么做", stepsFor(dish))
        addInfoCard(box, "营养特点", "蛋白质 ${if (dish.protein >= 3) "较高" else "适中"} · 蔬菜贡献 ${dish.vegetables} · 膳食纤维 ${dish.fiber} · 钙 ${dish.calcium} · 铁 ${dish.iron}")
        val tip = TextView(this).apply { text = "小贴士：家常午餐少油少盐即可；肉类彻底加热，海鲜和蛋类注意熟透。"; textSize = 13f; setTextColor(Color.GRAY); setPadding(dp(12), dp(12), dp(12), dp(18)) }
        box.addView(tip)
        body.addView(box)
    }

    private fun newMeal() {
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val seasonalNames = seasonal[month] ?: seasonal[8]!!
        val greenName = vegetableNames.filter { n -> seasonalNames.any { s -> n.contains(s) } }.ifEmpty { vegetableNames }.random()
        val green = makeDish(greenName, "green")
        val main = makeDish(porkNames.random(), "pork")
        val rotating = makeDish(rotatingNames.random().substringBefore("|"), rotatingNames.random().substringAfter("|"))
        val bean = makeDish(beanEggNames.random(), if (beanEggNames.random().contains("鸡蛋")) "egg" else "bean")
        val soup = makeDish(soupNames.random(), "soup")
        meal = mutableListOf(main, rotating, bean, green, soup)
    }

    private fun makeDish(name: String, type: String): Dish {
        val veg = if (type in listOf("green","bean","egg")) 3 else if (name.contains("番茄|青椒|土豆|洋葱|芹菜|豆角|白菜|冬瓜|丝瓜|蘑菇|西兰花|黄瓜|藕|萝卜|山药".toRegex())) 1 else 0
        val protein = when (type) { "pork","beef","lamb","duck","chicken","fish","shrimp" -> 4; "egg","bean" -> 2; "soup" -> if (name.contains("排骨|鸡|瘦肉|虾|蛋")) 2 else 1; else -> 0 }
        val fiber = when (type) { "green" -> 4; "bean","egg" -> 2; else -> veg }
        val calcium = when { name.contains("豆腐|豆干|腐竹") -> 3; name.contains("虾皮|紫菜") -> 2; type == "green" -> 1; else -> 0 }
        val iron = when { type in listOf("pork","beef","lamb") -> 2; type == "green" -> 2; type == "bean" -> 2; else -> 1 }
        return Dish(name, type, ingredientsFor(name, type), protein, veg, fiber, calcium, iron)
    }

    private fun ingredientsFor(name: String, type: String): String = when {
        type == "pork" -> "猪肉、葱姜蒜、应季蔬菜、食用油、生抽、少量老抽"
        type == "beef" -> "牛肉、洋葱/芹菜、葱姜蒜、生抽、黑胡椒"
        type == "lamb" -> "羊肉、洋葱、香菜、孜然、辣椒面"
        type == "duck" -> "鸭肉、姜、青椒、葱、生抽"
        type == "chicken" -> "鸡肉、葱姜蒜、时令蔬菜、生抽"
        type == "fish" -> "鲜鱼、姜、葱、蒸鱼豉油或生抽"
        type == "shrimp" -> "鲜虾/虾仁、葱姜、时令蔬菜、少量食用油"
        type == "bean" -> "豆腐/豆干/腐竹、葱姜蒜、时令蔬菜、生抽"
        type == "egg" -> "鸡蛋、时令蔬菜、葱花、少量食用油"
        type == "green" -> "当季绿叶菜、蒜、少量食用油、盐"
        else -> "当季蔬菜、蛋/肉/菌菇、葱姜、少量盐"
    }

    private fun stepsFor(d: Dish): String = when {
        d.type == "green" -> "1. 蔬菜洗净切段，蒜切末。\n2. 锅烧热放少量油，下蒜末炒香。\n3. 放蔬菜大火翻炒至断生。\n4. 加少量盐快速翻匀出锅，保持脆嫩。"
        d.type == "fish" -> "1. 鱼处理干净，两面划花刀。\n2. 放姜葱去腥，按菜式煎、烧或上汽蒸熟。\n3. 加生抽/蒸鱼豉油调味。\n4. 确认鱼肉完全熟透后出锅。"
        d.type == "shrimp" -> "1. 虾仁去虾线并冲洗。\n2. 热锅少油，先把虾仁炒至变色。\n3. 加蔬菜或鸡蛋快速翻炒。\n4. 少量盐调味，炒熟立即出锅。"
        d.type == "soup" -> "1. 主料洗净切块。\n2. 需要焯水的肉类先焯水。\n3. 加足量热水煮开，小火炖至食材熟软。\n4. 最后放盐，汤品尽量清淡。"
        d.name.contains("红烧|炖|焖|烧") -> "1. 肉类切块并焯水，蔬菜切块。\n2. 锅中少油煸香葱姜蒜，下主料翻炒。\n3. 加生抽、少量老抽和热水，小火焖熟。\n4. 开大火收汁，注意少盐少油。"
        d.name.contains("蒸") -> "1. 食材洗净切配并调味。\n2. 盘中铺姜葱或蔬菜。\n3. 水开后上锅蒸至中心完全熟透。\n4. 出锅后淋少量生抽或热油即可。"
        d.name.contains("凉拌") -> "1. 食材洗净处理，必要时焯熟。\n2. 放凉后控干水分。\n3. 加蒜末、生抽、醋等调味。\n4. 拌匀即可，少放油盐。"
        else -> "1. 主料切丝/片，蔬菜洗净切配。\n2. 肉类先用少量淀粉抓匀，热锅少油滑熟盛出。\n3. 下蔬菜大火炒至断生，倒回主料。\n4. 加生抽和少量盐快速翻匀出锅。"
    }

    private fun nutritionScore(): Int {
        if (meal.isEmpty()) return 90
        var s = 80
        if (meal.count { it.protein >= 3 } >= 2) s += 5
        if (meal.any { it.type == "green" }) s += 4
        if (meal.any { it.type == "bean" || it.type == "egg" }) s += 3
        if (meal.any { it.type == "soup" }) s += 2
        if (meal.map { it.type }.distinct().size >= 4) s += 3
        return min(98, max(80, s))
    }

    private fun addScoreCard(parent: LinearLayout, score: Int) {
        val card = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(16), dp(14), dp(16), dp(14)); setBackgroundColor(Color.WHITE) }
        addTextTo(card, "今日营养搭配  $score / 100", 18f, Color.rgb(35, 125, 65), Gravity.LEFT, 0, 0, 0, 6)
        addTextTo(card, "蛋白质 充足   ·   膳食纤维 充足   ·   维生素 充足", 13f, Color.DKGRAY, Gravity.LEFT, 0, 0, 0, 2)
        addTextTo(card, "荤素比例合理，猪肉主菜 + 动物蛋白轮换 + 豆制品 + 时令青菜 + 汤。", 12f, Color.GRAY, Gravity.LEFT, 0, 0, 0, 0)
        parent.addView(card, LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, dp(8), 0, 0) })
    }

    private fun addDishCard(parent: LinearLayout, dish: Dish, label: String) {
        val card = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL; setPadding(dp(10), dp(8), dp(8), dp(8)); setBackgroundColor(Color.WHITE); setOnClickListener { showDetail(dish) } }
        card.addView(DishArtView(this, dish), LinearLayout.LayoutParams(dp(92), dp(82)))
        val text = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(10), 0, 0, 0) }
        addTextTo(text, dish.name, 17f, Color.rgb(35, 35, 35), Gravity.LEFT, 0, 0, 0, 2)
        addTextTo(text, label, 11f, Color.rgb(50, 145, 75), Gravity.LEFT, 0, 0, 0, 4)
        addTextTo(text, "${dish.ingredients.take(28)}…", 11f, Color.GRAY, Gravity.LEFT, 0, 0, 0, 0)
        card.addView(text, LinearLayout.LayoutParams(0, -2, 1f))
        addTextTo(card, "›", 26f, Color.LTGRAY, Gravity.CENTER, 4, 0, 0, 0)
        parent.addView(card, LinearLayout.LayoutParams(-1, dp(92)).apply { setMargins(0, dp(5), 0, 0) })
    }

    private fun addInfoCard(parent: LinearLayout, title: String, text: String) {
        val card = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(14), dp(12), dp(14), dp(12)); setBackgroundColor(Color.WHITE) }
        addTextTo(card, title, 16f, Color.rgb(40, 75, 48), Gravity.LEFT, 0, 0, 0, 5)
        addTextTo(card, text, 13f, Color.rgb(90, 90, 90), Gravity.LEFT, 0, 0, 0, 0)
        parent.addView(card, LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, dp(8), 0, 0) })
    }

    private fun page() = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(16), dp(14), dp(16), dp(20)) }

    private fun bottomNav(): LinearLayout {
        val nav = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER; setBackgroundColor(Color.WHITE) }
        val items = listOf("⌂\n今天午餐", "▣\n营养", "♧\n时令", "☆\n收藏")
        items.forEachIndexed { i, label ->
            val t = TextView(this).apply { text = label; textSize = 11f; gravity = Gravity.CENTER; setTextColor(if (i == 0) Color.rgb(45, 155, 78) else Color.GRAY); setOnClickListener { when(i) { 0 -> { if (meal.isEmpty()) newMeal(); showMeal() }; 1 -> showNutrition(); 2 -> showSeasonal(); else -> Toast.makeText(this@MainActivity, "收藏功能下一版加入", Toast.LENGTH_SHORT).show() } } }
            nav.addView(t, LinearLayout.LayoutParams(0, -1, 1f))
        }
        return nav
    }

    private fun addTextTo(p: LinearLayout, s: String, size: Float, color: Int, gravity: Int, l: Int, t: Int, r: Int, b: Int): TextView {
        val v = TextView(this).apply { text = s; textSize = size; setTextColor(color); this.gravity = gravity; setPadding(dp(l), dp(t), dp(r), dp(b)) }
        p.addView(v)
        return v
    }

    private fun typeLabel(t: String) = when(t) { "pork" -> "猪肉"; "beef" -> "牛肉"; "lamb" -> "羊肉"; "duck" -> "鸭肉"; "chicken" -> "鸡肉"; "fish" -> "鱼类"; "shrimp" -> "虾类"; "bean" -> "豆制品"; "egg" -> "蛋类"; "green" -> "时令蔬菜"; else -> "汤品" }
    private fun seasonName() = when(Calendar.getInstance().get(Calendar.MONTH)+1) { 3,4,5 -> "春季"; 6,7,8 -> "夏季"; 9,10,11 -> "秋季"; else -> "冬季" }
    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()

    class PlateView(c: android.content.Context) : View(c) {
        private val p = Paint(3)
        override fun onDraw(c: Canvas) { val w=width.toFloat(); val h=height.toFloat(); p.color=Color.rgb(237,248,228); c.drawRoundRect(RectF(4f,8f,w-4f,h-8f),34f,34f,p); p.color=Color.WHITE; c.drawOval(RectF(w*.12f,h*.12f,w*.88f,h*.88f),p); p.color=Color.rgb(255,244,210); c.drawOval(RectF(w*.23f,h*.27f,w*.62f,h*.72f),p); p.color=Color.rgb(238,185,70); c.drawOval(RectF(w*.27f,h*.31f,w*.58f,h*.65f),p); p.color=Color.rgb(70,160,80); c.drawCircle(w*.68f,h*.42f,h*.13f,p); c.drawCircle(w*.72f,h*.58f,h*.12f,p); p.color=Color.rgb(150,90,45); c.drawOval(RectF(w*.48f,h*.58f,w*.78f,h*.78f),p); p.color=Color.rgb(248,220,185); c.drawCircle(w*.72f,h*.33f,h*.08f,p) }
    }

    class VegView(c: android.content.Context, private val name: String) : View(c) {
        private val p=Paint(3)
        override fun onDraw(c:Canvas){ p.color=Color.rgb(235,247,231); c.drawRoundRect(RectF(3f,3f,width-3f,height-3f),20f,20f,p); p.color=Color.rgb(75,170,85); repeat(5){ i -> c.drawOval(RectF(width*.25f+i*3,height*.25f-i*2,width*.58f+i*2,height*.7f),p) }; p.color=Color.rgb(70,120,65); c.drawRect(width*.48f,height*.45f,width*.53f,height*.82f,p) }
    }

    class DishArtView(c: android.content.Context, private val dish: Dish) : View(c) {
        private val p=Paint(3)
        override fun onDraw(c:Canvas){ p.color=Color.rgb(248,241,227); c.drawRoundRect(RectF(2f,2f,width-2f,height-2f),18f,18f,p); p.color=Color.WHITE; c.drawOval(RectF(width*.08f,height*.12f,width*.92f,height*.88f),p); val base=when(dish.type){"pork"->Color.rgb(190,90,55);"beef"->Color.rgb(145,65,45);"lamb"->Color.rgb(165,80,60);"duck"->Color.rgb(125,75,50);"chicken"->Color.rgb(220,165,70);"fish"->Color.rgb(210,170,105);"shrimp"->Color.rgb(235,115,95);"bean"->Color.rgb(180,115,70);"egg"->Color.rgb(240,190,60);"green"->Color.rgb(65,155,75);else->Color.rgb(220,225,210)}; p.color=base; for(i in 0..5){ val x=width*.25f+(i%3)*width*.18f; val y=height*.32f+(i/3)*height*.22f; c.drawCircle(x,y,min(width,height)*.10f,p) }; p.color=Color.rgb(70,140,65); if(dish.type in listOf("green","pork","beef","chicken","fish","shrimp")){ c.drawCircle(width*.70f,height*.28f,min(width,height)*.07f,p); c.drawCircle(width*.75f,height*.63f,min(width,height)*.06f,p) } }
    }
}
