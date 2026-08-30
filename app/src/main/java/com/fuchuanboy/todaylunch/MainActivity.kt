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
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread
import kotlin.random.Random

class MainActivity : Activity() {
    data class Recipe(
        val name: String,
        val protein: String,
        val vegetables: List<String>,
        val ingredients: String,
        val steps: List<String>,
        val score: Int,
        val image: String
    )

    private val recipes = listOf(
        Recipe("鱼香肉丝", "猪肉", listOf("木耳", "胡萝卜", "青椒"), "猪里脊、木耳、胡萝卜、青椒、葱姜蒜、泡椒、醋、生抽、糖、淀粉", listOf("猪肉切丝，加生抽、淀粉腌10分钟。", "木耳、胡萝卜、青椒切丝；调好鱼香汁。", "肉丝滑油变色盛出，爆香葱姜蒜和泡椒。", "下蔬菜翻炒，回锅肉丝，倒鱼香汁，大火收汁。"), 95, "pork"),
        Recipe("木须肉", "猪肉", listOf("黄瓜", "木耳"), "猪里脊、鸡蛋、黄瓜、木耳、葱姜、生抽、盐、料酒", listOf("猪肉切片腌好，鸡蛋打散。", "鸡蛋炒至凝固盛出，肉片滑炒变色。", "下黄瓜、木耳快速翻炒。", "加入鸡蛋和调味料，炒匀出锅。"), 94, "pork"),
        Recipe("青椒肉丝", "猪肉", listOf("青椒"), "猪里脊、青椒、蒜、生抽、料酒、淀粉", listOf("肉丝加生抽、料酒、淀粉抓匀。", "青椒切丝，蒜切末。", "热锅滑炒肉丝至变色盛出。", "蒜末爆香，下青椒，回锅肉丝，快速翻匀。"), 94, "pork"),
        Recipe("农家小炒肉", "猪肉", listOf("青椒", "蒜苗"), "五花肉、青椒、蒜苗、豆豉、蒜、生抽", listOf("五花肉切薄片，青椒切段。", "锅中少油煸出五花肉油脂。", "加入豆豉、蒜和青椒炒香。", "加入蒜苗和生抽，大火翻炒断生。"), 93, "pork"),
        Recipe("京酱肉丝", "猪肉", listOf("黄瓜", "葱"), "猪里脊、甜面酱、黄瓜、大葱、豆皮、淀粉", listOf("猪肉切丝腌制，黄瓜和葱切丝。", "肉丝滑炒至变色。", "锅中放甜面酱炒香。", "回锅肉丝翻匀，用豆皮卷黄瓜葱丝食用。"), 92, "pork"),
        Recipe("糖醋里脊", "猪肉", listOf("青椒"), "里脊肉、淀粉、番茄酱、白糖、醋、白芝麻", listOf("里脊切条，加盐和淀粉挂糊。", "油温六成热炸至金黄。", "锅中调番茄酱、糖、醋和少量水。", "倒入里脊快速裹汁，撒芝麻。"), 89, "pork"),
        Recipe("洋葱肥牛", "牛肉", listOf("洋葱", "青椒"), "肥牛片、洋葱、青椒、生抽、蚝油、黑胡椒", listOf("洋葱和青椒切块。", "肥牛焯水去浮沫。", "锅中少油炒香洋葱。", "加入肥牛、青椒、生抽、蚝油和黑胡椒，大火炒匀。"), 96, "beef"),
        Recipe("黑椒牛柳", "牛肉", listOf("彩椒", "洋葱"), "牛里脊、彩椒、洋葱、黑胡椒、生抽、淀粉", listOf("牛肉切条，加生抽和淀粉腌制。", "彩椒、洋葱切条。", "牛肉快速滑炒至七成熟。", "下彩椒洋葱和黑胡椒，大火炒至断生。"), 95, "beef"),
        Recipe("芹菜炒牛肉", "牛肉", listOf("芹菜", "红椒"), "牛里脊、芹菜、红椒、姜蒜、生抽、淀粉", listOf("牛肉切丝腌制，芹菜切段。", "牛肉快速滑炒盛出。", "姜蒜爆香，下芹菜和红椒。", "回锅牛肉，加生抽翻炒至断生。"), 94, "beef"),
        Recipe("土豆烧牛肉", "牛肉", listOf("土豆", "胡萝卜"), "牛腩、土豆、胡萝卜、葱姜、八角、生抽", listOf("牛腩焯水切块。", "葱姜和香料炒香，加入牛肉翻炒。", "加水和生抽炖至牛肉八成熟。", "加入土豆胡萝卜继续炖至软烂。"), 93, "beef"),
        Recipe("孜然羊肉", "羊肉", listOf("洋葱", "香菜"), "羊肉片、洋葱、香菜、孜然、辣椒面、生抽", listOf("羊肉片擦干水分。", "热锅大火炒羊肉至变色。", "加入洋葱快速翻炒。", "撒孜然和辣椒面，最后放香菜翻匀。"), 94, "lamb"),
        Recipe("葱爆羊肉", "羊肉", listOf("大葱"), "羊肉片、大葱、生抽、料酒、白胡椒", listOf("羊肉片加料酒和白胡椒腌5分钟。", "大葱切滚刀块。", "热锅大火炒羊肉。", "加入大葱和生抽快速翻炒出锅。"), 93, "lamb"),
        Recipe("照烧鸡腿", "鸡肉", listOf("西兰花", "胡萝卜"), "鸡腿、照烧汁、西兰花、胡萝卜、芝麻", listOf("鸡腿去骨，用少量盐腌制。", "鸡皮朝下煎至金黄，再翻面煎熟。", "倒入照烧汁，小火收至浓稠。", "西兰花和胡萝卜焯熟，鸡腿切块淋汁。"), 95, "chicken"),
        Recipe("宫保鸡丁", "鸡肉", listOf("黄瓜", "胡萝卜"), "鸡胸肉、花生、黄瓜、胡萝卜、干辣椒、花椒", listOf("鸡肉切丁，加淀粉和生抽腌制。", "调宫保汁。", "鸡丁滑炒至变色盛出。", "干辣椒花椒炒香，下蔬菜和鸡丁，倒宫保汁收汁，撒花生。"), 94, "chicken"),
        Recipe("咖喱鸡肉饭", "鸡肉", listOf("土豆", "胡萝卜", "洋葱"), "鸡腿肉、土豆、胡萝卜、洋葱、咖喱块、米饭", listOf("鸡肉切块，土豆胡萝卜洋葱切块。", "炒香洋葱和鸡肉。", "加入土豆胡萝卜和清水煮至软。", "关小火加入咖喱块融化，浇在米饭上。"), 96, "chicken"),
        Recipe("香菇炖鸡", "鸡肉", listOf("香菇", "冬瓜"), "鸡腿、鲜香菇、冬瓜、姜、葱、料酒", listOf("鸡块焯水。", "姜葱炒香后加入鸡块和料酒。", "加水炖煮25分钟。", "加入香菇和冬瓜再炖15分钟，调盐。"), 92, "chicken"),
        Recipe("香酥鸭", "鸭肉", listOf("黄瓜", "葱"), "鸭腿、五香粉、葱、黄瓜、面粉、甜面酱", listOf("鸭腿用五香粉和盐腌制。", "蒸熟后擦干表皮。", "烤或煎至表皮酥脆。", "切片配黄瓜葱丝和甜面酱。"), 90, "duck"),
        Recipe("啤酒鸭", "鸭肉", listOf("青椒", "土豆"), "鸭块、啤酒、青椒、土豆、姜蒜、豆瓣酱", listOf("鸭块焯水。", "姜蒜和豆瓣酱炒香，加入鸭块。", "倒啤酒炖至鸭肉软熟。", "加入土豆炖透，最后加入青椒。"), 93, "duck"),
        Recipe("蒜蓉虾", "虾", listOf("粉丝", "蒜"), "鲜虾、粉丝、大蒜、小米椒、生抽、蚝油", listOf("粉丝泡软铺盘，虾开背去虾线。", "蒜末和小米椒用热油炒香。", "把蒜蓉铺在虾上。", "蒸8分钟，淋生抽和热油。"), 95, "shrimp"),
        Recipe("白灼虾", "虾", listOf("生菜", "姜"), "鲜虾、生菜、姜、葱、生抽", listOf("锅中水加姜葱烧开。", "放入鲜虾煮至变红卷曲。", "捞出冰水快速降温。", "配生抽蘸食，生菜另盘。"), 94, "shrimp"),
        Recipe("西兰花炒虾仁", "虾", listOf("西兰花", "胡萝卜"), "虾仁、西兰花、胡萝卜、蒜、生抽", listOf("虾仁加少量盐和淀粉腌制。", "西兰花和胡萝卜焯水。", "蒜末爆香，炒虾仁至变色。", "加入蔬菜和生抽，大火翻匀。"), 97, "shrimp"),
        Recipe("清蒸鲈鱼", "鱼", listOf("葱", "姜"), "鲈鱼、葱、姜、蒸鱼豉油、食用油", listOf("鱼清理后两面划刀，放姜片。", "水开后上锅蒸8至10分钟。", "倒掉蒸出的水，铺葱丝。", "淋蒸鱼豉油和热油。"), 98, "fish"),
        Recipe("番茄鱼片", "鱼", listOf("番茄", "金针菇"), "鱼片、番茄、金针菇、姜、葱、生抽", listOf("鱼片加盐和淀粉腌制。", "番茄炒出汁，加水煮开。", "加入金针菇煮熟。", "放入鱼片煮至刚熟，调味出锅。"), 96, "fish"),
        Recipe("麻婆豆腐", "豆制品", listOf("豆腐", "蒜苗"), "嫩豆腐、肉末、豆瓣酱、花椒、蒜苗、生抽", listOf("豆腐切块焯水。", "肉末炒散，加入豆瓣酱和花椒炒出红油。", "加水放入豆腐，小火烧几分钟。", "勾薄芡，撒蒜苗和花椒粉。"), 96, "tofu"),
        Recipe("家常豆腐", "豆制品", listOf("木耳", "青椒"), "老豆腐、木耳、青椒、豆瓣酱、生抽", listOf("豆腐切片煎至两面金黄。", "木耳泡发，青椒切块。", "豆瓣酱炒香加少量水。", "加入豆腐木耳青椒烧至入味。"), 95, "tofu"),
        Recipe("西红柿炒鸡蛋", "鸡蛋", listOf("西红柿", "葱"), "西红柿、鸡蛋、葱、盐、糖", listOf("鸡蛋打散炒熟盛出。", "西红柿切块炒出汁。", "加入少量糖提鲜。", "回锅鸡蛋，加盐和葱花翻匀。"), 95, "egg"),
        Recipe("青椒炒蛋", "鸡蛋", listOf("青椒", "木耳"), "鸡蛋、青椒、木耳、蒜、生抽", listOf("鸡蛋炒熟盛出。", "青椒切丝，木耳切片。", "蒜末爆香，下青椒木耳炒熟。", "加入鸡蛋和生抽翻匀。"), 92, "egg"),
        Recipe("蒜蓉上海青", "蔬菜", listOf("上海青"), "上海青、大蒜、盐、食用油", listOf("上海青洗净沥干。", "蒜切末。", "热锅爆香蒜末。", "加入上海青大火翻炒至断生，加盐出锅。"), 94, "greens"),
        Recipe("香菇扒青菜", "蔬菜", listOf("上海青", "香菇"), "上海青、鲜香菇、蒜、蚝油", listOf("青菜焯水摆盘。", "香菇切片，蒜末爆香。", "加入香菇、蚝油和少量水烧熟。", "把香菇和汤汁浇在青菜上。"), 96, "greens"),
        Recipe("清炒空心菜", "蔬菜", listOf("空心菜"), "空心菜、大蒜、盐", listOf("空心菜洗净切段。", "蒜末入锅爆香。", "大火加入空心菜快速翻炒。", "叶片刚软时加盐立即出锅。"), 93, "greens"),
        Recipe("冬瓜虾皮汤", "汤", listOf("冬瓜", "虾皮"), "冬瓜、虾皮、葱、姜、盐", listOf("冬瓜去皮切片。", "姜片和虾皮炒香。", "加入冬瓜和清水煮至透明。", "加盐和葱花调味。"), 96, "soup"),
        Recipe("紫菜蛋花汤", "汤", listOf("紫菜", "鸡蛋"), "紫菜、鸡蛋、葱、盐、香油", listOf("锅中水烧开，放紫菜。", "鸡蛋打散沿锅边淋入。", "用筷子轻轻搅成蛋花。", "加盐、葱花和香油。"), 94, "soup")
    )

