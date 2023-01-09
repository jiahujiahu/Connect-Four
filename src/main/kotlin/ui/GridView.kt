package ui.assignments.connectfour.ui

import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

class GridView() {

    val grid = Group().apply {
        for (i in 1..8) {
            for (j in 1..7) {
                children.add(GridUnit(i*90.0, j*90.0+24.0, 90.0).unit)
            }
        }
    }

    fun draw(x: Double, y: Double, color: Color) {
        grid.children.add(Circle(x, y+24.0, 36.0, color))
    }


}