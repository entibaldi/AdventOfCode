package com.etibaldi.adventofcode

import io.data2viz.color.Colors
import io.data2viz.geom.Rect
import io.data2viz.geom.RectGeom
import io.data2viz.math.pct
import io.data2viz.viz.JFxVizRenderer
import io.data2viz.viz.RectNode
import io.data2viz.viz.viz
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.VBox
import javafx.stage.Stage
import java.io.File

const val CANVAS_SIZE = 1000.0
val ELF_CLAIM_REGEX = Regex("#(\\d*) @ (\\d*),(\\d*): (\\d*)x(\\d*)")

fun main(args: Array<String>) {
    Application.launch(Day3::class.java)
}

data class ElfClaim(
    val id: Int,
    val rect: Rect
) {
    fun overlapWith(other: ElfClaim): Rect? {
        return rect.overlapWith(other.rect)
    }
}

private fun Rect.overlapWith(other: Rect): Rect? {
    val leftX = Math.max(this.x, other.x)
    val rightX = Math.min(this.right, other.right)
    val topY = Math.max(this.y, other.y)
    val bottomY = Math.min(this.bottom, other.bottom)
    return if (leftX < rightX && topY < bottomY) {
        RectGeom(leftX, topY, rightX - leftX, bottomY - topY)
    } else null
}

private fun String.toElfClaim(): ElfClaim {
    val groups = ELF_CLAIM_REGEX.matchEntire(this)
        ?.groupValues?.let { groups ->
        groups.subList(1, groups.size).map { it.toDouble() }
    }!!
    return ElfClaim(groups[0].toInt(), RectGeom(groups[1], groups[2], groups[3], groups[4]))
}


class Day3 : Application() {

    val elfClaims = File("3-input.txt")
        .readLines().map { it.toElfClaim() }

    val elfClaimOverlaps = mutableListOf<Triple<Rect, Int, Int>>().apply {
        elfClaims.forEachComparison { e1, e2 ->
            e1.overlapWith(e2)?.let { this.add(Triple(it,e1.id, e2.id)) }
        }
    }

    val elfClaimWin: ElfClaim

    init {

        val localPaint = Array(CANVAS_SIZE.toInt()) { IntArray(CANVAS_SIZE.toInt()) { 0 } }
        elfClaimOverlaps.forEach { (rect, _, _) ->
            for (i in 0 until rect.height.toInt())
                for (j in 0 until rect.width.toInt())
                    localPaint[rect.y.toInt() + i][rect.x.toInt() + j] = 1
        }
        val elfClaimById = elfClaims.associateBy { it.id }
        elfClaimWin = elfClaimById.entries.first {
            val id = it.key
            elfClaimOverlaps.none { (_, id1, id2) -> id1 == id || id2 == id }
        }.value
        println(localPaint.sumBy { it.sum() })
        println(elfClaimWin.id)
    }

    val black = Colors.rgb(0x000000, 100.pct)
    val white = Colors.rgb(0xffffff, 100.pct)
    val red = Colors.rgb(0xff0000, 50.pct)
    val blue = Colors.rgb(0x0000ff, 100.pct)
    val canvas = Canvas(CANVAS_SIZE, CANVAS_SIZE)

    val viz = viz {
        width = CANVAS_SIZE
        height = CANVAS_SIZE

        // black background
        rect {
            width = CANVAS_SIZE
            height = CANVAS_SIZE
            x = 0.0
            y = 0.0
            fill = black
        }

        elfClaims.forEach {
            add(RectNode(it.rect).apply {
                fill = if (it.id == elfClaimWin.id) blue else white
            }
            )
        }

        elfClaimOverlaps.forEach { (rect, _, _) ->
            add(RectNode(rect).apply { fill = red })
        }

    }.apply { renderer = JFxVizRenderer(canvas, this) }

    override fun start(stage: Stage?) {


        val root = VBox()
        stage?.let {
            it.scene = (Scene(root, CANVAS_SIZE, CANVAS_SIZE))
            it.show()
            root.children.add(canvas)
            viz.render()
        }
    }

}