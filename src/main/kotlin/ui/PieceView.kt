package ui.assignments.connectfour.ui

import javafx.animation.AnimationTimer
import javafx.scene.chart.XYChart
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Text
import ui.assignments.connectfour.model.Grid
import ui.assignments.connectfour.model.Model
import ui.assignments.connectfour.model.Player
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.sign

// Input Devices - Implementing Dragging pg.42-43

data class DragInfo(
    var target: Circle? = null,
    var anchorX: Double = 0.0,
    var anchorY: Double = 0.0,
    var initialX: Double = 0.0,
    var initialY: Double = 0.0
)


class PieceView(x: Double, y: Double, color: Color, val gridView: GridView, val text: Label) {

    var next: PieceView? = null

    var dragInfo = DragInfo()

    val piece = Circle(x, y, 36.0).apply {
        addEventFilter(MouseEvent.MOUSE_PRESSED) {
            dragInfo = DragInfo(
                this, it.sceneX, it.sceneY,
                translateX, translateY
            )
        }
        addEventFilter(MouseEvent.MOUSE_DRAGGED) {
            if (it.sceneX > 0.0 && it.sceneX < 90.0 * 10) {
                translateX = dragInfo.initialX + it.sceneX - dragInfo.anchorX
            }
            if (it.sceneY > 0.0 && it.sceneY < 90.0) {
                translateY = dragInfo.initialY + it.sceneY - dragInfo.anchorY
            }
            if (it.sceneX > 90.0 && it.sceneX < 90.0 * 9) {
                if (color == Color.RED) {
                    translateX = ((translateX + 45.0) / 90.0).toInt() * 90.0
                } else {
                    translateX = ((translateX - 45.0) / 90.0).toInt() * 90.0
                }
            }

        }
        addEventFilter(MouseEvent.MOUSE_RELEASED) {
            dragInfo = DragInfo()

            if (xColumn() in 0..7) {
                Model.dropPiece(xColumn())
                if (Model.onPieceDropped.value == null) {
                    back()
                } else {
                    drop()
                }
            } else {
                back()
            }

        }
        fill = color
    }

    private fun back() {
        val animation = object : AnimationTimer() {
            override fun handle(now: Long) {
                piece.translateX -= 9.0 * sign(piece.translateX)
                piece.translateY -= 0.3 * sign(piece.translateY)
                if (abs(piece.translateX) < 10.0) {
                    stop()
                    piece.translateX = 0.0
                    piece.translateY = 0.0
                }
            }
        }
        animation.start()
    }

    private fun drop() {
        val dropY = Model.onPieceDropped.value?.y

        var step2 = false
        var step3 = false

        var speed = 0.0

        val animation = object : AnimationTimer() {
            override fun handle(now: Long) {
                if (dropY != null) {
                    if (step3) {
                        speed += 0.69
                        piece.translateY += speed
                        if (piece.translateY >= dropY * 90.0 + 75.0) {
                            stop()
                            val pieceDropped = Model.onPieceDropped.value
                            if (pieceDropped != null) {
                                gridView.draw(
                                    (pieceDropped.x + 2) * 90.0 - 45.0,
                                    (pieceDropped.y + 1) * 90.0 + 45.0,
                                    if (pieceDropped.player == Player.ONE) {
                                        Color.RED
                                    } else {
                                        Color.YELLOW
                                    }
                                )
                            }

                            piece.translateX = 0.0
                            piece.translateY = 0.0

                            piece.isVisible = false
                            text.textFill = Color.GRAY
                            // next?.piece?.isVisible = !(Model.onGameDraw.value || Model.onGameWin.value != Player.NONE)

                            if (Model.onGameDraw.value) {
                                gridView.grid.children.add(Label("Draw.").apply {
                                    relocate(90.0 * 5, 45.0)
                                })
                            } else if (Model.onGameWin.value == Player.ONE) {
                                gridView.grid.children.add(Label("Player #1 won!!!").apply {
                                    relocate(90.0 * 5 - 45.0, 45.0)
                                })
                            } else if (Model.onGameWin.value == Player.TWO) {
                                gridView.grid.children.add(Label("Player #2 won!!!").apply {
                                    relocate(90.0 * 5, 45.0)
                                })
                            } else {
                                next?.piece?.isVisible = true
                                next?.text?.textFill = Color.BLACK
                            }
                        }
                    } else if (step2) {
                        speed -= 1.8
                        piece.translateY -= speed
                        if (abs(speed) <= 1.0) {
                            step3 = true
                        }
                    } else {
                        piece.translateY += speed
                        speed += 0.9
                        if (piece.translateY >= dropY * 90.0 + 75.0) {
                            step2 = true
                        }
                    }
                }
            }
        }
        animation.start()
    }

    private fun xColumn(): Int {
        return floor((piece.centerX + piece.translateX) / 90.0).toInt() - 1
    }

}