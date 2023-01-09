package ui.assignments.connectfour

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage
import ui.assignments.connectfour.model.Model
import ui.assignments.connectfour.ui.GridView
import ui.assignments.connectfour.ui.PieceView

class ConnectFourApp : Application() {

    private val rootGirdView = GridView()
    private val root = rootGirdView.grid

    override fun start(stage: Stage) {
        val scene = Scene(root, 90.0*10, 90.0*10)
        stage.title = "CS349 - A3 Connect Four - j349hu"
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }

    private val startButton = Button("Click here to start game!")

    var playerText1 = Label("Player #1")
    var playerText2 = Label("Player #2")

    var pieceView1 = PieceView(45.0, 45.0+18.0, Color.RED, rootGirdView, playerText1)
    var pieceView2 = PieceView(90.0*10-45.0, 45.0+18.0, Color.YELLOW, rootGirdView, playerText2)

    init {
        pieceView1.next = pieceView2
        pieceView2.next = pieceView1

        root.children.add(pieceView1.piece)
        root.children.add(pieceView2.piece)

        root.children.add(playerText1)
        root.children.add(playerText2)

        pieceView1.piece.toBack()
        pieceView2.piece.toBack()
        pieceView1.piece.isVisible = false
        pieceView2.piece.isVisible = false

        playerText1.relocate(18.0, 18.0)
        playerText2.relocate(90.0*9+18.0, 18.0)
        // playerText1.font = Font("Arial", 12.0)
        // playerText2.font = Font("Arial", 12.0)
        playerText1.textFill = Color.GRAY
        playerText2.textFill = Color.GRAY

        root.children.add(startButton)
        startButton.onAction = EventHandler {
            Model.startGame()
            startButton.isVisible = false
            pieceView1.piece.isVisible = true
            playerText1.textFill = Color.BLACK
        }
        startButton.relocate(90.0*5-45.0, 45.0)

    }

}
