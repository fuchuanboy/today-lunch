package com.fuchuanboy.todaylunch

import android.app.Activity
import android.graphics.Color
import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.net.URL
import java.net.URLEncoder
import java.util.Calendar
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : Activity() {
    data class Dish(
        val name: String,
        val category: String,
        val ingredients: String,
        val score: Int,
        val steps: List<String>
    )

    private val random = Random(System.currentTimeMillis())
    private val dishes = listOf(
        "红烧肉|猪肉", "回锅肉|猪肉", "小炒肉|猪肉", "鱼香肉丝|猪肉", "木须肉|猪肉",
        "青椒肉丝|猪肉", "蒜薹炒肉|猪肉", "芹菜炒肉|猪肉", "香菇肉片|猪肉", "土豆烧肉|猪肉",
        "梅菜扣肉|猪肉", "腐竹烧肉|猪肉", "粉条炖肉|猪肉", "酸菜白肉|猪肉", "糖醋排骨|猪肉",
        "红烧排骨|猪肉", "椒盐排骨|猪肉", "粉蒸排骨|猪肉", "红烧猪蹄|猪肉", "黄豆炖猪蹄|猪肉",
        "肉末茄子|猪肉", "肉末豆角|猪肉", "肉末蒸蛋|猪肉", "肉末豆腐|猪肉",
        "洋葱肥牛|牛肉", "番茄肥牛|牛肉", "金针菇肥牛|牛肉", "土豆烧牛肉|牛肉", "黑椒牛柳|牛肉",
        "芹菜炒牛肉|牛肉", "小炒黄牛肉|牛肉", "孜然牛肉|牛肉", "水煮牛肉|牛肉", "酱牛肉|牛肉",
        "红烧牛腩|牛肉", "萝卜炖牛腩|牛肉", "咖喱牛肉|牛肉", "番茄牛腩|牛肉",
        "葱爆羊肉|羊肉", "孜然羊肉|羊肉", "手抓羊肉|羊肉", "红焖羊肉|羊肉", "萝卜炖羊肉|羊肉",
        "洋葱炒羊肉|羊肉", "芹菜炒羊肉|羊肉", "香辣羊肉|羊肉", "清炖羊肉|羊肉",
        "照烧鸡腿|鸡肉", "宫保鸡丁|鸡肉", "辣子鸡丁|鸡肉", "黄焖鸡|鸡肉", "咖喱鸡肉|鸡肉",
        "土豆烧鸡|鸡肉", "香菇炖鸡|鸡肉", "板栗烧鸡|鸡肉", "青椒鸡丁|鸡肉", "黑椒鸡柳|鸡肉",
        "柠檬鸡腿|鸡肉", "可乐鸡翅|鸡肉", "红烧鸡翅|鸡肉", "蒜香鸡翅|鸡肉", "盐焗鸡腿|鸡肉",
        "葱油鸡|鸡肉", "口水鸡|鸡肉", "麻辣鸡丝|鸡肉", "酸辣鸡丝|鸡肉",
        "啤酒鸭|鸭肉", "魔芋烧鸭|鸭肉", "红烧鸭块|鸭肉", "酸菜鸭|鸭肉", "土豆烧鸭|鸭肉",
        "香辣鸭块|鸭肉", "冬瓜炖鸭|鸭肉", "紫苏鸭|鸭肉",
        "清蒸鲈鱼|鱼肉", "红烧鲫鱼|鱼肉", "家常炖鱼|鱼肉", "酸菜鱼|鱼肉", "水煮鱼|鱼肉",
        "番茄鱼片|鱼肉", "剁椒鱼头|鱼肉", "葱烧鱼块|鱼肉", "糖醋鱼块|鱼肉", "香煎带鱼|鱼肉",
        "红烧带鱼|鱼肉", "清蒸带鱼|鱼肉", "椒盐带鱼|鱼肉",
        "蒜蓉粉丝虾|海鲜", "白灼虾|海鲜", "油焖大虾|海鲜", "香辣虾|海鲜", "蒜香虾仁|海鲜",
        "西兰花虾仁|海鲜", "芦笋虾仁|海鲜", "番茄虾仁|海鲜", "虾仁炒蛋|海鲜", "青豆虾仁|海鲜",
        "腰果虾仁|海鲜", "豆腐虾仁|海鲜", "粉丝蒸虾|海鲜", "椒盐虾|海鲜", "葱姜炒蟹|海鲜",
        "香辣蟹|海鲜", "清蒸螃蟹|海鲜", "蟹黄豆腐|海鲜"
    ).map { item ->
        val parts = item.split("|")
        parts[0] to parts[1]
    }

    private val vegetables = listOf(
        "上海青", "小白菜", "油麦菜", "菠菜", "菜心", "芥蓝", "西兰花", "菜花", "空心菜", "苋菜",
        "生菜", "娃娃菜", "大白菜", "包菜", "芦笋", "芹菜", "蒜薹", "豇豆", "四季豆", "毛豆",
        "茄子", "番茄", "黄瓜", "丝瓜", "冬瓜", "苦瓜", "西葫芦", "土豆", "莲藕", "山药",
        "胡萝卜", "白萝卜", "青椒", "彩椒", "洋葱", "豆芽", "韭菜", "香菇", "平菇", "杏鲍菇",
        "金针菇", "木耳", "海带", "莴笋", "秋葵", "南瓜", "紫甘蓝", "红薯叶"
    )

    private val soups = listOf(
        "番茄蛋花汤", "紫菜蛋花汤", "冬瓜虾皮汤", "海带豆腐汤", "菌菇豆腐汤", "酸辣汤",
        "玉米排骨汤", "萝卜牛腩汤", "山药排骨汤", "莲藕排骨汤", "鲫鱼豆腐汤", "丝瓜蛋汤",
        "菠菜蛋汤", "冬瓜肉丸汤", "紫菜虾皮汤", "青菜豆腐汤", "菌菇鸡汤", "老鸭冬瓜汤",
        "羊肉萝卜汤", "番茄牛肉汤"
    )

    private val seasonal = mapOf(
        1 to listOf("菠菜", "大白菜", "上海青", "芥蓝", "白萝卜"),
        2 to listOf("菠菜", "菜心", "上海青", "芥蓝", "生菜"),
        3 to listOf("菠菜", "油麦菜", "芹菜", "春笋", "菜心"),
        4 to listOf("油麦菜", "上海青", "苋菜", "芦笋", "莴笋"),
        5 to listOf("空心菜", "苋菜", "生菜", "油麦菜", "丝瓜"),
        6 to listOf("空心菜", "苋菜", "油麦菜", "丝瓜", "豇豆"),
        7 to listOf("空心菜", "苋菜", "丝瓜", "豇豆", "毛豆"),
        8 to listOf("空心菜", "苋菜", "丝瓜", "豇豆", "冬瓜"),
        9 to listOf("空心菜", "油麦菜", "上海青", "丝瓜", "芥蓝"),
        10 to listOf("菠菜", "上海青", "大白菜", "芥蓝", "西兰花"),
        11 to listOf("菠菜", "大白菜", "上海青", "萝卜", "西兰花"),
        12 to listOf("菠菜", "大白菜", "上海青", "芥蓝", "菜心")
    )

    private var today = mutableListOf<Dish>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateMeal()
        showHome()
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).roundToInt()

    private fun rounded(color: Int, radius: Int): GradientDrawable = GradientDrawable().apply {
        setColor(color)
        cornerRadius = dp(radius).toFloat()
    }

    private fun text(value: String, size: Float, color: Int = Color.DKGRAY): TextView = TextView(this).apply {
        text = value
        textSize = size
        setTextColor(color)
        setPadding(dp(6), dp(6), dp(6), dp(6))
    }

    private fun page(): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(18), dp(18), dp(18), dp(24))
        setBackgroundColor(Color.rgb(247, 250, 245))
    }

    private fun header(page: LinearLayout, title: String, subtitle: String) {
        page.addView(text("今天中午吃什么", 14f, Color.rgb(48, 126, 68)))
        page.addView(text(title, 29f, Color.rgb(20, 100, 55)).apply { gravity = Gravity.CENTER })
        page.addView(text(subtitle, 14f, Color.GRAY).apply { gravity = Gravity.CENTER })
    }

    private fun actionButton(page: LinearLayout, caption: String, action: () -> Unit) {
        page.addView(TextView(this).apply {
            text = caption
            textSize = 17f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            background = rounded(Color.rgb(55, 174, 91), 34)
            setPadding(dp(8), dp(12), dp(8), dp(12))
            setOnClickListener { action() }
        }, LinearLayout.LayoutParams(-1, dp(62)).apply {
            setMargins(0, dp(12), 0, dp(8))
        })
    }

    private fun card(parent: LinearLayout): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(14), dp(14), dp(14), dp(14))
        background = rounded(Color.WHITE, 22)
        parent.addView(this, LinearLayout.LayoutParams(-1, -2).apply {
            setMargins(0, dp(10), 0, dp(8))
        })
    }

    private fun foodImage(parent: LinearLayout, query: String, height: Int) {
        val image = ImageView(this).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            background = rounded(Color.rgb(232, 238, 230), 18)
        }
        parent.addView(image, LinearLayout.LayoutParams(-1, dp(height)).apply {
            setMargins(0, dp(4), 0, dp(8))
        })
        Thread {
            try {
                val encoded = URLEncoder.encode("$query Chinese food", "UTF-8")
                val url = URL("https://loremflickr.com/900/650/$encoded?lock=${query.hashCode().and(0x7fffffff)}")
                val bitmap = BitmapFactory.decodeStream(url.openStream())
                image.post { if (bitmap != null) image.setImageBitmap(bitmap) }
            } catch (_: Exception) {
                // Network images are optional; the card remains usable offline.
            }
        }.start()
    }

    private fun showHome() {
        val page = page()
        header(page, "今天中午吃什么？", "家常口味 · 时令搭配 · 科学膳食")
        val hero = card(page)
        foodImage(hero, "中国家常菜 四菜一汤", 220)
        hero.addView(text("2000+ 道组合菜谱", 21f, Color.rgb(30, 110, 65)).apply { gravity = Gravity.CENTER })
        hero.addView(text("随机组合 · 真实菜品图片 · 点击查看做法", 13f, Color.GRAY).apply { gravity = Gravity.CENTER })
        actionButton(page, "🎲  随机一桌\n生成今日四菜一汤") {
            generateMeal()
            showMenu()
        }
        page.addView(text("猪肉为主，牛肉、羊肉、鸡肉、鸭肉、鱼虾蟹轮换\n固定包含时令烧青菜，并给出营养搭配参考分", 14f).apply {
            gravity = Gravity.CENTER
        })
        setContentView(ScrollView(this).apply { addView(page) })
    }

    private fun makeDish(name: String, category: String, vegetable: String, score: Int): Dish {
        val steps = when {
            name.contains("蒸") || name.contains("清蒸") -> listOf(
                "主料洗净处理好，加入葱姜、料酒和少量生抽腌制10分钟。",
                "水烧开后上锅蒸，按照食材厚度蒸至完全熟透。",
                "取出后去掉多余汤汁，加入少量热油或生抽提香。"
            )
            name.contains("炖") || name.contains("烧") || name.contains("红焖") || name.contains("黄焖") -> listOf(
                "主料切块或切段，加入料酒、姜片和少量生抽腌制。",
                "热锅少油煎至表面微黄，加入葱姜蒜炒香。",
                "加入生抽、少量老抽和热水，大火烧开后转小火。",
                "炖至主料熟透，再加入$vegetable焖至断生，收汁后出锅。"
            )
            else -> listOf(
                "主料处理干净切好，加入料酒、生抽和少量淀粉抓匀腌制10分钟。",
                "热锅少油，葱姜蒜爆香后加入主料，大火翻炒至变色。",
                "加入$vegetable快速翻炒，根据菜式加入生抽、蚝油、醋或豆瓣酱。",
                "炒至配菜断生、肉类完全熟透，最后少量盐调味即可。"
            )
        }
        val ingredientText = "$name、$vegetable、葱、姜、蒜、食用油、生抽、料酒、盐"
        return Dish(name, category, ingredientText, score, steps)
    }

    private fun generateMeal() {
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val seasonalGreens = seasonal[month] ?: vegetables.take(5)
        today.clear()

        val categories = listOf("猪肉", "牛肉", "羊肉", "鸡肉", "鱼肉", "海鲜")
        val usedNames = mutableSetOf<String>()
        val usedVegetables = mutableSetOf<String>()

        for (category in categories) {
            if (today.size >= 3) break
            val candidate = dishes.filter { it.second == category && !usedNames.contains(it.first) }.randomOrNull(random)
            if (candidate != null) {
                val vegetable = vegetables.filter { !usedVegetables.contains(it) }.random(random)
                usedNames.add(candidate.first)
                usedVegetables.add(vegetable)
                today.add(makeDish(candidate.first, candidate.second, vegetable, 86 + random.nextInt(10)))
            }
        }

        while (today.size < 3) {
            val candidate = dishes.filter { !usedNames.contains(it.first) }.random(random)
            val vegetable = vegetables.filter { !usedVegetables.contains(it) }.randomOrNull(random) ?: vegetables.random(random)
            usedNames.add(candidate.first)
            usedVegetables.add(vegetable)
            today.add(makeDish(candidate.first, candidate.second, vegetable, 85 + random.nextInt(12)))
        }

        val green = seasonalGreens.random(random)
        today.add(
            Dish(
                "蒜蓉炒$green",
                "时令蔬菜",
                "$green、蒜末、食用油、盐、少量蚝油",
                95,
                listOf(
                    "$green洗净沥干，蒜末切好。",
                    "热锅少油，小火把蒜末炒出香味。",
                    "转大火加入$green快速翻炒，加少量盐和蚝油。",
                    "刚好断生就出锅，保持翠绿和脆嫩。"
                )
            )
        )
    }

    private fun showMenu() {
        val page = page()
        header(page, "今日四菜一汤", "荤素搭配 · 时令蔬菜 · 点击菜品查看完整做法")
        val average = today.map { it.score }.average().roundToInt()
        val summary = card(page)
        summary.addView(text("营养搭配评分  $average / 100", 21f, Color.rgb(25, 125, 65)).apply { gravity = Gravity.CENTER })
        summary.addView(text("动物蛋白 ✓   时令蔬菜 ✓   荤素搭配 ✓   汤品 ✓", 13f).apply { gravity = Gravity.CENTER })

        today.forEach { addDishCard(page, it) }
        addDishCard(page, makeSoup(soups.random(random)))

        actionButton(page, "🎲 再换一桌") {
            generateMeal()
            showMenu()
        }
        actionButton(page, "⌂ 返回首页") { showHome() }
        setContentView(ScrollView(this).apply { addView(page) })
    }

    private fun makeSoup(name: String): Dish = Dish(
        name,
        "汤品",
        "$name、葱、姜、盐",
        91,
        listOf(
            "食材洗净切好，准备葱姜。",
            "加水煮开后放入主料，转小火煮至熟透。",
            "最后少量盐调味，保持清淡即可。"
        )
    )

    private fun addDishCard(page: LinearLayout, dish: Dish) {
        val card = card(page)
        foodImage(card, dish.name, 155)
        card.addView(text(dish.name, 19f, Color.rgb(35, 80, 50)))
        card.addView(text("${dish.category} · 营养搭配参考 ${dish.score}/100", 13f, Color.GRAY))
        card.setOnClickListener { showDetail(dish) }
    }

    private fun showDetail(dish: Dish) {
        val page = page()
        header(page, dish.name, "${dish.category} · 完整家常做法")
        val card = card(page)
        foodImage(card, dish.name, 250)
        card.addView(text("营养搭配参考分  ${dish.score}/100", 20f, Color.rgb(25, 125, 65)))
        card.addView(text("主要用料", 18f, Color.DKGRAY))
        card.addView(text(dish.ingredients, 14f))
        card.addView(text("制作步骤", 18f, Color.DKGRAY))
        dish.steps.forEachIndexed { index, step ->
            card.addView(text("${index + 1}. $step", 14f))
        }
        card.addView(text("搭配建议", 18f, Color.DKGRAY))
        card.addView(text("午餐可搭配米饭；本评分只用于本应用的菜品组合参考，不替代专业营养评估。", 14f))
        actionButton(page, "← 返回今日菜单") { showMenu() }
        setContentView(ScrollView(this).apply { addView(page) })
    }
}
