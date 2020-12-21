package aoc.y2020

import aoc.Puzzle
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun main() {
    Puzzle21().runAndProfile()
}

class Puzzle21 : Puzzle(2020, 21) {

    private val allergensToIngredients: MutableMap<String, MutableSet<String>> = mutableMapOf()
    private val allIngredients: MutableList<Set<String>> = mutableListOf()

    override fun run() {
        inputLines.forEach {
            val ingredients = it.split(" (contains ")[0].split(" ").toMutableSet()
            allIngredients.add(ingredients)
            val allergens = it.split(" (contains ")[1].removeSuffix(")").split(", ")
            allergens.forEach { allergen ->
                allergensToIngredients[allergen] = allergensToIngredients.getOrPut(allergen) { ingredients }
                    .intersect(ingredients).toMutableSet()
            }
        }
        val potentialAllergens = allergensToIngredients.values.flatten().toSet()
        val withoutAllergens = allIngredients.flatten().toSet() - potentialAllergens
        println(allIngredients.sumBy { it.intersect(withoutAllergens).size })
        while (allergensToIngredients.values.any { it.size > 1 }) {
            allergensToIngredients.forEach { (allergen, ingredients) ->
                if (ingredients.size == 1) {
                    val found = ingredients.first()
                    allergensToIngredients.filterKeys { it != allergen }
                        .forEach { (_, otherIngredients) -> otherIngredients.remove(found) }
                }
            }
        }
        println(allergensToIngredients.entries.sortedBy { (key, _) -> key }
            .joinToString(",") { it.value.first() })
    }
}