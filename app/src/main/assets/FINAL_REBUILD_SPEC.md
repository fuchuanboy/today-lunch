# Final rebuild specification

## Lunch structure
1. Animal protein: pork weighted highest; rotate beef, lamb, chicken, duck, fish, shrimp/crab.
2. Plant protein: tofu, dried tofu, tofu skin, edamame, soy products.
3. Leafy green: seasonal leafy vegetable; never duplicate the same primary vegetable in one meal.
4. Vitamin vegetable: rotate tomato, bell pepper, broccoli, cauliflower, carrot, pumpkin, etc.
5. Soup: complementary ingredients; avoid repeating the main vegetables from the four dishes.

## Recipe integrity
Every Recipe stores name, protein, vegetables, ingredients, steps, nutrition tags and image search terms as one immutable record. Never synthesize a recipe name from unrelated ingredients.

## Images
Use Wikimedia Commons API search at runtime, matching the recipe's dedicated image query. Cache the resolved thumbnail per recipe. Never use random placeholder images or generic cat/landscape images.

## UI
Use fixed aspect-ratio image cards (16:10) with centerCrop, text wrapping, and ScrollView. No single-line clipping. Detail screen shows image, ingredients, steps, and nutrition score.

## Meal scoring
Score protein diversity, plant protein, leafy green, vitamin vegetable, vegetable diversity, cooking-method diversity, and soup complementarity. Penalize repeated vegetables and repeated protein source.
