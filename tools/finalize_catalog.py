#!/usr/bin/env python3
import csv
from pathlib import Path
import generate_recipe_catalog as g

out=Path('app/src/main/assets/recipes.csv')
names=list(dict.fromkeys(g.BASE))
extra=Path('tools/extra_dishes.txt').read_text(encoding='utf-8').splitlines()
for n in extra:
    n=n.strip()
    if n and n not in names:names.append(n)
verbs=['炒','烧','炖','蒸','煎','焖','拌','卤','煮','烤','炸']
for n in list(names):
    if any(v in n for v in verbs) or any(v in n for v in ['汤','粥','面','饭','饼','包子','饺子','糕','馍']):continue
    t=g.classify(n)
    prefixes=['红烧','香煎','香辣','酱香'] if t in ['动物蛋白','植物蛋白'] else ['清炒','蒜蓉','蚝油','香辣']
    for p in prefixes:
        x=p+n
        if x not in names:names.append(x)
        if len(names)>=2400:break
    if len(names)>=2400:break
if len(names)<2000:raise SystemExit(f'Only {len(names)} dishes generated')
out.parent.mkdir(parents=True,exist_ok=True)
with out.open('w',encoding='utf-8',newline='') as f:
    w=csv.writer(f);w.writerow(['id','name','type','vegetables','score','ingredients','steps','image_query'])
    for i,n in enumerate(names[:2400],1):
        t=g.classify(n);v,s,ing,step=g.fields(n,t);w.writerow([i,n,t,'、'.join(v),s,ing,step,n])
print('Generated',min(len(names),2400),'dishes')