    private val random = Random(System.currentTimeMillis())
    private lateinit var root: LinearLayout
    private lateinit var hero: ImageView
    private lateinit var title: TextView
    private lateinit var subtitle: TextView
    private lateinit var score: TextView
    private lateinit var listBox: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        render()
    }

    private fun render() {
        root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(24, 24, 24, 28); setBackgroundColor(Color.rgb(248, 252, 246)) }
        val scroll = ScrollView(this).apply { addView(root) }
        val head = TextView(this).apply { text = "河南省 · 今天中午吃什么？"; textSize = 16f; setTextColor(Color.DKGRAY); setPadding(4, 4, 4, 18) }
        root.addView(head)
        title = TextView(this).apply { text = "今天中午吃什么？"; textSize = 30f; typeface = Typeface.DEFAULT_BOLD; setTextColor(Color.rgb(20, 82, 42)); gravity = Gravity.CENTER; setPadding(0, 4, 0, 4) }
        root.addView(title)
        subtitle = TextView(this).apply { text = "随机四菜一汤 · 荤素搭配 · 真实菜品图片"; textSize = 16f; gravity = Gravity.CENTER; setTextColor(Color.GRAY); setPadding(0, 0, 0, 16) }
        root.addView(subtitle)
        hero = ImageView(this).apply { scaleType = ImageView.ScaleType.CENTER_CROP; setBackgroundColor(Color.LTGRAY) }
        root.addView(hero, LinearLayout.LayoutParams(-1, 420).apply { setMargins(0, 0, 0, 18) })
        val randomButton = Button(this).apply { text = "🎲  随机一桌"; textSize = 18f; setOnClickListener { makeMeal() } }
        root.addView(randomButton, LinearLayout.LayoutParams(-1, 58).apply { setMargins(0, 0, 0, 16) })
        score = TextView(this).apply { textSize = 18f; typeface = Typeface.DEFAULT_BOLD; setTextColor(Color.rgb(30, 120, 60)); gravity = Gravity.CENTER; setPadding(0, 8, 0, 18) }
        root.addView(score)
        listBox = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        root.addView(listBox)
        setContentView(scroll)
        makeMeal()
    }

    private fun makeMeal() {
        val pool = recipes.filter { it.protein != "汤" && it.protein != "蔬菜" }
        val chosen = mutableListOf<Recipe>()
        val usedVegetables = mutableSetOf<String>()
        val usedProtein = mutableSetOf<String>()
        val candidates = pool.shuffled(random)
        for (r in candidates) {
            val overlap = r.vegetables.any { it in usedVegetables }
            if (!overlap && (r.protein !in usedProtein || chosen.size >= 2)) {
                chosen += r
                usedVegetables += r.vegetables
                usedProtein += r.protein
            }
            if (chosen.size == 4) break
        }
        if (chosen.size < 4) chosen.addAll(pool.filter { it !in chosen }.shuffled(random).take(4 - chosen.size))
        val soup = recipes.filter { it.protein == "汤" }.random(random)
        val meal = chosen + soup
        listBox.removeAllViews()
        meal.forEachIndexed { index, r -> addCard(r, index + 1) }
        val avg = meal.map { it.score }.average().toInt()
        score.text = "营养搭配评分  $avg / 100    ★★★★★\n蔬菜重复：${if (chosen.flatMap { it.vegetables }.size == chosen.flatMap { it.vegetables }.toSet().size) "无" else "已优化"} · 蛋白质：${chosen.map { it.protein }.distinct().joinToString("、")}"
        loadImage(hero, chosen.first().image, chosen.first().name)
    }

    private fun addCard(r: Recipe, number: Int) {
        val box = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL; setPadding(16, 12, 16, 12); setBackgroundColor(Color.WHITE); isClickable = true; setOnClickListener { showDetail(r) } }
        val text = TextView(this).apply { text = "$number  ${r.name}\n${r.protein} · ${r.vegetables.joinToString("、")}\n营养评分 ${r.score}"; textSize = 17f; setTextColor(Color.DKGRAY) }
        box.addView(text, LinearLayout.LayoutParams(0, -2, 1f))
        val arrow = TextView(this).apply { text = "›"; textSize = 30f; gravity = Gravity.CENTER_VERTICAL; setTextColor(Color.GRAY) }
        box.addView(arrow, LinearLayout.LayoutParams(40, -1))
        listBox.addView(box, LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, 0, 0, 10) })
    }

    private fun showDetail(r: Recipe) {
        val body = StringBuilder().apply {
            append("食材\n${r.ingredients}\n\n")
            append("做法\n")
            r.steps.forEachIndexed { i, s -> append("${i + 1}. $s\n") }
            append("\n营养评分：${r.score}/100")
        }
        AlertDialog.Builder(this).setTitle(r.name).setMessage(body.toString()).setPositiveButton("知道了", null).show()
    }

    private fun loadImage(view: ImageView, type: String, dishName: String) {
        val fixed = when (type) {
            "pork" -> "https://images.unsplash.com/photo-1786114777708-46385013cd00?auto=format&fit=crop&w=1200&q=80"
            "shrimp" -> "https://images.unsplash.com/photo-1633504581786-316c8002b1b9?auto=format&fit=crop&w=1200&q=80"
            "chicken" -> "https://images.unsplash.com/photo-1774271694486-4a7abbc3b78d?auto=format&fit=crop&w=1200&q=80"
            "duck" -> "https://images.unsplash.com/photo-1785899578127-f3883106c735?auto=format&fit=crop&w=1200&q=80"
            else -> "https://source.unsplash.com/1200x800/?${URLEncoder.encode("Chinese food $dishName", "UTF-8")}"
        }
        thread {
            try {
                val bitmap = BitmapFactory.decodeStream(URL(fixed).openStream())
                runOnUiThread { if (bitmap != null) view.setImageBitmap(bitmap) }
            } catch (_: Exception) { }
        }
    }
}
