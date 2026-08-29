package com.fuchuanboy.todaylunch

import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import kotlin.math.min
import kotlin.random.Random

class MainActivity : Activity() {

    data class Dish(
        val name: String,
        val type: String,
        val method: String,
        val ingredients: String
    )

    private val pork = listOf(
        Dish("红烧肉", "pork", "五花肉切块焯水，炒糖色后加姜葱、生抽老抽和热水，小火焖40分钟收汁。", "五花肉、姜、葱、生抽、老抽"),
        Dish("青椒肉丝", "pork", "猪里脊切丝，加少许淀粉抓匀；热锅快炒肉丝，加入青椒和生抽，大火翻匀。", "里脊肉、青椒、蒜、生抽"),
        Dish("木须肉", "pork", "肉片滑熟后盛出，鸡蛋炒散；再炒木耳、黄瓜，倒回肉片和鸡蛋，加盐快速翻匀。", "猪里脊、鸡蛋、木耳、黄瓜"),
        Dish("土豆烧肉", "pork", "肉块煸香后加姜蒜、生抽和热水焖20分钟，再放土豆继续焖至软糯，最后收汁。", "猪肉、土豆、姜、蒜"),
        Dish("蒜薹炒肉", "pork", "肉丝滑熟后盛出，蒜薹大火炒至断生，加入肉丝和少量生抽翻炒即可。", "猪肉、蒜薹、蒜、生抽"),
        Dish("香菇滑肉", "pork", "肉片用淀粉和少量水抓匀，香菇炒香后加水；放肉片煮熟，加青葱调味。", "猪里脊、香菇、葱、淀粉"),
        Dish("家常小炒肉", "pork", "五花肉薄片煸出油脂，加入青椒、蒜片和豆豉，大火快速翻炒。", "五花肉、青椒、蒜、豆豉")
    )

    private val otherMeat = listOf(
        Dish("番茄炖牛腩", "beef", "牛腩焯水后与番茄、姜一起炖煮约60分钟，最后加盐调味，酸香软烂。", "牛腩、番茄、姜、葱"),
        Dish("孜然羊肉", "lamb", "羊肉片大火快速炒熟，加入洋葱、孜然和辣椒面，翻匀立即出锅。", "羊肉、洋葱、孜然"),
        Dish("姜爆鸭", "duck", "鸭肉焯水后煸香，加入姜片和青椒，大火炒至鸭肉熟透，少量生抽调味。", "鸭肉、姜、青椒"),
        Dish("宫保鸡丁", "chicken", "鸡丁加少许淀粉抓匀，快速滑熟；加入黄瓜、花生和宫保汁，大火翻匀。", "鸡胸肉、黄瓜、花生、葱"),
        Dish("清蒸鱼", "fish", "鱼身划刀，放姜葱，上汽后蒸8—12分钟，倒掉蒸汁，淋蒸鱼豉油和热油。", "鲜鱼、姜、葱、蒸鱼豉油"),
        Dish("虾仁炒蛋", "shrimp", "鸡蛋炒至半凝固盛出，虾仁炒熟后倒回鸡蛋，加葱花快速翻匀。", "虾仁、鸡蛋、葱")
    )

    private val beans = listOf(
        Dish("麻婆豆腐", "bean", "豆腐焯水；肉末炒香后加豆瓣酱、姜蒜和少量热水，放豆腐小火烧5分钟。", "豆腐、肉末、豆瓣酱、姜蒜"),
        Dish("家常豆腐", "bean", "豆腐煎至两面微黄，加入木耳、青椒和生抽，加少量水烧3—5分钟。", "豆腐、木耳、青椒"),
        Dish("葱烧豆腐", "bean", "豆腐煎香，加入大葱、生抽和少量热水，小火焖3分钟收汁。", "豆腐、大葱、生抽"),
        Dish("腐竹木耳", "bean", "泡发腐竹和木耳，蒜片爆香后大火翻炒，加蚝油和少量水焖2分钟。", "腐竹、木耳、蒜、蚝油")
    )

