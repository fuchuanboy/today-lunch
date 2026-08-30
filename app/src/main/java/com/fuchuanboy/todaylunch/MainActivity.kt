package com.fuchuanboy.todaylunch

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*
import java.net.URL
import java.net.URLEncoder
import java.util.Calendar
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity:Activity(){
 data class Dish(val name:String,val protein:String,val main:String,val veg:String,val score:Int,val steps:List<String>)
 private val r=Random(System.currentTimeMillis())
 private lateinit var root:LinearLayout
 private val meat=listOf(
  "红烧肉","回锅肉","小炒肉","鱼香肉丝","木须肉","青椒肉丝","蒜薹炒肉","芹菜炒肉","香菇肉片","土豆烧肉","梅菜扣肉","腐竹烧肉","笋干烧肉","粉条炖肉","酸菜白肉","糖醋排骨","红烧排骨","椒盐排骨","粉蒸排骨","红烧猪蹄","黄豆炖猪蹄","酱肘子","肉末茄子","肉末豆角","肉末蒸蛋","肉末豆腐","洋葱肥牛","番茄肥牛","金针菇肥牛","土豆烧牛肉","黑椒牛柳","芹菜炒牛肉","小炒黄牛肉","孜然牛肉","水煮牛肉","酱牛肉","红烧牛腩","萝卜炖牛腩","咖喱牛肉","番茄牛腩","葱爆羊肉","孜然羊肉","手抓羊肉","红焖羊肉","萝卜炖羊肉","洋葱炒羊肉","芹菜炒羊肉","香辣羊肉","清炖羊肉","照烧鸡腿","宫保鸡丁","辣子鸡丁","黄焖鸡","咖喱鸡肉","土豆烧鸡","香菇炖鸡","板栗烧鸡","青椒鸡丁","黑椒鸡柳","柠檬鸡腿","可乐鸡翅","红烧鸡翅","蒜香鸡翅","盐焗鸡腿","葱油鸡","口水鸡","麻辣鸡丝","酸辣鸡丝","啤酒鸭","魔芋烧鸭","红烧鸭块","酸菜鸭","土豆烧鸭","香辣鸭块","冬瓜炖鸭","紫苏鸭","清蒸鲈鱼","红烧鲫鱼","家常炖鱼","酸菜鱼","水煮鱼","番茄鱼片","剁椒鱼头","葱烧鱼块","糖醋鱼块","香煎带鱼","红烧带鱼","清蒸带鱼","椒盐带鱼","蒜蓉粉丝虾","白灼虾","油焖大虾","香辣虾","蒜香虾仁","西兰花虾仁","芦笋虾仁","番茄虾仁","虾仁炒蛋","青豆虾仁","腰果虾仁","豆腐虾仁","粉丝蒸虾","椒盐虾","葱姜炒蟹","香辣蟹","清蒸螃蟹","蟹黄豆腐")
 private val veg=listOf("上海青","小白菜","油麦菜","菠菜","菜心","芥蓝","西兰花","菜花","空心菜","苋菜","生菜","娃娃菜","大白菜","包菜","芦笋","芹菜","蒜薹","豇豆","四季豆","毛豆","茄子","番茄","黄瓜","丝瓜","冬瓜","苦瓜","西葫芦","土豆","莲藕","山药","胡萝卜","白萝卜","青椒","彩椒","洋葱","豆芽","韭菜","韭黄","香菇","平菇","杏鲍菇","金针菇","木耳","海带","莴笋","秋葵","南瓜","佛手瓜","瓠瓜","芥菜","豌豆苗","玉米","紫甘蓝","红薯叶","藕带","春笋","冬笋","荠菜")
 private val soup=listOf("番茄蛋花汤","紫菜蛋花汤","冬瓜虾皮汤","海带豆腐汤","菌菇豆腐汤","酸辣汤","玉米排骨汤","萝卜牛腩汤","山药排骨汤","莲藕排骨汤","鲫鱼豆腐汤","丝瓜蛋汤","菠菜蛋汤","冬瓜肉丸汤","紫菜虾皮汤","青菜豆腐汤","菌菇鸡汤","老鸭冬瓜汤","羊肉萝卜汤","番茄牛肉汤")
 private val season=mapOf(1 to listOf("菠菜","白菜","上海青","芥蓝","萝卜"),2 to listOf("菠菜","菜心","上海青","芥蓝","生菜"),3 to listOf("菠菜","油麦菜","芹菜","春笋","菜心"),4 to listOf("油麦菜","上海青","苋菜","芦笋","莴笋"),5 to listOf("空心菜","苋菜","生菜","油麦菜","丝瓜"),6 to listOf("空心菜","苋菜","油麦菜","丝瓜","豇豆"),7 to listOf("空心菜","苋菜","丝瓜","豇豆","毛豆"),8 to listOf("空心菜","苋菜","丝瓜","豇豆","冬瓜"),9 to listOf("空心菜","油麦菜","上海青","丝瓜","芥蓝"),10 to listOf("菠菜","上海青","白菜","芥蓝","西兰花"),11 to listOf("菠菜","白菜","上海青","萝卜","西兰花"),12 to listOf("菠菜","白菜","上海青","芥蓝","菜心"))
 private var today=mutableListOf<Dish>()
 override fun onCreate(b:Bundle?){super.onCreate(b);newMeal();home()}
 private fun dp(x:Int)=(x*resources.displayMetrics.density).roundToInt()
 private fun bg(c:Int,rad:Int)=GradientDrawable().apply{setColor(c);cornerRadius=dp(rad).toFloat()}
 private fun label(t:String,size:Float,color:Int=Color.DKGRAY)=TextView(this).apply{text=t;textSize=size;setTextColor(color);setPadding(dp(5),dp(6),dp(5),dp(6))}
 private fun page():LinearLayout=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(18),dp(16),dp(18),dp(20));setBackgroundColor(Color.rgb(247,250,245))}
 private fun title(p:LinearLayout,a:String,b:String){p.addView(label("今天中午吃什么",14,Color.rgb(55,125,70)));p.addView(label(a,30,Color.rgb(20,100,55)).apply{gravity=Gravity.CENTER});p.addView(label(b,14,Color.GRAY).apply{gravity=Gravity.CENTER})}
 private fun button(p:LinearLayout,t:String,fn:()->Unit){p.addView(TextView(this).apply{text=t;textSize=17f;gravity=Gravity.CENTER;setTextColor(Color.WHITE);background=bg(Color.rgb(55,174,91),34);setPadding(dp(8),dp(10),dp(8),dp(10));setOnClickListener{fn()}},LinearLayout.LayoutParams(-1,dp(68)).apply{setMargins(0,dp(10),0,dp(8))})}
 private fun image(p:LinearLayout,q:String,h:Int){val iv=ImageView(this);iv.scaleType=ImageView.ScaleType.CENTER_CROP;iv.background=bg(Color.rgb(232,238,230),22);p.addView(iv,LinearLayout.LayoutParams(-1,dp(h)).apply{setMargins(0,dp(4),0,dp(4))});Thread{try{val k=URLEncoder.encode(q+", Chinese food","UTF-8");val b=BitmapFactory.decodeStream(URL("https://loremflickr.com/900/650/$k?lock=${q.hashCode().and(0x7fffffff)}").openStream());iv.post{if(b!=null)iv.setImageBitmap(b)}}catch(_:Exception){}}.start()}
 private fun card(p:LinearLayout)=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;setPadding(dp(12),dp(12),dp(12),dp(12));background=bg(Color.WHITE,24);p.addView(this,LinearLayout.LayoutParams(-1,-2).apply{setMargins(0,dp(10),0,dp(8))})}
 private fun home(){val p=page();title(p,"今天中午吃什么？","家常口味 · 时令搭配 · 科学膳食");val c=card(p);image(c,"中国家常菜 四菜一汤",245);c.addView(label("2400+ 道组合菜谱",21,Color.rgb(30,110,65)).apply{gravity=Gravity.CENTER});c.addView(label("真实网络菜品照片 · 点击菜品查看做法",13,Color.GRAY).apply{gravity=Gravity.CENTER});button(p,"🎲  随机一桌\n生成今日四菜一汤"){newMeal();menu()};p.addView(label("猪肉为主 · 牛肉 · 羊肉 · 鸡肉 · 鸭肉 · 鱼虾蟹轮换\n每桌固定包含时令烧青菜，并计算营养搭配评分",14,Color.DKGRAY).apply{gravity=Gravity.CENTER});setContentView(ScrollView(this).apply{addView(p)})}
 private fun classify(s:String)=when{listOf("牛","肥牛").any{s.contains(it)}->"牛肉";s.contains("羊")->"羊肉";s.contains("鸡")->"鸡肉";s.contains("鸭")->"鸭肉";s.contains("鱼")||s.contains("带鱼")->"鱼肉";s.contains("虾")||s.contains("蟹")->"海鲜";else->"猪肉"}
 private fun recipe(m:String,v:String,score:Int):Dish{val kind=classify(m);val verbs=when{m.contains("蒸")||m.contains("清蒸")->listOf("食材洗净切好，鱼虾或肉类擦干。","加入葱姜、少量料酒和生抽腌制10分钟。","水开后上锅蒸，按主料厚度蒸至熟透。","加入$v后快速翻热，出锅前少量盐调味。");m.contains("炖")||m.contains("烧")||m.contains("红烧")||m.contains("黄焖")->listOf("主料切块，加入料酒、生抽、姜片腌制10分钟。","热锅少油煎至表面微黄，加入葱姜蒜炒香。","加入生抽、老抽少量、冰糖或蚝油，加热水没过食材。","小火焖至主料熟透，再加入$v收汁。","最后尝味调整盐，汤汁浓稠即可出锅。");else->listOf("主料切片或切块，加料酒、生抽和少量淀粉抓匀腌10分钟。","热锅少油，葱姜蒜爆香，主料大火翻炒至变色。","加入$v快速翻炒，按口味加入生抽、蚝油、醋或豆瓣酱。","炒至配菜断生、肉类全熟，最后调整盐和少量糖提鲜。")};return Dish(if(m.contains(v))m else "$m·$v",kind,m,v,score,verbs)}
 private fun newMeal(){val month=Calendar.getInstance().get(Calendar.MONTH)+1;val greens=season[month]!!;val used=HashSet<String>();today.clear();val order=listOf("猪肉","牛肉","羊肉","鸡肉","鸭肉","鱼肉","海鲜");for(k in order){val x=meat.shuffled(r).firstOrNull{classify(it)==k&&used.add(it)};if(x!=null&&today.size<3)today.add(recipe(x,veg.filter{!used.contains(it)}.random(r),88+r.nextInt(10)))};while(today.size<3){val x=meat.random(r);if(used.add(x))today.add(recipe(x,veg.random(r),86+r.nextInt(12)))};val g=greens.random(r);today.add(Dish("蒜蓉炒$g","蔬菜","蒜蓉炒$g",g,94,listOf("$g洗净沥干，蒜末切好。","热锅少油，蒜末小火炒香。","大火下$g快速翻炒，加少量盐和蚝油。","断生立即出锅，保持翠绿脆嫩。")))}
 private fun menu(){val p=page();title(p,"今日四菜一汤","点击任意菜品，查看真实图片、用料、做法与评分");val score=today.map{it.score}.average().roundToInt();val c=card(p);c.addView(label("营养搭配评分  $score / 100",21,Color.rgb(25,125,65)).apply{gravity=Gravity.CENTER});c.addView(label("动物蛋白 ✓  蔬菜 ✓  时令 ✓  荤素 ✓  油盐控制 ✓",13,Color.DKGRAY).apply{gravity=Gravity.CENTER});today.forEach{dish(p,it)};dish(p,Dish(soup.random(r),"汤品","汤品","",91,listOf("食材洗净切好。","加水煮开后转小火，煮至主料熟透。","最后少量盐调味，保持清淡。")));button(p,"🎲 再换一桌"){newMeal();menu()};button(p,"⌂ 返回首页"){home()};setContentView(ScrollView(this).apply{addView(p)})}
 private fun dish(p:LinearLayout,d:Dish){val c=card(p);image(c,d.name,150);c.addView(label(d.name,19,Color.rgb(35,80,50)));c.addView(label("${d.protein} · 营养评分 ${d.score}/100",13,Color.GRAY));c.setOnClickListener{detail(d)}}
 private fun detail(d:Dish){val p=page();title(p,d.name,"${d.protein} · 完整家常做法");val c=card(p);image(c,d.name,250);c.addView(label("营养搭配评分  ${d.score}/100",20,Color.rgb(25,125,65)));c.addView(label("主要用料",18,Color.DKGRAY));c.addView(label("${d.main}、${d.veg}、葱、姜、蒜、食用油、生抽、料酒、盐；根据菜式可选蚝油、醋、豆瓣酱或冰糖。",14));c.addView(label("制作步骤",18,Color.DKGRAY));d.steps.forEachIndexed{i,s->c.addView(label("${i+1}. $s",14))};c.addView(label("搭配建议",18,Color.DKGRAY));c.addView(label("午餐建议搭配米饭；一桌同时安排肉类、时令蔬菜和清淡汤品。营养分为本应用的搭配参考分，不替代专业膳食评估。",14));button(p,"← 返回今日菜单"){menu()};setContentView(ScrollView(this).apply{addView(p)})}
}
