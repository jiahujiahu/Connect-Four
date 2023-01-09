package ui.assignments.connectfour.ui

import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.scene.shape.Shape

class GridUnit (x: Double, y: Double, size: Double) {

    val unit: Shape = Shape.subtract(
        Rectangle(x, y, size, size),
        Circle(x + size / 2, y + size / 2, size / 2 - 9)
    )

    init {
        unit.fill = javafx.scene.paint.Color.DEEPSKYBLUE
        unit.stroke = Color.BLACK
    }
    
}