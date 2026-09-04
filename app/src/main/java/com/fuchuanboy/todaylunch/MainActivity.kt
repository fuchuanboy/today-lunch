package com.fuchuanboy.todaylunch

import android.app.Activity
import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
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

    private lateinit var hero: ImageView
    private lateinit var scoreView: TextView
    private lateinit var listBox: LinearLayout
    private val random = Random(System.currentTimeMillis())

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
        Recipe("清蒸鲈鱼", "鱼", listOf("葱", "姜"), "鲈鱼、葱、姜、蒸鱼豉油、食用油", listOf("鱼清理后两面划刀，放姜片。", "水开后上锅蒸8至10分钟。", "倒掉蒸出的水，铺葱丝。", "淋蒸鱼豉油和热油。"), 96, "fish"),
        Recipe("番茄炒蛋", "鸡蛋", listOf("番茄"), "鸡蛋、番茄、葱、盐、食用油", listOf("鸡蛋打散炒熟盛出。", "番茄切块炒出汁。", "鸡蛋回锅翻匀。", "加盐和葱花出锅。"), 92, "egg"),
        Recipe("家常豆腐", "豆制品", listOf("青椒", "木耳"), "老豆腐、青椒、木耳、葱姜蒜、生抽、蚝油、淀粉", listOf("豆腐切块煎至两面金黄。", "青椒和木耳切好。", "葱姜蒜爆香后下青椒木耳。", "加入豆腐、生抽和蚝油，加少量水烧至入味。"), 94, "tofu"),
        Recipe("麻婆豆腐", "豆制品", listOf("青蒜", "辣椒"), "嫩豆腐、牛肉末、豆瓣酱、花椒、青蒜、辣椒", listOf("豆腐切块焯水。", "牛肉末炒散，加入豆瓣酱和花椒炒香。", "加水放入豆腐，小火烧入味。", "淀粉勾薄芡，撒青蒜。"), 93, "tofu"),
        Recipe("蒜蓉上海青", "绿叶蔬菜", listOf("上海青"), "上海青、大蒜、食用油、盐", listOf("上海青洗净沥干，大蒜切末。", "热锅少油爆香蒜末。", "加入上海青大火快速翻炒至刚断生。", "加盐调味立即出锅。"), 94, "green"),
        Recipe("香菇炒油麦菜", "绿叶蔬菜", listOf("油麦菜", "香菇"), "油麦菜、鲜香菇、大蒜、蚝油、盐、食用油", listOf("油麦菜洗净切段，香菇切片。", "蒜末爆香，先下香菇炒软。", "加入油麦菜大火翻炒。", "加蚝油和少量盐，炒至刚断生。"), 95, "green"),
        Recipe("清炒空心菜", "绿叶蔬菜", listOf("空心菜"), "空心菜、大蒜、盐、食用油", listOf("空心菜洗净切段，大蒜切末。", "热锅下油爆香蒜末。", "大火加入空心菜快速翻炒。", "加盐炒匀，保持脆嫩后出锅。"), 94, "green"),
        Recipe("蚝油生菜", "绿叶蔬菜", listOf("生菜"), "生菜、大蒜、蚝油、生抽、食用油", listOf("生菜洗净沥干。", "水开后焯生菜十几秒，立即捞出。", "蒜末爆香，加入蚝油和少量生抽。", "将料汁淋在生菜上。"), 93, "green"),
        Recipe("番茄西兰花", "蔬菜", listOf("番茄", "西兰花"), "番茄、西兰花、大蒜、盐、食用油", listOf("西兰花切小朵焯水，番茄切块。", "蒜末爆香，加入番茄炒出汁。", "放入西兰花翻炒。", "加盐调味，炒匀出锅。"), 95, "vegetable"),
        Recipe("酸辣土豆丝", "蔬菜", listOf("土豆", "青椒"), "土豆、青椒、干辣椒、醋、盐、蒜", listOf("土豆切细丝，清水冲洗去淀粉。", "干辣椒和蒜末爆香。", "加入土豆丝和青椒大火快炒。", "沿锅边淋醋，加盐翻匀出锅。"), 92, "vegetable"),
        Recipe("凉拌黄瓜", "蔬菜", listOf("黄瓜"), "黄瓜、大蒜、生抽、醋、香油", listOf("黄瓜拍裂切段。", "蒜切末。", "加入生抽、醋和少量香油。", "拌匀后冷藏片刻食用。"), 90, "vegetable"),
        Recipe("冬瓜虾皮汤", "汤", listOf("冬瓜"), "冬瓜、虾皮、姜、葱、盐", listOf("冬瓜切片。", "姜和虾皮炒香。", "加水煮开后放入冬瓜。", "煮至冬瓜透明，调盐撒葱花。"), 96, "soup"),
        Recipe("番茄蛋花汤", "汤", listOf("番茄"), "番茄、鸡蛋、葱、盐、香油", listOf("番茄切块炒出汁。", "加入清水煮开。", "鸡蛋打散沿锅边淋入。", "调盐，滴少量香油。"), 96, "soup"),
        Recipe("紫菜豆腐汤", "汤", listOf("紫菜", "豆腐"), "紫菜、豆腐、葱、盐、白胡椒", listOf("豆腐切小块。", "水开后加入豆腐煮几分钟。", "放入紫菜。", "加盐和白胡椒，撒葱花。"), 95, "soup"),
        Recipe("丝瓜蛋汤", "汤", listOf("丝瓜"), "丝瓜、鸡蛋、姜、盐、香油", listOf("丝瓜去皮切片。", "锅中加水和姜片烧开。", "放入丝瓜煮软。", "淋入蛋液，调盐和香油。"), 95, "soup")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scroll = ScrollView(this)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 24, 20, 24)
            setBackgroundColor(Color.rgb(246, 250, 247))
        }
        val title = TextView(this).apply {
            text = "今天中午吃什么？"
            textSize = 28f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.rgb(24, 91, 51))
            setPadding(0, 0, 0, 6)
        }
        root.addView(title)
        val subtitle = TextView(this).apply {
            text = "河南家常 · 营养搭配 · 四菜一汤"
            textSize = 15f
            setTextColor(Color.GRAY)
            setPadding(0, 0, 0, 14)
        }
        root.addView(subtitle)
        hero = ImageView(this).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            setBackgroundColor(Color.rgb(220, 235, 224))
        }
        root.addView(hero, LinearLayout.LayoutParams(-1, 240).apply { setMargins(0, 0, 0, 18) })
        val randomButton = Button(this).apply {
            text = "🎲  随机一桌"
            textSize = 18f
            setOnClickListener { makeMeal() }
        }
        root.addView(randomButton, LinearLayout.LayoutParams(-1, 58).apply { setMargins(0, 0, 0, 16) })
        scoreView = TextView(this).apply {
            textSize = 18f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.rgb(30, 120, 60))
            gravity = Gravity.CENTER
            setPadding(0, 8, 0, 18)
        }
        root.addView(scoreView)
        listBox = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        root.addView(listBox)
        scroll.addView(root)
        setContentView(scroll)
        makeMeal()
    }

    private fun makeMeal() {
        val animal = recipes.filter { it.protein in setOf("猪肉", "牛肉", "羊肉", "鸡肉", "鸭肉", "鱼", "虾") }
        val plant = recipes.filter { it.protein == "豆制品" }
        val green = recipes.filter { it.protein == "绿叶蔬菜" }
        val vitamin = recipes.filter { it.protein == "蔬菜" }
        val chosen = mutableListOf<Recipe>()
        chosen += animal.random(random)
        chosen += plant.random(random)
        chosen += green.random(random)
        val used = chosen.flatMap { it.vegetables }.toSet()
        val vitaminPick = vitamin.filter { r -> r.vegetables.none { it in used } }.randomOrNull(random) ?: vitamin.random(random)
        chosen += vitaminPick
        val soup = recipes.filter { it.protein == "汤" }.filter { it.vegetables.none { v -> chosen.flatMap { it.vegetables }.contains(v) } }.randomOrNull(random)
            ?: recipes.filter { it.protein == "汤" }.random(random)
        val meal = chosen + soup
        listBox.removeAllViews()
        meal.forEachIndexed { index, r -> addCard(r, index + 1) }
        val avg = meal.map { it.score }.average().toInt()
        val vegetables = chosen.flatMap { it.vegetables }
        val repeat = vegetables.size != vegetables.toSet().size
        scoreView.text = "营养搭配评分  $avg / 100  ★★★★★\n蔬菜重复：${if (repeat) "已优化" else "无"} · 蛋白质：${chosen.map { it.protein }.distinct().joinToString("、")}"
        loadImage(hero, chosen.first().image, chosen.first().name)
    }

    private fun addCard(r: Recipe, number: Int) {
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(16, 12, 16, 12)
            setBackgroundColor(Color.WHITE)
            isClickable = true
            setOnClickListener { showDetail(r) }
        }
        val text = TextView(this).apply {
            text = "$number  ${r.name}\n${r.protein} · ${r.vegetables.joinToString("、")}\n营养评分 ${r.score}"
            textSize = 17f
            setTextColor(Color.DKGRAY)
        }
        box.addView(text, LinearLayout.LayoutParams(0, -2, 1f))
        val arrow = TextView(this).apply {
            text = "›"
            textSize = 30f
            gravity = Gravity.CENTER_VERTICAL
            setTextColor(Color.GRAY)
        }
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
