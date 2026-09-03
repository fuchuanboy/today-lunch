package com.fuchuanboy.todaylunch

data class CatalogDish(
    val name: String,
    val protein: String,
    val vegetables: List<String>,
    val ingredients: String,
    val steps: List<String>,
    val score: Int,
    val imageQuery: String
)

object ExpandedDishDatabase {
    private val meats = listOf(
        "猪里脊" to "猪肉", "五花肉" to "猪肉", "猪梅肉" to "猪肉",
        "牛里脊" to "牛肉", "肥牛片" to "牛肉", "牛腩" to "牛肉",
        "羊肉片" to "羊肉", "鸡腿肉" to "鸡肉", "鸡胸肉" to "鸡肉",
        "鸭腿肉" to "鸭肉", "鲈鱼" to "鱼", "巴沙鱼" to "鱼", "虾仁" to "虾"
    )
    private val styles = listOf("鱼香", "青椒", "黑椒", "洋葱", "芹菜", "香菇", "番茄", "蒜香", "葱爆", "孜然", "红烧", "家常", "照烧", "宫保", "糖醋", "酱香")
    private val vegetables = listOf("青椒", "彩椒", "洋葱", "芹菜", "胡萝卜", "番茄", "西兰花", "黄瓜", "木耳", "香菇", "土豆", "冬瓜", "丝瓜", "豇豆", "茄子", "莲藕", "玉米笋", "山药", "莴笋", "荷兰豆", "毛豆", "南瓜", "白萝卜", "白菜")
    private val plantNames = listOf("家常豆腐", "麻婆豆腐", "香煎豆腐", "豆干炒芹菜", "木耳炒豆皮", "香菇烧豆腐", "青椒炒豆皮", "番茄烧豆腐", "毛豆烧豆腐", "豆腐蒸蛋")
    private val plantBases = listOf("老豆腐", "嫩豆腐", "北豆腐", "豆干", "豆腐皮", "千张", "毛豆", "腐竹")
    private val greens = listOf("上海青", "小油菜", "空心菜", "苋菜", "菠菜", "生菜", "油麦菜", "芥蓝", "菜心", "小白菜", "大白菜")

    val all: List<CatalogDish> by lazy {
        val out = ArrayList<CatalogDish>(1300)
        var n = 0
        for ((meat, protein) in meats) {
            for (style in styles) {
                for (i in vegetables.indices) {
                    if (n >= 1200) break
                    val v1 = vegetables[i]
                    val v2 = vegetables[(i + 7) % vegetables.size]
                    val dishName = if (style == "红烧" || style == "家常") "$style${meat}配$v1" else "$style${meat}配$v1"
                    val ingredientText = "$meat、$v1、$v2、葱姜蒜、生抽、食用油、盐、淀粉"
                    val steps = listOf(
                        "将${meat}切片或切条，加入少量生抽和淀粉抓匀腌制。",
                        "将${v1}和${v2}洗净，分别切成适合烹调的大小。",
                        "热锅少油，将${meat}炒至变色后盛出。",
                        "锅中爆香葱姜蒜，加入${v1}和${v2}翻炒至断生。",
                        "回锅${meat}，加入生抽和盐，大火翻匀后出锅。"
                    )
                    out += CatalogDish(dishName, protein, listOf(v1, v2), ingredientText, steps, 88 + (n % 11), dishName)
                    n++
                }
                if (n >= 1200) break
            }
            if (n >= 1200) break
        }
        out
    }

    val plantProtein: List<CatalogDish> by lazy {
        val out = ArrayList<CatalogDish>()
        for (i in plantBases.indices) {
            for (j in vegetables.indices) {
                if (out.size >= 80) break
                val base = plantBases[i]
                val veg = vegetables[j]
                val name = "${plantNames[i % plantNames.size]}·${veg}"
                out += CatalogDish(
                    name, "豆制品", listOf(veg),
                    "$base、$veg、葱姜蒜、生抽、蚝油、食用油、盐",
                    listOf(
                        "将$base切块或切段，$veg洗净切好。",
                        "热锅少油，将$base煎至表面微黄。",
                        "加入葱姜蒜炒香，再加入$veg翻炒。",
                        "加入少量清水、生抽和蚝油，小火烧至入味。"
                    ), 91 + (i + j) % 8, name
                )
            }
            if (out.size >= 80) break
        }
        out
    }

    val greenVegetables: List<CatalogDish> by lazy {
        val out = ArrayList<CatalogDish>()
        for (green in greens) {
            for (style in listOf("蒜蓉", "香菇炒", "蚝油", "清炒", "上汤")) {
                val name = if (style == "香菇炒") "香菇炒$green" else "$style$green"
                val extra = if (style == "香菇炒") "、鲜香菇" else if (style == "蚝油") "、蚝油" else ""
                out += CatalogDish(name, "绿叶蔬菜", listOf(green), "$green、大蒜、食用油、盐$extra", listOf("将$green洗净沥干，大蒜切末。", "热锅少油爆香蒜末。", "加入$green大火快速翻炒至刚断生。", "根据菜式加入盐和调味料，立即出锅。"), 92 + out.size % 7, name)
            }
        }
        out
    }

    val soups = listOf(
        CatalogDish("冬瓜虾皮汤", "汤", listOf("冬瓜"), "冬瓜、虾皮、姜、葱、盐", listOf("冬瓜切片。", "姜和虾皮炒香。", "加水煮开后放入冬瓜。", "煮至冬瓜透明，调盐撒葱花。"), 96, "冬瓜虾皮汤"),
        CatalogDish("番茄蛋花汤", "汤", listOf("番茄"), "番茄、鸡蛋、葱、盐、香油", listOf("番茄切块炒出汁。", "加入清水煮开。", "鸡蛋打散沿锅边淋入。", "调盐，滴少量香油。"), 96, "番茄蛋花汤"),
        CatalogDish("紫菜豆腐汤", "汤", listOf("紫菜", "豆腐"), "紫菜、豆腐、葱、盐、白胡椒", listOf("豆腐切小块。", "水开后加入豆腐煮几分钟。", "放入紫菜。", "加盐和白胡椒，撒葱花。"), 95, "紫菜豆腐汤"),
        CatalogDish("玉米胡萝卜排骨汤", "汤", listOf("玉米", "胡萝卜"), "排骨、玉米、胡萝卜、姜、盐", listOf("排骨焯水洗净。", "排骨加姜炖煮。", "加入玉米和胡萝卜。", "炖至排骨熟透，调盐。"), 97, "玉米胡萝卜排骨汤"),
        CatalogDish("丝瓜蛋汤", "汤", listOf("丝瓜"), "丝瓜、鸡蛋、姜、盐、香油", listOf("丝瓜去皮切片。", "锅中加水和姜片烧开。", "放入丝瓜煮软。", "淋入蛋液，调盐和香油。"), 95, "丝瓜蛋汤")
    )
}