    private val seasonalGreens = mapOf(
        1 to listOf("蒜蓉菠菜", "香菇上海青", "清炒白菜"),
        2 to listOf("香菇上海青", "蒜蓉生菜", "清炒菜心"),
        3 to listOf("香菇青菜", "蒜蓉菜心", "清炒菠菜"),
        4 to listOf("蒜蓉油麦菜", "香菇青菜", "清炒西兰花"),
        5 to listOf("蚝油生菜", "蒜蓉空心菜", "清炒西兰花"),
        6 to listOf("蒜蓉空心菜", "清炒苋菜", "蚝油生菜"),
        7 to listOf("蒜蓉空心菜", "清炒苋菜", "清炒丝瓜"),
        8 to listOf("蒜蓉空心菜", "清炒苋菜", "蒜蓉油麦菜", "清炒丝瓜"),
        9 to listOf("香菇青菜", "蒜蓉上海青", "清炒西兰花"),
        10 to listOf("香菇上海青", "蒜蓉菠菜", "清炒白菜"),
        11 to listOf("蒜蓉菠菜", "香菇上海青", "清炒白菜"),
        12 to listOf("蒜蓉菠菜", "香菇上海青", "清炒白菜")
    )

    private val soups = listOf(
        Dish("番茄蛋花汤", "soup", "番茄炒出汁，加水煮开，淋入打散的鸡蛋，最后加盐和葱花。", "番茄、鸡蛋、葱"),
        Dish("紫菜蛋花汤", "soup", "水开后放紫菜，淋入蛋液形成蛋花，加盐和香油，撒葱花。", "紫菜、鸡蛋、葱"),
        Dish("冬瓜排骨汤", "soup", "排骨焯水，与冬瓜、姜一起炖40分钟，最后加盐和葱花。", "排骨、冬瓜、姜"),
        Dish("菌菇汤", "soup", "菌菇洗净后加水煮8—10分钟，加入豆腐和青菜，少量盐调味。", "香菇、平菇、豆腐、青菜"),
        Dish("萝卜汤", "soup", "白萝卜切块，加姜和瘦肉煮25—30分钟，最后加盐调味。", "白萝卜、瘦肉、姜"),
        Dish("青菜豆腐汤", "soup", "水开后放豆腐煮3分钟，加入青菜煮1—2分钟，加盐和少量香油。", "豆腐、青菜、香油")
    )

