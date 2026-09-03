package com.fuchuanboy.todaylunch

/**
 * Structured dish-combination catalog.
 * Each generated record keeps the protein, plant protein, green vegetable,
 * vitamin vegetable and cooking method as separate fields so the meal picker
 * can enforce nutrition and vegetable non-overlap rules instead of inventing
 * meaningless strings at runtime.
 */
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
        "猪里脊", "五花肉", "猪梅肉", "牛里脊", "肥牛片", "牛腩",
        "羊肉片", "鸡腿肉", "鸡胸肉", "鸭腿肉", "鲈鱼片", "巴沙鱼片", "虾仁"
    )
    private val meatNames = listOf(
        "鱼香", "青椒", "黑椒", "洋葱", "芹菜", "香菇", "番茄", "蒜香",
        "葱爆", "孜然", "红烧", "家常", "照烧", "宫保", "糖醋", "酱香"
    )
    private val vegetables = listOf(
        "青椒", "彩椒", "洋葱", "芹菜", "胡萝卜", "番茄", "西兰花", "黄瓜",
        "木耳", "香菇", "土豆", "冬瓜", "丝瓜", "豇豆", "茄子", "莲藕",
        "玉米笋", "山药", "莴笋", "荷兰豆", "毛豆", "南瓜", "白萝卜", "白菜"
    )
    private val greens = listOf(
        "上海青", "小油菜", "空心菜", "苋菜", "菠菜", "生菜", "油麦菜", "芥蓝",
        "菜心", "小白菜", "大白菜", "西兰花苗"
    )
    private val plantProteins = listOf(
        "北豆腐", "嫩豆腐", "豆干", "香干", "千张", "毛豆", "腐竹", "豆皮"
    )
    private val methods = listOf(
        "家常炒", "蒜香炒", "红烧", "酱烧", "黑椒炒", "葱爆", "糖醋", "清炒"
    )

    /** More than 1000 deterministic, structured records. */
    val all: List<CatalogDish> by lazy {
        val out = ArrayList<CatalogDish>(1400)
        var index = 0
        for (meat in meats) {
            for (main in meatNames) {
                for (vegIndex in 0 until 6) {
                    val v1 = vegetables[(index + vegIndex) % vegetables.size]
                    val v2 = vegetables[(index + vegIndex + 7) % vegetables.size]
                    if (v1 == v2) continue
                    val method = methods[(index + vegIndex) % methods.size]
                    val displayProtein = when {
                        meat.contains("猪") -> "猪肉"
                        meat.contains("牛") -> "牛肉"
                        meat.contains("羊") -> "羊肉"
                        meat.contains("鸡") -> "鸡肉"
                        meat.contains("鸭") -> "鸭肉"
                        meat.contains("鱼") -> "鱼"
                        else -> "虾"
                    }
                    val name = "$main${meat.removeSuffix("片").removeSuffix("肉")}配${v1}"
                    out += CatalogDish(
                        name = name,
                        protein = displayProtein,
                        vegetables = listOf(v1, v2),
                        ingredients = "$meat、$v1、$v2、葱姜蒜、生抽、食用油、盐、淀粉",
                        steps = listOf(
                            "$meat切片或切条，加少量生抽、淀粉抓匀腌制。",
                            "$v1和$v2洗净切成适合快炒的大小。",
                            "热锅少油，先将$meat炒至变色后盛出。",
                            "锅中爆香葱姜蒜，加入$v1和$v2翻炒至断生。",
                            "回锅$meat，加入${method}需要的调味料，大火翻匀后出锅。"
                        ),
                        score = 88 + ((index + vegIndex) % 11),
                        imageQuery = name
                    )
                    index++
                    if (out.size >= 1200) return@lazy out
                }
            }
        }
        out
    }

    /** Plant-protein dishes, kept separate so the meal builder can guarantee one. */
    val plantProtein: List<CatalogDish> by lazy {
        plantProteins.flatMapIndexed { i, bean ->
            vegetables.take(10).mapIndexed { j, veg ->
                CatalogDish(
                    name = "${veg}${bean}家常烧",
                    protein = "豆制品",
                    vegetables = listOf(veg),
                    ingredients = "$bean、$veg、葱姜蒜、生抽、蚝油、食用油、盐",
                    steps = listOf(
                        "$bean切块或切段，$veg洗净切好。",
                        "热锅少油，将$bean煎至表面微黄。",
                        "加入葱姜蒜炒香，再加入$veg翻炒。",
                        "加入少量清水、生抽和蚝油，小火烧至入味。"
                    ),
                    score = 91 + ((i + j) % 8),
                    imageQuery = "${veg}${bean}"
                )
            }
        )
    }

    val greenVegetables: List<CatalogDish> by lazy {
        greens.flatMapIndexed { i, green ->
            listOf("蒜蓉", "香菇", "蚝油", "清炒", "上汤").mapIndexed { j, style ->
                val name = when (style) {
                    "蒜蓉" -> "蒜蓉$green"
                    "香菇" -> "香菇扒$green"
                    "蚝油" -> "蚝油$green"
                    "清炒" -> "清炒$green"
                    else -> "上汤$green"
                }
                CatalogDish(
                    name = name,
                    protein = "蔬菜",
                    vegetables = listOf(green),
                    ingredients = "$green、大蒜、食用油、盐${if (style == "香菇") "、鲜香菇" else ""}${if (style == "蚝油") "、蚝油" else ""}",
                    steps = listOf(
                        "$green洗净沥干，大蒜切末。",
                        "热锅少油爆香蒜末。",
                        "加入$green大火翻炒至刚断生。",
                        "根据菜式加入${if (style == "蚝油") "蚝油和少量清水" else "盐"}，快速出锅。"
                    ),
                    score = 92 + ((i + j) % 7),
                    imageQuery = name
                )
            }
        }
    }

    val soups: List<CatalogDish> = listOf(
        CatalogDish("冬瓜虾皮汤", "汤", listOf("冬瓜"), "冬瓜、虾皮、姜、葱、盐", listOf("冬瓜切片。", "姜和虾皮炒香。", "加水煮开后放入冬瓜。", "煮至冬瓜透明，调盐撒葱花。"), 96, "冬瓜虾皮汤"),
        CatalogDish("番茄蛋花汤", "汤", listOf("番茄"), "番茄、鸡蛋、葱、盐、香油", listOf("番茄切块炒出汁。", "加入清水煮开。", "鸡蛋打散沿锅边淋入。", "调盐，滴少量香油。"), 96, "番茄蛋花汤"),
        CatalogDish("紫菜豆腐汤", "汤", listOf("紫菜", "豆腐"), "紫菜、豆腐、葱、盐、白胡椒", listOf("豆腐切小块。", "水开后加入豆腐煮几分钟。", "放入紫菜。", "加盐和白胡椒，撒葱花。"), 95, "紫菜豆腐汤"),
        CatalogDish("玉米胡萝卜排骨汤", "汤", listOf("玉米", "胡萝卜"), "排骨、玉米、胡萝卜、姜、盐", listOf("排骨焯水洗净。", "排骨加姜炖煮。", "加入玉米和胡萝卜。", "炖至排骨熟透，调盐。"), 97, "玉米胡萝卜排骨汤"),
        CatalogDish("丝瓜蛋汤", "汤", listOf("丝瓜"), "丝瓜、鸡蛋、姜、盐、香油", listOf("丝瓜去皮切片。", "锅中加水和姜片烧开。", "放入丝瓜煮软。", "淋入蛋液，调盐和香油。"), 95, "丝瓜蛋汤")
    )
}