    private var meal: List<Dish> = emptyList()
    private lateinit var content: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        show()
    }

    private fun show() {
        val scroll = ScrollView(this)
        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(28), dp(18), dp(24))
            setBackgroundColor(Color.rgb(250, 247, 240))
        }
        scroll.addView(content)
        setContentView(scroll)
        renderMeal()
    }

    private fun renderMeal() {
        content.removeAllViews()

        addText("今天中午吃什么？", 30f, Color.rgb(60, 45, 35), dp(8), Gravity.CENTER)
        addText("随机四菜一汤 · 河南家常口味 · 荤素搭配", 16f, Color.rgb(105, 99, 92), dp(6), Gravity.CENTER)

        val month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1
        val greenName = seasonalGreens[month]!!.random()
        val green = Dish(greenName, "green", greenMethod(greenName), greenIngredients(greenName))
        val main = pork.random()
        val rotating = otherMeat.random()
        val bean = beans.random()
        val soup = soups.random()
        meal = listOf(main, rotating, bean, green, soup)

        val hero = DishIllustrationView(this, "meal")
        content.addView(hero, LinearLayout.LayoutParams(-1, dp(150)).apply { setMargins(0, dp(12), 0, dp(8)) })

        addSectionTitle("🍚 今日午餐")
        addDishCard(main, "荤菜① · 猪肉主菜")
        addDishCard(rotating, "荤菜② · 动物蛋白轮换")
        addDishCard(bean, "素菜① · 豆制品")
        addDishCard(green, "素菜② · 当季烧青菜")
        addDishCard(soup, "🍲 汤")

        val score = nutritionScore(main, rotating, bean, green, soup)
        addNutritionCard(score, main, rotating, bean, green, soup)

        addText("主食：米饭（按饭量调整）", 16f, Color.rgb(80, 74, 68), dp(10), Gravity.CENTER)
        addText("猪肉为主，牛羊鸭鸡鱼虾轮换；每天保证青菜、豆制品和汤。", 14f, Color.rgb(120, 113, 105), dp(4), Gravity.CENTER)

        val button = Button(this).apply {
            text = "🎲 换一套随机午餐"
            textSize = 17f
            setOnClickListener { renderMeal() }
        }
        content.addView(button, LinearLayout.LayoutParams(-1, dp(54)).apply { setMargins(0, dp(14), 0, 0) })
    }

    private fun addDishCard(dish: Dish, label: String) {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(12), dp(10), dp(12), dp(10))
            setBackgroundColor(Color.WHITE)
        }
        val image = ImageView(this).apply {
            setImageDrawable(DishIllustrationDrawable(dish.type, dish.name))
            scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
        box.addView(image, LinearLayout.LayoutParams(dp(74), dp(74)))

        val texts = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(10), 0, 0, 0) }
        val title = TextView(this).apply {
            text = "$label  ${dish.name}"
            textSize = 18f
            setTextColor(Color.rgb(55, 50, 46))
        }
        val method = TextView(this).apply {
            text = "做法：${dish.method}"
            textSize = 14f
            setTextColor(Color.rgb(95, 90, 84))
            setPadding(0, dp(5), 0, 0)
        }
        val ing = TextView(this).apply {
            text = "食材：${dish.ingredients}"
            textSize = 12f
            setTextColor(Color.rgb(135, 128, 120))
            setPadding(0, dp(4), 0, 0)
        }
        texts.addView(title); texts.addView(method); texts.addView(ing)
        box.addView(texts, LinearLayout.LayoutParams(0, -2, 1f))
        content.addView(box, LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, dp(5), 0, 0) })
    }

    private fun addNutritionCard(score: Int, main: Dish, rotating: Dish, bean: Dish, green: Dish, soup: Dish) {
        addSectionTitle("🥗 营养搭配评分")
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(14), dp(16), dp(14))
            setBackgroundColor(Color.WHITE)
        }
        val scoreText = TextView(this).apply {
            text = "$score / 100   ${scoreLabel(score)}"
            textSize = 26f
            setTextColor(Color.rgb(55, 50, 46))
            gravity = Gravity.CENTER
        }
        card.addView(scoreText)
        val detail = TextView(this).apply {
            text = "蛋白质 30分  ✓  猪肉+${rotating.name}\n蔬菜 25分  ✓  当季烧青菜\n豆制品 15分  ✓  ${bean.name}\n汤品 10分  ✓  ${soup.name}\n动物蛋白轮换 10分  ✓  荤素比例合理\n烹饪多样性 10分  ✓  炒、炖、烧搭配"
            textSize = 14f
            setTextColor(Color.rgb(85, 80, 74))
            setPadding(0, dp(10), 0, 0)
        }
        card.addView(detail)
        val note = TextView(this).apply {
            text = "评分是本APP的膳食搭配参考分，不代替专业营养评估。"
            textSize = 11f
            setTextColor(Color.rgb(145, 138, 130))
            setPadding(0, dp(10), 0, 0)
        }
        card.addView(note)
        content.addView(card, LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, dp(5), 0, 0) })
    }

    private fun nutritionScore(vararg dishes: Dish): Int {
        var score = 80
        if (dishes.count { it.type == "pork" } >= 1) score += 4
        if (dishes.any { it.type == "bean" }) score += 4
        if (dishes.any { it.type == "green" }) score += 4
        if (dishes.any { it.type == "soup" }) score += 3
        if (dishes.map { it.type }.distinct().size >= 5) score += 3
        return min(score, 98)
    }

    private fun scoreLabel(score: Int) = when {
        score >= 95 -> "营养搭配很均衡"
        score >= 90 -> "营养搭配优秀"
        else -> "营养搭配良好"
    }

    private fun addSectionTitle(text: String) = addText(text, 22f, Color.rgb(62, 53, 45), dp(12), Gravity.LEFT)

    private fun addText(text: String, size: Float, color: Int, top: Int, gravity: Int) {
        val tv = TextView(this).apply {
            this.text = text
            textSize = size
            setTextColor(color)
            this.gravity = gravity
            setPadding(dp(2), top, dp(2), dp(2))
        }
        content.addView(tv, LinearLayout.LayoutParams(-1, -2))
    }

    private fun greenMethod(name: String) = when {
        name.contains("空心菜") -> "空心菜洗净切段，蒜末爆香，大火快炒至断生，加盐即可，保持脆嫩。"
        name.contains("苋菜") -> "苋菜洗净，蒜末爆香后大火炒软，加少量盐快速出锅。"
        name.contains("丝瓜") -> "丝瓜去皮切块，蒜末爆香后大火翻炒，加少量水焖2分钟，保持清甜。"
        name.contains("油麦菜") -> "油麦菜洗净切段，蒜末爆香后大火快炒，加盐或少量蚝油。"
        name.contains("上海青") -> "上海青对半切开，蒜末爆香后大火翻炒，加少量蚝油，断生即出锅。"
        else -> "青菜洗净，蒜末爆香后大火快炒，加盐调味，断生即出锅。"
    }

    private fun greenIngredients(name: String) = when {
        name.contains("空心菜") -> "空心菜、蒜、盐"
        name.contains("苋菜") -> "苋菜、蒜、盐"
        name.contains("丝瓜") -> "丝瓜、蒜、盐"
        name.contains("油麦菜") -> "油麦菜、蒜、蚝油"
        else -> "$name、蒜、蚝油"
    }

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()

    class DishIllustrationView(context: android.content.Context, private val kind: String) : View(context) {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            paint.color = Color.rgb(244, 235, 216)
            canvas.drawRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), 28f, 28f, paint)
            paint.color = Color.WHITE
            canvas.drawOval(RectF(width * .25f, height * .22f, width * .75f, height * .75f), paint)
            paint.color = Color.rgb(190, 82, 54)
            canvas.drawCircle(width * .43f, height * .47f, min(width, height) * .11f, paint)
            paint.color = Color.rgb(83, 137, 72)
            canvas.drawCircle(width * .58f, height * .52f, min(width, height) * .10f, paint)
            paint.color = Color.rgb(224, 174, 74)
            canvas.drawCircle(width * .51f, height * .38f, min(width, height) * .08f, paint)
            paint.color = Color.rgb(125, 78, 54)
            paint.strokeWidth = 7f
            canvas.drawLine(width * .35f, height * .62f, width * .66f, height * .62f, paint)
        }
    }

    class DishIllustrationDrawable(private val type: String, private val name: String) : android.graphics.drawable.Drawable() {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        override fun draw(canvas: Canvas) {
            val w = bounds.width().toFloat(); val h = bounds.height().toFloat()
            paint.color = when (type) {
                "pork" -> Color.rgb(248, 224, 213)
                "beef", "lamb", "duck", "chicken" -> Color.rgb(248, 231, 207)
                "fish", "shrimp" -> Color.rgb(214, 236, 241)
                "bean" -> Color.rgb(235, 226, 201)
                "green" -> Color.rgb(218, 238, 213)
                else -> Color.rgb(231, 231, 231)
            }
            canvas.drawRoundRect(RectF(0f, 0f, w, h), 24f, 24f, paint)
            paint.color = Color.WHITE
            canvas.drawOval(RectF(w*.12f, h*.18f, w*.88f, h*.84f), paint)
            val food = when (type) {
                "green" -> Color.rgb(74, 145, 68)
                "bean" -> Color.rgb(201, 154, 91)
                "fish", "shrimp" -> Color.rgb(224, 132, 96)
                "soup" -> Color.rgb(225, 177, 73)
                else -> Color.rgb(177, 76, 55)
            }
            paint.color = food
            for (i in 0..3) {
                val x = w * (.32f + (i % 2) * .25f)
                val y = h * (.39f + (i / 2) * .19f)
                canvas.drawCircle(x, y, min(w, h)*.09f, paint)
            }
            paint.color = Color.rgb(70, 120, 65)
            paint.strokeWidth = 4f
            canvas.drawLine(w*.28f, h*.65f, w*.72f, h*.65f, paint)
        }
        override fun setAlpha(alpha: Int) { paint.alpha = alpha }
        override fun setColorFilter(colorFilter: android.graphics.ColorFilter?) { paint.colorFilter = colorFilter }
        @Deprecated("Deprecated in Android") override fun getOpacity() = android.graphics.PixelFormat.TRANSLUCENT
    }
}
